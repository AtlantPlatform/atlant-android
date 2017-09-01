package com.frostchein.atlant.rest;

import com.frostchein.atlant.Config;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.SendTransactions;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import org.greenrobot.eventbus.EventBus;
import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.request.RawTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class TransactionRestHandler {


  public static void preparationTransaction(AtlantClient atlantClient) {
    getNonce(atlantClient, CredentialHolder.getAddress());
  }

  private static void getNonce(final AtlantClient atlantClient, String address) {
    atlantClient.getNonce(new Callback<Nonce>() {
      @Override
      public void onResponse(Call<Nonce> call, Response<Nonce> response) {
        if (response.isSuccessful()) {
          getGasPrice(atlantClient, response.body());
        } else {
          EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
        }
      }

      @Override
      public void onFailure(Call<Nonce> call, Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
          EventBus.getDefault().post(new OnStatusTimeOut(BaseActivity.REQUEST_CODE_SEND));
        } else {
          EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
        }
      }
    }, address);
  }

  private static void getGasPrice(AtlantClient atlantClient, final Nonce nonce) {
    atlantClient.getGasPrice(new Callback<GasPrice>() {
      @Override
      public void onResponse(Call<GasPrice> call, Response<GasPrice> response) {
        if (response.isSuccessful()) {
          EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_SEND, nonce, response.body()));
        } else {
          EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
        }
      }

      @Override
      public void onFailure(Call<GasPrice> call, Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
          EventBus.getDefault().post(new OnStatusTimeOut(BaseActivity.REQUEST_CODE_SEND));
        } else {
          EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
        }
      }
    });
  }

  public static void sendTransaction(AtlantClient atlantClient, Nonce nonce, GasPrice gasPrice, String address,
      String value) {
    String s;
    try {
      s = inputData(Config.CONTRACT_ADDRESS_ID, address, value);

      RawTransaction rawTransaction = RawTransaction
          .createTransaction(DigitsUtils.getBase10from16(nonce.getResult()),
              DigitsUtils.getBase10from16(gasPrice.getResult()),
              BigInteger.valueOf(Config.GAS_LIMIT), Config.CONTRACT_ADDRESS, BigInteger.ZERO, s);

      byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, CredentialHolder.getCredentials());
      String hexValue = Hex.toHexString(signedMessage);

      atlantClient.sendTransaction(new Callback<SendTransactions>() {
        @Override
        public void onResponse(Call<SendTransactions> call, Response<SendTransactions> response) {
          if (response.isSuccessful()) {

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

          } else {
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
          }
        }

        @Override
        public void onFailure(Call<SendTransactions> call, Throwable throwable) {
          if (throwable instanceof SocketTimeoutException) {
            EventBus.getDefault().post(new OnStatusTimeOut(BaseActivity.REQUEST_CODE_SEND));
          } else {
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
          }
        }
      }, hexValue);

    } catch (Exception e) {
      EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_SEND));
    }
  }

  private static String inputData(long contractId, String address, String value) throws Exception {
    if (!WalletUtils.isValidAddress(address)) {
      throw new Exception("address error");
    }

    String strContract = "0x" + Long.toHexString(contractId);
    String strAddress = stringTo64Symbols(address);

    BigDecimal bigDecimal = new BigDecimal(value);
    BigDecimal bd = new BigDecimal(DigitsUtils.divide);
    BigDecimal doubleWithStringValue = bd.multiply(bigDecimal);
    String strValue = doubleWithStringValue.toBigInteger().toString(16);
    strValue = stringTo64Symbols(strValue);
    return strContract + strAddress + strValue;
  }

  private static String stringTo64Symbols(String address) {
    if (address.charAt(0) == '0' && address.charAt(1) == 'x') {
      StringBuilder buffer = new StringBuilder(address);
      buffer.deleteCharAt(0);
      buffer.deleteCharAt(0);
      address = buffer.toString();
    }

    StringBuilder buffer = new StringBuilder();
    buffer.append("0000000000000000000000000000000000000000000000000000000000000000");

    for (int i = 0; i < address.length(); i++) {
      buffer.setCharAt(64 - i - 1, address.charAt(address.length() - i - 1));
    }
    return buffer.toString();

  }
}
