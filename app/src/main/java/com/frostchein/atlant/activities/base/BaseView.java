package com.frostchein.atlant.activities.base;

import android.content.Context;

public interface BaseView {

  Context getContext();

  void onRefreshStart();

  void onRefreshComplete();

  void onNoInternetConnection();

  void onScreenError(String text);

  void showProgressDialog(String dialogMessage);

  void hideProgressDialog();
}
