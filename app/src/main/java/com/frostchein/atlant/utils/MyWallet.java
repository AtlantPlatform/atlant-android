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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.frostchein.atlant.Config;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;

public class MyWallet {

  private Credentials credentials;

  public MyWallet() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
    credentials = Credentials.create(Keys.createEcKeyPair());
  }

  public MyWallet(byte[] privateKey) {
    credentials = Credentials.create(ECKeyPair.create(privateKey));
  }

  public MyWallet(String privateKey) throws Exception {
    if (!WalletUtils.isValidPrivateKey(privateKey)) {
      throw new Exception("Private Key error");
    }
    credentials = Credentials.create(ECKeyPair.create(hexStringToByteArray(privateKey)));
  }

  public String getAddress() {
    return credentials.getAddress();
  }

  public String getPrivateKey() {
    String s = String.format("%02x", credentials.getEcKeyPair().getPrivateKey());
    return String.format("%64s", s).replace(" ","0");
  }

  public byte[] getBytePrivateKey() {
    return credentials.getEcKeyPair().getPrivateKey().toByteArray();
  }

  public byte[] getBytePublicKey() {
    return credentials.getEcKeyPair().getPublicKey().toByteArray();
  }

  public void saveWallet(String password, File file) throws CipherException, IOException {
    WalletFile walletFile = Wallet.create(password, credentials.getEcKeyPair(), Config.N_APPLICATION, 1);
    ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    objectMapper.writeValue(file, walletFile);
  }

  public void loadCredentials(String password, File file) throws IOException, CipherException {
    credentials = WalletUtils.loadCredentials(password, file);
  }

  public Credentials getCredentials() {
    return credentials;
  }

  private byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
          + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
