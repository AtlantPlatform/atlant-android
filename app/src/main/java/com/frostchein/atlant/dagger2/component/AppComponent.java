package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.MyApplication;
import com.frostchein.atlant.dagger2.modules.AppModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MyApplication myApp);

}
