package com.frostchein.atlant.activities.backup;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface BackupPresenter extends BasePresenter {

  void onCreate();

  void onDone();

  void checked(boolean bol);

}
