package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.rent_main.RentMainActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.RentMainActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = RentMainActivityModule.class
)
public interface RentMainActivityComponent {

  void inject(RentMainActivity activity);
}

