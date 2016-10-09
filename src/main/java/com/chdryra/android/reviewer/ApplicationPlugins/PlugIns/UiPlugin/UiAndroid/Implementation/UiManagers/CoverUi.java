/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverUi {
    private final ReviewView mReviewView;
    private final RelativeLayout mView;
    private final GridViewUi mGridView;
    private final Activity mActivity;

    public CoverUi(ReviewView reviewView, RelativeLayout view, GridViewUi gridView, Activity
            activity) {
        mReviewView = reviewView;
        mView = view;
        mGridView = gridView;
        mActivity = activity;
    }

    public void update() {
        mReviewView.updateCover();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCover(@Nullable DataImage cover) {
        if (cover != null && cover.getBitmap() != null) {
            BitmapDrawable bitmap = new BitmapDrawable(mActivity.getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mView.setBackground(bitmap);
            } else {
                mView.setBackgroundDrawable(bitmap);
            }
            mGridView.setTransparent();
        } else {
            removeCover();
        }
    }

    private void removeCover() {
        mView.setBackgroundColor(Color.TRANSPARENT);
        mGridView.setOpaque();
    }
}
