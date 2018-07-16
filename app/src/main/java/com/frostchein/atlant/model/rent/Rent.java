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

package com.frostchein.atlant.model.rent;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Rent implements android.os.Parcelable {

  private String name;
  private String country;
  private String city;
  private String[] imageUrl;
  private int numberRooms;
  private int numberBeds;
  private int numberBath;
  private int numberMaxGuests;
  private int priceDollars;
  private String howDay;
  private String description;
  private boolean growth;
  private int numberLikes;
  private Amenities amenities;

  public Rent(
      @NonNull String name,
      @NonNull String country,
      @NonNull String city,
      @NonNull String[] imageUrl,
      int numberRooms,
      int numberBeds,
      int numberBath,
      int numberMaxGuests,
      int priceDollars,
      @NonNull String howDay,
      @NonNull String description,
      boolean growth,
      int numberLikes,
      Amenities amenities) {
    this.name = name;
    this.country = country;
    this.city = city;
    this.imageUrl = imageUrl;
    this.numberRooms = numberRooms;
    this.numberBeds = numberBeds;
    this.numberBath = numberBath;
    this.numberMaxGuests = numberMaxGuests;
    this.priceDollars = priceDollars;
    this.howDay = howDay;
    this.description = description;
    this.growth = growth;
    this.numberLikes = numberLikes;
    this.amenities = amenities;
  }

  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(@NonNull String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(@NonNull String city) {
    this.city = city;
  }

  public String[] getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(@NonNull String[] imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getNumberRooms() {
    return numberRooms;
  }

  public void setNumberRooms(int numberRooms) {
    this.numberRooms = numberRooms;
  }

  public int getNumberBeds() {
    return numberBeds;
  }

  public void setNumberBeds(int numberBeds) {
    this.numberBeds = numberBeds;
  }

  public int getPriceDollars() {
    return priceDollars;
  }

  public void setPriceDollars(int priceDollars) {
    this.priceDollars = priceDollars;
  }

  public String getHowDay() {
    return howDay;
  }

  public boolean isGrowth() {
    return growth;
  }

  public void setGrowth(boolean growth) {
    this.growth = growth;
  }

  public void setHowDay(@NonNull String howDay) {
    this.howDay = howDay;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(@NonNull String description) {
    this.description = description;
  }

  public int getNumberLike() {
    return numberLikes;
  }

  public void setNumberLike(int like) {
    this.numberLikes = like;
  }

  public int getNumberBath() {
    return numberBath;
  }

  public void setNumberBath(int numberBath) {
    this.numberBath = numberBath;
  }

  public int getNumberMaxGuests() {
    return numberMaxGuests;
  }

  public void setNumberMaxGuests(int numberMaxGuests) {
    this.numberMaxGuests = numberMaxGuests;
  }

  public Amenities getAmenities() {
    return amenities;
  }

  public void setAmenities(Amenities amenities) {
    this.amenities = amenities;
  }

  public static class Amenities implements Parcelable {

    private boolean tv;
    private boolean elevator;
    private boolean wifi;
    private boolean kitchen;

    public Amenities(boolean tv, boolean elevator, boolean wifi, boolean kitchen) {
      this.tv = tv;
      this.elevator = elevator;
      this.wifi = wifi;
      this.kitchen = kitchen;
    }

    public boolean isTv() {
      return tv;
    }

    public void setTv(boolean tv) {
      this.tv = tv;
    }

    public boolean isElevator() {
      return elevator;
    }

    public void setElevator(boolean elevator) {
      this.elevator = elevator;
    }

    public boolean isWifi() {
      return wifi;
    }

    public void setWifi(boolean wifi) {
      this.wifi = wifi;
    }

    public boolean isKitchen() {
      return kitchen;
    }

    public void setKitchen(boolean kitchen) {
      this.kitchen = kitchen;
    }


    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte(this.tv ? (byte) 1 : (byte) 0);
      dest.writeByte(this.elevator ? (byte) 1 : (byte) 0);
      dest.writeByte(this.wifi ? (byte) 1 : (byte) 0);
      dest.writeByte(this.kitchen ? (byte) 1 : (byte) 0);
    }

    public Amenities() {
    }

    protected Amenities(Parcel in) {
      this.tv = in.readByte() != 0;
      this.elevator = in.readByte() != 0;
      this.wifi = in.readByte() != 0;
      this.kitchen = in.readByte() != 0;
    }

    public static final Creator<Amenities> CREATOR = new Creator<Amenities>() {
      @Override
      public Amenities createFromParcel(Parcel source) {
        return new Amenities(source);
      }

      @Override
      public Amenities[] newArray(int size) {
        return new Amenities[size];
      }
    };
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.country);
    dest.writeString(this.city);
    dest.writeStringArray(this.imageUrl);
    dest.writeInt(this.numberRooms);
    dest.writeInt(this.numberBeds);
    dest.writeInt(this.numberBath);
    dest.writeInt(this.numberMaxGuests);
    dest.writeInt(this.priceDollars);
    dest.writeString(this.howDay);
    dest.writeString(this.description);
    dest.writeByte(this.growth ? (byte) 1 : (byte) 0);
    dest.writeInt(this.numberLikes);
    dest.writeParcelable(this.amenities, flags);
  }

  private Rent(Parcel in) {
    this.name = in.readString();
    this.country = in.readString();
    this.city = in.readString();
    this.imageUrl = in.createStringArray();
    this.numberRooms = in.readInt();
    this.numberBeds = in.readInt();
    this.numberBath = in.readInt();
    this.numberMaxGuests = in.readInt();
    this.priceDollars = in.readInt();
    this.howDay = in.readString();
    this.description = in.readString();
    this.growth = in.readByte() != 0;
    this.numberLikes = in.readInt();
    this.amenities = in.readParcelable(Amenities.class.getClassLoader());
  }

  public static final Creator<Rent> CREATOR = new Creator<Rent>() {
    @Override
    public Rent createFromParcel(Parcel source) {
      return new Rent(source);
    }

    @Override
    public Rent[] newArray(int size) {
      return new Rent[size];
    }
  };
}
