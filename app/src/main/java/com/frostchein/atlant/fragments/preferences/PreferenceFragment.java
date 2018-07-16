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

package com.frostchein.atlant.fragments.preferences;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.frostchein.atlant.R;

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
