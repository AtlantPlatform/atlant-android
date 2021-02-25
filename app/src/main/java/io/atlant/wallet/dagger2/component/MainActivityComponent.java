/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.main.MainActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.MainActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.main.MainActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = MainActivityModule.class
)
public interface MainActivityComponent {

  void inject(MainActivity activity);
}

