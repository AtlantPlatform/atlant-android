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


  public static final int ITEM_WALLET = R.string.login_selected_app_invest_title;
  public static final int ITEM_RENT = R.string.login_selected_app_rent_title;
  public static final int ITEM_TRADE = R.string.login_selected_app_trade_title;

  /**
   * Section app
   */
  private static SectionDrawerItem sectionApp = new SectionDrawerItem()
      .withName(R.string.material_drawer_app)
      .withTypeface(Typeface.MONOSPACE)
      .withDivider(false);

  private static PrimaryDrawerItem walletItem = new PrimaryDrawerItem()
      .withName(ITEM_WALLET)
      .withSelectable(false)
      .withEnabled(false)
      .withIcon(R.mipmap.ic_wallet)
      .withIconTintingEnabled(true);

  private static PrimaryDrawerItem rentItem = new PrimaryDrawerItem()
      .withName(ITEM_RENT)
      .withSelectable(false)
      .withIcon(R.mipmap.ic_p2p_rentals);

  private static PrimaryDrawerItem tradeItem = new PrimaryDrawerItem()
      .withName(ITEM_TRADE)
      .withSelectable(false)
      .withIcon(R.mipmap.ic_trade);

  /**
   * Section action
   */
  private static SectionDrawerItem sectionAction = new SectionDrawerItem()
      .withName(R.string.material_drawer_actions)
      .withTypeface(Typeface.MONOSPACE)
      .withDivider(true);

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
    walletItem.withIconColorRes(R.color.colorAccent);
    return new IDrawerItem[]{
        sectionApp, walletItem, rentItem, tradeItem,
        sectionAction, sendItem, receiveItem,
        sectionSettings, settingsItem};
  }
}
