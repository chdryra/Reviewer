/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverRvUi extends CoverUi{
    private final ReviewView mReviewView;
    private final GridViewUi<?, ?> mGridView;

    public CoverRvUi(ReviewView reviewView, ImageView view, GridViewUi<?, ?> gridView) {
        super(view, new ReferenceValueGetter<Bitmap>() {
            @Override
            @Nullable
            public Bitmap getValue() {
                return null;
            }
        });
        mReviewView = reviewView;
        mGridView = gridView;
    }

    @Override
    public void update() {
        mReviewView.updateCover();
    }

    @Override
    public void setViewValue(@Nullable Bitmap image) {
        super.setViewValue(image);
        if (image != null) {
            mGridView.setTransparent();
        } else {
            mGridView.setOpaque();
        }
    }
}
