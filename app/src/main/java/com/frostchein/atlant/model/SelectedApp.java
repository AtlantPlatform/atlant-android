package com.frostchein.atlant.model;

public class SelectedApp {

  private String title1;
  private String title2;
  private String url;

  public SelectedApp(String title1, String title2, String url) {
    this.title1 = title1;
    this.title2 = title2;
    this.url = url;
  }

  public String getTitle1() {
    return title1;
  }

  public void setTitle1(String title1) {
    this.title1 = title1;
  }

  public String getTitle2() {
    return title2;
  }

  public void setTitle2(String title2) {
    this.title2 = title2;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
