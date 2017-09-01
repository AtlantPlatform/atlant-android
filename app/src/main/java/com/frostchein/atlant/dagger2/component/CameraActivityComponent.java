package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.camera.CameraActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.CameraActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = CameraActivityModule.class
)
public interface CameraActivityComponent {

  void inject(CameraActivity activity);
}

