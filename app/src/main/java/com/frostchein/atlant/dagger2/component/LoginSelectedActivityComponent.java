package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.login_selected.LoginSelectedActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.LoginSelectedActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = LoginSelectedActivityModule.class
)
public interface LoginSelectedActivityComponent {

  void inject(LoginSelectedActivity activity);
}

