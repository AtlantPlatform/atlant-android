/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.rest;

import androidx.annotation.NonNull;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.events.network.OnStatusError;
import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.model.GasPrice;
import io.atlant.wallet.model.Nonce;
import io.atlant.wallet.model.SendTransactions;
import io.atlant.wallet.utils.DigitsUtils;
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
          if (response.body().getResult() != null && response.body().getStatus() !=0) {
            EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_SEND));
          } else {
            //fail
            if (response.body().getError() != null) {
              EventBus.getDefault()
                  .post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND, response.body().getError().getMessage()));
            } else {
              EventBus.getDefault()
                      .post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND, response.body().getResult()));
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
