package com.chdryra.android.reviewer.View.Screens;

import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAction extends ReviewViewAction {
    private String mTitle;

    //Constructors
    public BannerButtonAction() {
    }

    public BannerButtonAction(String title) {
        mTitle = title;
    }

    //Static methods
    public static BannerButtonAction newDisplayButton(final String title) {
        return new BannerButtonAction(title) {
        };
    }

    //public methods
    public String getButtonTitle() {
        return mTitle;
    }

    public void onClick(View v) {
    }

    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }
}
