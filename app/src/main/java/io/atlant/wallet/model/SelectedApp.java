/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.model;

public class SelectedApp {

    private String title1;
    private String title2;
    private int resDrawable;

    public SelectedApp(String title1, String title2, int resDrawable) {
        this.title1 = title1;
        this.title2 = title2;
        this.resDrawable = resDrawable;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getResDrawable() {
        return resDrawable;
    }

    public void setResDrawable(int resDrawable) {
        this.resDrawable = resDrawable;
    }
}
