package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.backup.BackupActivity;
import com.frostchein.atlant.activities.main.MainActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.BackupActivityModule;
import com.frostchein.atlant.dagger2.modules.MainActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = BackupActivityModule.class
)
public interface BackupActivityComponent {

  void inject(BackupActivity activity);
}

