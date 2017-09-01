package com.frostchein.atlant.activities.camera;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface CameraPresenter extends BasePresenter {

    void onCreate(int typeResult);

    void onResume();

    void onPause();

}
