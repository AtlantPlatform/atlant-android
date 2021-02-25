/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.receive.ReceiveActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.ReceiveActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.receive.ReceiveActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ReceiveActivityModule.class
)
public interface ReceiveActivityComponent {

  void inject(ReceiveActivity activity);
}

