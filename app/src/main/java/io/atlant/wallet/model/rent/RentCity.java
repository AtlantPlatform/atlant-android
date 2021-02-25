/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.model.rent;

import androidx.annotation.NonNull;

public class RentCity {

  private String name;
  private String pathImage;

  public RentCity(@NonNull String name, @NonNull String pathImage) {
    this.name = name;
    this.pathImage = pathImage;
  }

  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getPathImage() {
    return pathImage;
  }

  public void setPathImage(@NonNull String pathImage) {
    this.pathImage = pathImage;
  }
}
