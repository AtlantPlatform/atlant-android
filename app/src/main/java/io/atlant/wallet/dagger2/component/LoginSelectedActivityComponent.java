/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.login_selected.LoginSelectedActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.LoginSelectedActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.login_selected.LoginSelectedActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = LoginSelectedActivityModule.class
)
public interface LoginSelectedActivityComponent {

  void inject(LoginSelectedActivity activity);
}

