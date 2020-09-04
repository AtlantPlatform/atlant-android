/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;

import atlant.wallet.BuildConfig;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerAppComponent;
import io.atlant.wallet.dagger2.modules.AppModule;
import net.danlew.android.joda.JodaTimeAndroid;

import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.modules.AppModule;

public class MyApplication extends Application {

  private AppComponent appComponent;

  public static MyApplication get(Context context) {
    return (MyApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();


    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }


    JodaTimeAndroid.init(this);
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
    appComponent.inject(this);
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
