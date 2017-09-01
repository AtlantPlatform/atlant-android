package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.import_wallet.ImportActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.ImportActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ImportActivityModule.class
)
public interface ImportActivityComponent {

  void inject(ImportActivity activity);
}

