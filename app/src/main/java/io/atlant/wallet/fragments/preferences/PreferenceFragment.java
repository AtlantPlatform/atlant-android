/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.fragments.preferences;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import atlant.wallet.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {

  public interface Callback {

    void changePassword();

    void exportWallet();

    void importWallet();

    void deleteWallet();
  }

  private Callback callback;

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  public static PreferenceFragment newInstance() {
    PreferenceFragment fragment = new PreferenceFragment();
    fragment.setArguments(new Bundle());
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref);
    addPreferencesClickListener();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    assert view != null;
    view.setBackgroundResource(R.drawable.highlight);
    return view;
  }

  private void addPreferencesClickListener() {
    Preference preferenceChangePin = findPreference(getString(R.string.pref_security_pin));
    preferenceChangePin.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference pref) {
        if (callback != null) {
          callback.changePassword();
        }
        return true;
      }
    });

    Preference preferenceImport = findPreference(getString(R.string.pref_control_import));
    preferenceImport.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference pref) {
        if (callback != null) {
          callback.importWallet();
        }
        return true;
      }
    });

    Preference preferenceExport = findPreference(getString(R.string.pref_control_export));
    preferenceExport.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference pref) {
        if (callback != null) {
          callback.exportWallet();
        }
        return true;
      }
    });

    Preference preferenceDelete = findPreference(getString(R.string.pref_control_delete));
    preferenceDelete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference pref) {
        if (callback != null) {
          callback.deleteWallet();
        }
        return true;
      }
    });

    Preference preferenceVersion = findPreference(getString(R.string.pref_about_app_version));
    preferenceVersion.setTitle(getVersionApp(getActivity()));
  }

  private String getVersionApp(Context context) {
    try {
      return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
      return "0";
    }
  }
}
