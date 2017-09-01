package com.frostchein.atlant.utils;

import android.content.Context;
import com.frostchein.atlant.events.login.CredentialsCleared;
import com.frostchein.atlant.events.login.SuccessfulAuthorisation;
import com.frostchein.atlant.events.login.SuccessfulChangePassword;
import com.frostchein.atlant.events.login.WrongPassword;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.Transactions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import org.greenrobot.eventbus.EventBus;
import org.web3j.crypto.Credentials;

public final class CredentialHolder {

  private final static String FILE_DIRECTORY = "data";
  public final static String PATH_WALLET = "wallet.data";
  private final static String PATH_TRANSACTION = "transaction.data";
  private final static String PATH_BALANCE = "balance.data";

  private static MyWallet myWallet;

  private static File getFile(Context context, String name) {
    return new File(context.getDir(FILE_DIRECTORY, Context.MODE_PRIVATE).getAbsolutePath(), name);
  }

  public static void createWallet(final Context context, final String password) {
    Thread myThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          myWallet = new MyWallet();
          myWallet.saveWallet(password, getFile(context, PATH_WALLET));
          EventBus.getDefault().post(new SuccessfulAuthorisation());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    myThread.start();
  }

  public static void createWallet(final Context context, final String privateKey, final String password) {
    Thread myThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          myWallet = new MyWallet(privateKey);
          myWallet.saveWallet(password, getFile(context, PATH_WALLET));
          EventBus.getDefault().post(new SuccessfulAuthorisation());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    myThread.start();
  }

  public static void changePasswordWallet(final Context context, final String password) {
    Thread myThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          myWallet = new MyWallet(myWallet.getPrivateKey());
          myWallet.saveWallet(password, getFile(context, PATH_WALLET));
          EventBus.getDefault().post(new SuccessfulChangePassword());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    myThread.start();
  }

  public static void recreateWallet(final Context context, final String password) {
    Thread myThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          myWallet = new MyWallet();
          myWallet.loadCredentials(password, getFile(context, PATH_WALLET));
          EventBus.getDefault().post(new SuccessfulAuthorisation());
        } catch (Exception e) {
          e.printStackTrace();
          EventBus.getDefault().post(new WrongPassword());
        }
      }
    });
    myThread.start();
  }

  public static String getPrivateKey() {
    return myWallet.getPrivateKey();
  }

  public static Credentials getCredentials() {
    return myWallet.getCredentials();
  }

  public static void createPublicKeyByPrivateKey() {
    EventBus.getDefault().post(new SuccessfulAuthorisation());
  }

  public static boolean isFileExist(Context context, String path) {
    return IOUtil.isFileExist(getFile(context, path));
  }

  public static boolean isLogged() {
    return myWallet != null;
  }

  public static String getAddress() {
    return myWallet == null ? null : myWallet.getAddress();
  }

  public static void clearAndRestart() {
    myWallet = null;
    EventBus.getDefault().post(new CredentialsCleared());
  }

  public static void deleteWallet(Context context) {
    myWallet = null;
    try {
      IOUtil.deleteFile(getFile(context, PATH_WALLET));
      deleteWalletInfo(context);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void deleteWalletInfo(Context context) {
    try {
      IOUtil.deleteFile(getFile(context, PATH_BALANCE));
      IOUtil.deleteFile(getFile(context, PATH_TRANSACTION));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void saveWalletInfo(Context context, Balance balance, Transactions transactions) {
    Gson gson = new Gson();
    String json = gson.toJson(balance);
    IOUtil.saveFileWithText(getFile(context, PATH_BALANCE), json);
    json = gson.toJson(transactions);
    IOUtil.saveFileWithText(getFile(context, PATH_TRANSACTION), json);
  }

  public static Balance getWalletBalance(Context context) {
    if (isFileExist(context, PATH_BALANCE)) {
      String str = IOUtil.getTextFromFile(getFile(context, PATH_BALANCE));
      Gson gson = new Gson();
      Type type = new TypeToken<Balance>() {
      }.getType();
      return gson.fromJson(str, type);
    }
    return null;
  }

  public static Transactions getWalletTransaction(Context context) {
    if (isFileExist(context, PATH_TRANSACTION)) {
      String str = IOUtil.getTextFromFile(getFile(context, PATH_TRANSACTION));
      Gson gson = new Gson();
      Type type = new TypeToken<Transactions>() {
      }.getType();
      return gson.fromJson(str, type);
    }
    return null;
  }

}
