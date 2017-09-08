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
