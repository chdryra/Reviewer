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
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageUi extends SimpleViewUi<ImageView, Bitmap> {
    private final int mPlaceholder;

    public ImageUi(ImageView view) {
        super(view);
        mPlaceholder = -1;
    }

    public ImageUi(ImageView view, int placeholder, @Nullable Command onClick) {
        super(view, onClick);
        mPlaceholder = placeholder;
    }

    @Override
    public void update(@Nullable Bitmap value) {
        if (value != null) {
            getView().setImageBitmap(value);
        } else {
            setPlaceholder();
        }
    }

    @Override
    Bitmap getValue() {
        return ((BitmapDrawable) getView().getDrawable()).getBitmap();
    }

    private void setPlaceholder() {
        if (mPlaceholder != -1) {
            getView().setImageResource(mPlaceholder);
        } else {
            getView().setImageBitmap(null);
        }
    }
}
