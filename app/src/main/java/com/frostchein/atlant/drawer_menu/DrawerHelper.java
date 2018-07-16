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

package com.frostchein.atlant.drawer_menu;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.utils.FontsUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import javax.inject.Inject;

public final class DrawerHelper {

  private Drawer drawer;

  @Inject
  public DrawerHelper(final AppCompatActivity activity) {

    View view = LayoutInflater.from(activity).inflate(R.layout.view_menu_header, null);
    TextView textTitle = view.findViewById(R.id.menu_title);
    FontsUtils.toOctarineBold(activity, textTitle);

    drawer = new DrawerBuilder()
        .withActivity(activity)
        .withSliderBackgroundDrawableRes(R.drawable.fon_gradient)
        .withHeader(R.layout.view_menu_header)
        .withHeader(view)
        .withActionBarDrawerToggle(true)
        .withStickyFooter(R.layout.view_menu_footer)
        .withStickyFooterDivider(false)
        .build();

    if (activity instanceof Drawer.OnDrawerItemClickListener) {
      drawer.setOnDrawerItemClickListener((Drawer.OnDrawerItemClickListener) activity);
    }
  }

  public void setDrawerContent(IDrawerItem... drawerItems) {
    drawer.removeAllItems();
    drawer.addItems(drawerItems);
  }

  public void setDisableState() {
    drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
  }

  public void setEnableState() {
    drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
  }

  public void setListener(Drawer.OnDrawerItemClickListener listener) {
    drawer.setOnDrawerItemClickListener(listener);
  }

  public void setSelectionByNameId(int position) {
    drawer.setSelectionAtPosition(position);
  }

  public int getDrawerItemResourceInPosition(int position) {
    if (drawer.getHeader() == null) {
      return ((Nameable) drawer.getDrawerItems().get(position)).getName().getTextRes();
    } else {
      return ((Nameable) drawer.getDrawerItems().get(position - 1)).getName().getTextRes();
    }
  }

  public void setToolbar(AppCompatActivity activity, Toolbar toolbar) {
    drawer.setToolbar(activity, toolbar);
  }

  public void closeDrawer() {
    drawer.closeDrawer();
  }

  public boolean isDrawerOpen() {
    return drawer.isDrawerOpen();
  }
}
