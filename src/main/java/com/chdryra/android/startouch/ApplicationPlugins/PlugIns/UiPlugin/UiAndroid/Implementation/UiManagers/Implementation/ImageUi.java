/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ImageUi extends SimpleViewUi<ImageView, Bitmap> {
    public ImageUi(ImageView view) {
        super(view);
    }

    @Override
    public void update(@Nullable Bitmap value) {
        getView().setImageBitmap(value);
    }

    @Override
    Bitmap getValue() {
        return ((BitmapDrawable)getView().getDrawable()).getBitmap();
    }
}
