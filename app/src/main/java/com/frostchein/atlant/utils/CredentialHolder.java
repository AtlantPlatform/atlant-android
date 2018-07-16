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
          MyWallet myWallet = new MyWallet();
          myWallet.loadCredentials(password, getFile(context, PATH_WALLET));
          CredentialHolder.myWallet = myWallet;
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
    saveSetting(context, SharedPreferencesUtils.TAG_CURRENT_VALUE, numberToken);
  }

  public static int getNumberToken() {
    return numberToken;
  }

  public static SharedPreferences loadSetting(Context context) {
    SharedPreferences prefs = context
        .getSharedPreferences(SharedPreferencesUtils.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    return prefs;
  }

  public static void saveSetting(Context context, String tag, int value) {
    SharedPreferences.Editor editor = context
        .getSharedPreferences(SharedPreferencesUtils.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
    editor.putInt(tag, value);
    editor.apply();
  }

  public static void saveSetting(Context context, String tag, boolean value) {
    SharedPreferences.Editor editor = context
        .getSharedPreferences(SharedPreferencesUtils.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
    editor.putBoolean(tag, value);
    editor.apply();
  }
}
