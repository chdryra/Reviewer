/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverUi extends ImageUi {
    private final GridViewUi<?, ?> mGridView;

    public CoverUi(ImageView view, GridViewUi<?, ?> gridView) {
        super(view);
        mGridView = gridView;
    }

    @Override
    public void update(@Nullable Bitmap image) {
        super.update(image);
        if (image != null) {
            mGridView.setTransparent();
        } else {
            mGridView.setOpaque();
        }
    }
}
