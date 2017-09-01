package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.main.MainActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.MainActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = MainActivityModule.class
)
public interface MainComponent {

  void inject(MainActivity activity);
}

