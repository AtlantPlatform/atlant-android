/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.login.LoginActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerSettingsActivityComponent;
import io.atlant.wallet.dagger2.component.SettingsActivityComponent;
import io.atlant.wallet.dagger2.modules.SettingsActivityModule;
import io.atlant.wallet.events.login.CredentialsCleared;
import io.atlant.wallet.fragments.preferences.PreferenceFragment;
import io.atlant.wallet.utils.DialogUtils;
import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements SettingsView {

  @Inject
  SettingsPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    setToolbarTitle(R.string.material_drawer_settings);
  }

  @Override
  protected void initUI() {
    PreferenceFragment fragment = PreferenceFragment.newInstance();
    getFragmentManager().beginTransaction()
        .replace(R.id.settings_fragment_content_frame, fragment, PreferenceFragment.class.getName())
        .commit();

    fragment.setCallback(new PreferenceFragment.Callback() {
      @Override
      public void changePassword() {
        goToLoginActivity(false, LoginActivity.TYPE_CHANGE_PASSWORD);
      }

      @Override
      public void exportWallet() {
        goToLoginActivity(false, LoginActivity.TYPE_AUTHORISATION_EXPORT);
      }

      @Override
      public void importWallet() {
        DialogUtils.openDialogChoice(getContext(), R.string.app_name,
            getString(R.string.pref_dialog_import), R.string.bt_dialog_continue,
            R.string.bt_dialog_back, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                goToImportWalletActivity(false);
              }
            });
      }

      @Override
      public void deleteWallet() {
        DialogUtils.openDialogChoice(getContext(), R.string.app_name,
            getString(R.string.pref_dialog_delete), R.string.bt_dialog_continue,
            R.string.bt_dialog_back, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                presenter.onDeleteWallet();
              }
            });
      }
    });
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    SettingsActivityComponent component = DaggerSettingsActivityComponent.builder()
        .appComponent(appComponent)
        .settingsActivityModule(new SettingsActivityModule(this))
        .build();
    component.inject(this);
  }

  @Override
  public boolean useToolbar() {
    return true;
  }

  @Override
  public boolean useDrawer() {
    return false;
  }

  @Override
  public boolean useToolbarActionHome() {
    return true;
  }

  @Override
  public boolean useToolbarActionQRCode() {
    return false;
  }

  @Override
  public boolean useCustomToolbar() {
    return false;
  }

  @Override
  public boolean useSwipeRefresh() {
    return false;
  }

  @Override
  public boolean timerLogOut() {
    return true;
  }

  @Override
  public void restartApp() {
    onCredentialsCleared(new CredentialsCleared());
  }
}
