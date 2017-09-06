package com.frostchein.atlant.activities.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.login.LoginActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerSettingsActivityComponent;
import com.frostchein.atlant.dagger2.component.SettingsActivityComponent;
import com.frostchein.atlant.dagger2.modules.SettingsActivityModule;
import com.frostchein.atlant.events.login.CredentialsCleared;
import com.frostchein.atlant.fragments.preferences.PreferenceFragment;
import com.frostchein.atlant.utils.DialogUtils;
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
