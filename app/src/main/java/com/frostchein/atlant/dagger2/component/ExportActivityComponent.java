package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.export_wallet.ExportActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.ExportActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ExportActivityModule.class
)
public interface ExportActivityComponent {

  void inject(ExportActivity activity);
}

