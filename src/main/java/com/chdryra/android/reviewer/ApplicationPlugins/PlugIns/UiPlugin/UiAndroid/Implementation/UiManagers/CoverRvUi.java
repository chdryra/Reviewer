/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverRvUi extends CoverUi{
    private final ReviewView mReviewView;
    private final DataViewUi<?, ?>  mGridView;

    public CoverRvUi(ReviewView reviewView, ImageView view, DataViewUi<?, ?> gridView) {
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
        if (image != null) {
            super.setViewValue(image);
            mGridView.setTransparent();
        } else {
            mGridView.setOpaque();
        }
    }
}
