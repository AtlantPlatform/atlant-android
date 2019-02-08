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

package io.atlant.wallet;


import static org.junit.Assert.assertTrue;

import io.atlant.wallet.utils.DigitsUtils;
import io.atlant.wallet.utils.MyWallet;
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