package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.rent_start.RentStartActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.RentStartActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = RentStartActivityModule.class
)
public interface RentStartActivityComponent {

  void inject(RentStartActivity activity);
}

