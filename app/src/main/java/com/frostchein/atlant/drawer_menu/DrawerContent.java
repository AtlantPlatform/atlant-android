package com.frostchein.atlant.drawer_menu;

import android.graphics.Typeface;
import com.frostchein.atlant.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public final class DrawerContent {

  public static final int ITEM_SEND = R.string.material_drawer_send;
  public static final int ITEM_RECEIVE = R.string.material_drawer_receive;
  public static final int ITEM_SETTINGS = R.string.material_drawer_general;

  /**
   * Section action
   */
  private static SectionDrawerItem sectionAction = new SectionDrawerItem()
      .withName(R.string.material_drawer_actions)
      .withTypeface(Typeface.MONOSPACE)
      .withDivider(false);

  private static PrimaryDrawerItem sendItem = new PrimaryDrawerItem()
      .withName(ITEM_SEND)
      .withSelectable(false)
      .withIcon(R.mipmap.ic_send);

  private static PrimaryDrawerItem receiveItem = new PrimaryDrawerItem()
      .withName(ITEM_RECEIVE)
      .withSelectable(false)
      .withIcon(R.mipmap.ic_receive);

  /**
   * Section setting
   */
  private static SectionDrawerItem sectionSettings = new SectionDrawerItem()
      .withName(R.string.material_drawer_settings)
      .withTypeface(Typeface.MONOSPACE)
      .withDivider(true);

  private static PrimaryDrawerItem settingsItem = new PrimaryDrawerItem()
      .withName(ITEM_SETTINGS)
      .withSelectable(false)
      .withIcon(R.mipmap.ic_settings);

  public static IDrawerItem[] getDrawerContent() {
    return new IDrawerItem[]{
        sectionAction, sendItem, receiveItem,
        sectionSettings, settingsItem};
  }
}
