package com.frostchein.atlant.dagger2.modules;

import android.content.Context;
import com.frostchein.atlant.MyApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {

  private final MyApplication myApp;

  public AppModule(MyApplication myApp) {
    this.myApp = myApp;
  }

  @Provides
  @Singleton
  Context providesApplication() {
    return myApp;
  }

}
