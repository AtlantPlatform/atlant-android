/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.send.SendActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.SendActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.send.SendActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = SendActivityModule.class
)
public interface SendActivityComponent {

  void inject(SendActivity activity);
}

