package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.settings.SettingsActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.SettingsActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = SettingsActivityModule.class
)
public interface SettingsActivityComponent {

  void inject(SettingsActivity activity);
}

