/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.drawer_menu;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import atlant.wallet.R;
import io.atlant.wallet.utils.FontsUtils;
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
