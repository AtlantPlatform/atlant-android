package com.frostchein.atlant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.frostchein.atlant.events.login.CredentialsCleared;
import com.frostchein.atlant.events.login.SuccessfulAuthorisation;
import com.frostchein.atlant.events.login.SuccessfulChangePassword;
import com.frostchein.atlant.events.login.WrongPassword;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.Transactions;
import com.frostchein.atlant.model.TransactionsTokens;
import com.frostchein.atlant.utils.tokens.Token;
import com.frostchein.atlant.utils.tokens.TokenATL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import org.greenrobot.eventbus.EventBus;
import org.web3j.crypto.Credentials;

public final class CredentialHolder {

  private final static String FILE_DIRECTORY = "data";
  public final static String PATH_WALLET = "wallet.data";

  private final static String PATH_ETH_TRANSACTIONS = "eth_transactions.data";
  private final static String PATH_ETH_BALANCE = "eth_balance.data";

  private static MyWallet myWallet;

  /**
   * value -1 is ETH
   * value 0 - N is from Arrays token
   */
  private static int defaultNumberToken = 0;
  private static int numberToken = defaultNumberToken;

  private final static Token[] tokens = {
      new TokenATL()
  };

  private static String getDir(Context context) {
    return context.getDir(FILE_DIRECTORY, Context.MODE_PRIVATE).getAbsolutePath();
  }

  private static File getFile(Context context, String name) {
    return new File(getDir(context), name);
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
    return IOUtils.isFileExist(getFile(context, path));
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
      setNumberToken(context, defaultNumberToken);
      IOUtils.deleteAllFile(new File(getDir(context)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void deleteWalletInfo(Context context) {
    myWallet = null;
    try {
      for (Token token : tokens) {
        IOUtils.deleteFile(getFile(context, token.getNameFileBalance()));
        IOUtils.deleteFile(getFile(context, token.getNameFileTransactions()));
      }
      IOUtils.deleteFile(getFile(context, PATH_ETH_BALANCE));
      IOUtils.deleteFile(getFile(context, PATH_ETH_TRANSACTIONS));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void saveWalletInfo(Context context, Balance balance, Object transactions, Token token) {
    Gson gson = new Gson();

    String pathBalance = PATH_ETH_BALANCE;
    String pathTransactions = PATH_ETH_TRANSACTIONS;

    if (token != null) {
      pathBalance = token.getNameFileBalance();
      pathTransactions = token.getNameFileTransactions();
    }

    String json = gson.toJson(balance);
    IOUtils.saveFileWithText(getFile(context, pathBalance), json);
    json = gson.toJson(transactions);
    IOUtils.saveFileWithText(getFile(context, pathTransactions), json);
  }

  public static Balance getBalance(Context context) {
    return getBalance(context, PATH_ETH_BALANCE);
  }

  public static Balance getBalance(Context context, Token token) {
    return getBalance(context, token.getNameFileBalance());
  }

  private static Balance getBalance(Context context, String path) {
    if (isFileExist(context, path)) {
      String str = IOUtils.getTextFromFile(getFile(context, path));
      Gson gson = new Gson();
      Type type = new TypeToken<Balance>() {
      }.getType();
      return gson.fromJson(str, type);
    }
    return null;
  }

  public static TransactionsTokens getTransaction(Context context, Token token) {
    if (isFileExist(context, token.getNameFileTransactions())) {
      String str = IOUtils.getTextFromFile(getFile(context, token.getNameFileTransactions()));
      Gson gson = new Gson();
      Type type = new TypeToken<TransactionsTokens>() {
      }.getType();
      return gson.fromJson(str, type);
    }
    return null;
  }

  public static Transactions getTransaction(Context context) {
    if (isFileExist(context, PATH_ETH_TRANSACTIONS)) {
      String str = IOUtils.getTextFromFile(getFile(context, PATH_ETH_TRANSACTIONS));
      Gson gson = new Gson();
      Type type = new TypeToken<Transactions>() {
      }.getType();
      return gson.fromJson(str, type);
    }
    return null;
  }

  public static Token[] getTokens() {
    return tokens;
  }

  public static Token getCurrentToken() {
    try {
      if (numberToken == -1) {
        return null;
      }
      return tokens[numberToken];
    } catch (Exception ignored) {
    }
    numberToken = -1;
    return null;
  }

  public static void setNumberToken(Context context, int numberToken) {
    CredentialHolder.numberToken = numberToken;
    SharedPreferences.Editor editor = context
        .getSharedPreferences(SharedPreferencesUtils.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
    editor.putInt(SharedPreferencesUtils.TAG_CURRENT_VALUE, numberToken);
    editor.apply();
  }

  public static int getNumberToken() {
    return numberToken;
  }

  public static void loadSetting(Context context) {
    SharedPreferences prefs = context
        .getSharedPreferences(SharedPreferencesUtils.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    numberToken = prefs.getInt(SharedPreferencesUtils.TAG_CURRENT_VALUE, -1);
  }
}
