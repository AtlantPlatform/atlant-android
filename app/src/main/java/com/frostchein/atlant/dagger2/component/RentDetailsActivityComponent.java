package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.rent_details.RentDetailsActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.RentDetailsActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = RentDetailsActivityModule.class
)
public interface RentDetailsActivityComponent {

  void inject(RentDetailsActivity activity);
}

