/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.export_wallet.ExportActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.ExportActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.export_wallet.ExportActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ExportActivityModule.class
)
public interface ExportActivityComponent {

  void inject(ExportActivity activity);
}

