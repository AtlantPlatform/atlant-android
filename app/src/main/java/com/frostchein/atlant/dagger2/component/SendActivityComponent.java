package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.send.SendActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.SendActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = SendActivityModule.class
)
public interface SendActivityComponent {

  void inject(SendActivity activity);
}

