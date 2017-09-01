package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.home.HomeActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.HomeActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = HomeActivityModule.class
)
public interface HomeActivityComponent {

  void inject(HomeActivity activity);
}

