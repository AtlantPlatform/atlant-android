package com.frostchein.atlant.model.rent;

import android.support.annotation.NonNull;

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
