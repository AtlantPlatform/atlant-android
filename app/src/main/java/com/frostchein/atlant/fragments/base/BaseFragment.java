package com.frostchein.atlant.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.frostchein.atlant.MyApplication;
import com.frostchein.atlant.dagger2.component.AppComponent;

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
