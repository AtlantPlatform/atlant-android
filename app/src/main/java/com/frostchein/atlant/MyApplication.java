package com.frostchein.atlant;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerAppComponent;
import com.frostchein.atlant.dagger2.modules.AppModule;
import net.danlew.android.joda.JodaTimeAndroid;

public class MyApplication extends Application {

  private AppComponent appComponent;

  public static MyApplication get(Context context) {
    return (MyApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
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
