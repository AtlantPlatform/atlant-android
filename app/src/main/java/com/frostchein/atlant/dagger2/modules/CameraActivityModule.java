package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.camera.CameraPresenter;
import com.frostchein.atlant.activities.camera.CameraPresenterImpl;
import com.frostchein.atlant.activities.camera.CameraView;
import dagger.Module;
import dagger.Provides;

@Module
public class CameraActivityModule {

  private CameraView view;

  public CameraActivityModule(CameraView view) {
    this.view = view;
  }

  @Provides
  CameraView provideView() {
    return view;
  }

  @Provides
  CameraPresenter providePresenter(CameraPresenterImpl presenter) {
    return presenter;
  }

}
