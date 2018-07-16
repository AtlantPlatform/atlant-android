/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package com.frostchein.atlant.rest;

import android.support.annotation.NonNull;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.SendTransactions;
import com.frostchein.atlant.utils.DigitsUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.greenrobot.eventbus.EventBus;
import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.request.RawTransaction;
import retrofit2.Call;
import retrofit2.Response;

public final class TransactionRestHandler {

  private Call<Nonce> callNonce;
  private Call<GasPrice> callGasPrice;
  private Call<SendTransactions> callSendTransactions;

  private Nonce nonce;
  private GasPrice gasPrice;

  public void preparationTransaction(AtlantClient atlantClient, String address) {
    requestNonce(atlantClient, address);
    requestGasPrice(atlantClient);
  }

  public void cancel() {
    if (callNonce != null) {
      callNonce.cancel();
    }
    if (callGasPrice != null) {
      callGasPrice.cancel();
    }
    if (callSendTransactions != null) {
      callSendTransactions.cancel();
    }
    callNonce = null;
    callGasPrice = null;
    callSendTransactions = null;
    nonce = null;
    gasPrice = null;
  }

  private void requestNonce(final AtlantClient atlantClient, final String address) {
    BaseRequest<Nonce> callback = new BaseRequest<>(new BaseRequest.Callback<Nonce>() {
      @Override
      public void onResponse(Response<Nonce> response) {
        nonce = response.body();
        if (nonce != null && gasPrice != null) {
          EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_SEND, nonce, gasPrice));
        }
      }
    }, BaseActivity.REQUEST_CODE_SEND);
    callNonce = atlantClient.getNonce(callback, address);
  }

  private void requestGasPrice(final AtlantClient atlantClient) {
    BaseRequest<GasPrice> callback = new BaseRequest<>(new BaseRequest.Callback<GasPrice>() {
      @Override
      public void onResponse(Response<GasPrice> response) {
        gasPrice = response.body();
        if (nonce != null && gasPrice != null) {
          EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_SEND, nonce, gasPrice));
        }
      }
    }, BaseActivity.REQUEST_CODE_SEND);

    callGasPrice = atlantClient.getGasPrice(callback);
  }

  public void sendTransactionToken(
      AtlantClient atlantClient,
      Nonce nonce,
      GasPrice gasPrice,
      String address,
      String value,
      Credentials credentials,
      long gasLimit,
      String tokenAddress,
      long tokenID
  ) throws Exception {
    String s;

    s = inputData(tokenID, address, value);

    RawTransaction rawTransaction = RawTransaction
        .createTransaction(DigitsUtils.getBase10from16(nonce.getResult()),
            DigitsUtils.getBase10from16(gasPrice.getResult()),
            BigInteger.valueOf(gasLimit), tokenAddress, BigInteger.ZERO, s);

    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
    String hexValue = Hex.toHexString(signedMessage);
    callSendTransactions = atlantClient.sendTransaction(callbackSendTransactions, hexValue);
  }

  public void sendTransaction(
      AtlantClient atlantClient,
      Nonce nonce,
      GasPrice gasPrice,
      String address,
      String value,
      Credentials credentials,
      long gasLimit
  ) throws Exception {

    RawTransaction rawTransaction = RawTransaction
        .createTransaction(
            DigitsUtils.getBase10from16(nonce.getResult()),
            DigitsUtils.getBase10from16(gasPrice.getResult()),
            BigInteger.valueOf(gasLimit), address, new BigInteger(stringValueFormat(value, 10)), "");

    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
    String hexValue = Hex.toHexString(signedMessage);
    callSendTransactions = atlantClient.sendTransaction(callbackSendTransactions, hexValue);
  }

  private static BaseRequest<SendTransactions> callbackSendTransactions = new BaseRequest<>(
      new BaseRequest.Callback<SendTransactions>() {
        @Override
        public void onResponse(Response<SendTransactions> response) {
          //ok
          if (response.body().getResult() != null) {
            EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_SEND));
          } else {
            //fail
            if (response.body().getError() != null) {
              EventBus.getDefault()
                  .post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND, response.body().getError().getMessage()));
            } else {
              EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
            }
          }
        }
      }, BaseActivity.REQUEST_CODE_SEND);

  private static String inputData(long contractId, String address, String value) throws Exception {
    if (!WalletUtils.isValidAddress(address)) {
      throw new Exception("address error");
    }
    String strContract = "0x" + Long.toHexString(contractId);
    String strAddress = stringTo64Symbols(address);
    String strValue = stringValueFormat(value, 16);
    strValue = stringTo64Symbols(strValue);
    return strContract + strAddress + strValue;
  }

  private static String stringValueFormat(String value, int radix) {
    BigDecimal bigDecimal = new BigDecimal(value);
    BigDecimal bd = new BigDecimal(DigitsUtils.divide);
    BigDecimal doubleWithStringValue = bd.multiply(bigDecimal);
    return doubleWithStringValue.toBigInteger().toString(radix);
  }

  @NonNull
  private static String stringTo64Symbols(String line) {
    if (line.charAt(0) == '0' && line.charAt(1) == 'x') {
      StringBuilder buffer = new StringBuilder(line);
      buffer.deleteCharAt(0);
      buffer.deleteCharAt(0);
      line = buffer.toString();
    }

    StringBuilder buffer = new StringBuilder();
    buffer.append("0000000000000000000000000000000000000000000000000000000000000000");

    for (int i = 0; i < line.length(); i++) {
      buffer.setCharAt(64 - i - 1, line.charAt(line.length() - i - 1));
    }
    return buffer.toString();

  }
}
