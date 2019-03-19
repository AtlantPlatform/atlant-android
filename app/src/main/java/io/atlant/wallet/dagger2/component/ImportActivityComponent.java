/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.import_wallet.ImportActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.ImportActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.import_wallet.ImportActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ImportActivityModule.class
)
public interface ImportActivityComponent {

  void inject(ImportActivity activity);
}

