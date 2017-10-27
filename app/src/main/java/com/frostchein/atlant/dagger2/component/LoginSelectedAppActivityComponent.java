package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.login_selected_app.LoginSelectedAppActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.LoginSelectedAppActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = LoginSelectedAppActivityModule.class
)
public interface LoginSelectedAppActivityComponent {

  void inject(LoginSelectedAppActivity activity);
}

