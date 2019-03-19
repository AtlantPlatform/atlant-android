/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.MyApplication;
import io.atlant.wallet.dagger2.modules.AppModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MyApplication myApp);

}
