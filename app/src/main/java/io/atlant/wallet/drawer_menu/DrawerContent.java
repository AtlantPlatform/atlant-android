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

package io.atlant.wallet.drawer_menu;

import android.graphics.Typeface;
import atlant.wallet.R;
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
