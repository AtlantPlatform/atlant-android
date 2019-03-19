/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils.tokens;

abstract class BaseToken extends Token {

  public abstract String getName();

  public abstract String getContractAddress();

  public abstract long getContractId();

}
