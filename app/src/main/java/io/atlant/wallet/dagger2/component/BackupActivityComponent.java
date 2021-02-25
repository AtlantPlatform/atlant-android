/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.activities.backup.BackupActivity;
import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.BackupActivityModule;
import dagger.Component;
import io.atlant.wallet.activities.backup.BackupActivity;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = BackupActivityModule.class
)
public interface BackupActivityComponent {

  void inject(BackupActivity activity);
}

