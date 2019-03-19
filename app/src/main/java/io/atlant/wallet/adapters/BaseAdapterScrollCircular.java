/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.adapters;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

abstract class BaseAdapterScrollCircular<T> extends PagerAdapter {

  private ArrayList<T> arrayList;
  private boolean animation = false;
  private int positionChange;
  private boolean positionChangeSuccess;

  BaseAdapterScrollCircular(ArrayList<T> arrayList) {
    arrayList.add(0, arrayList.get(arrayList.size() - 1));
    arrayList.add(arrayList.size(), arrayList.get(1));
    this.arrayList = arrayList;
  }

  @Override
  public Object instantiateItem(final ViewGroup container, int position) {
    ((ViewPager) container).addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        if (position == 0) {
          positionChangeSuccess = true;
          positionChange = position;
        }
        if (position == getCount() - 1) {
          positionChangeSuccess = true;
          positionChange = position;
        }

      }

      @Override
      public void onPageScrollStateChanged(int state) {

        if (positionChange == 0 && positionChangeSuccess) {
          positionChangeSuccess = false;
          ((ViewPager) container).setCurrentItem(getCount() - 2, animation);
        }
        if (positionChange == getCount() - 1 && positionChangeSuccess) {
          positionChangeSuccess = false;
          ((ViewPager) container).setCurrentItem(1, animation);
        }
      }
    });
    return createView(container, position);
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view.equals(object);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return arrayList.size();
  }

  abstract public View createView(ViewGroup container, int position);

}
