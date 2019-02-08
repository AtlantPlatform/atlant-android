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
