package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.login.LoginActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.LoginActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = LoginActivityModule.class
)
public interface LoginActivityComponent {

  void inject(LoginActivity activity);
}

