/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import android.content.Context;
import io.atlant.wallet.MyApplication;
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
