/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.fragments.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.atlant.wallet.MyApplication;
import io.atlant.wallet.dagger2.component.AppComponent;

public abstract class BaseFragment extends Fragment {

  private Unbinder unbinder;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(getLayoutId(), container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
    setupComponent(MyApplication.get(getActivity()).getAppComponent());
    onCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView() {
    unbinder.unbind();
    super.onDestroyView();
  }

  protected abstract void onCreated(View view, Bundle savedInstanceState);

  protected abstract int getLayoutId();

  protected abstract void setupComponent(AppComponent appComponent);
}
