package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.backup.BackupActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.BackupActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = BackupActivityModule.class
)
public interface BackupActivityComponent {

  void inject(BackupActivity activity);
}

