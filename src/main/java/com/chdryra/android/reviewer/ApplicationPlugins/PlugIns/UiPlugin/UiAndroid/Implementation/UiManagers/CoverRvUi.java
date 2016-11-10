/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverRvUi extends CoverUi<Bitmap>{
    private final ReviewView mReviewView;
    private final GridViewUi mGridView;

    public CoverRvUi(ReviewView reviewView, RelativeLayout view, GridViewUi gridView, Activity
            activity) {
        super(view, new ValueGetter<Bitmap>() {
            @Override
            @Nullable
            public Bitmap getValue() {
                return null;
            }
        }, activity);
        mReviewView = reviewView;
        mGridView = gridView;
    }

    @Override
    public void update() {
        mReviewView.updateCover();
    }

    @Override
    public void setCover(@Nullable Bitmap image) {
        super.setCover(image);
        if (image != null) {
            mGridView.setTransparent();
        } else {
            mGridView.setOpaque();
        }
    }
}
