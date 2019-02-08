/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
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
