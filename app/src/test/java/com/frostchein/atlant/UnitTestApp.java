package com.frostchein.atlant;


import static org.junit.Assert.assertTrue;

import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.MyWallet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.web3j.crypto.WalletUtils;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTestApp {

  @Test
  public void TestValidAddress()
      throws ExecutionException, InterruptedException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
    for (int i = 0; i < 100; i++) {
      MyWallet myWallet = new MyWallet();
      if (!WalletUtils.isValidAddress(myWallet.getAddress()) || !WalletUtils
          .isValidPrivateKey(myWallet.getPrivateKey())) {
        assertTrue(false);
      }
    }
    assertTrue(true);
  }


  @Test
  public void Test16to10() {
    assertTrue(DigitsUtils.getBase10from16("0x59551bb0").intValue() == 1498749872
        && DigitsUtils.getBase10from16("59551bb0").intValue() == 1498749872);
  }

  @Test
  public void TestRound() {
    String str = "3510499999999999999";
    String str2 = "3.510499999999999999";

    BigInteger bd = new BigInteger(str);
    String result = DigitsUtils.valueToString(bd);

    BigDecimal bigDecimal = new BigDecimal(str2);
    BigDecimal result2 = bigDecimal.multiply(BigDecimal.valueOf(DigitsUtils.divide));

    if (result.equals(str2) && result2.toBigInteger().toString().equals(str)) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
  }
}