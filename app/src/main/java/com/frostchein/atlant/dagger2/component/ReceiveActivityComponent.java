package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.receive.ReceiveActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.ReceiveActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ReceiveActivityModule.class
)
public interface ReceiveActivityComponent {

  void inject(ReceiveActivity activity);
}

