/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CoverBannerUi<T> extends ViewUi<ImageView, T>{
    private final Bitmap mPlaceholder;

    public CoverBannerUi(ImageView view, ValueGetter<T> getter, Bitmap placeholder) {
        super(view, getter);
        mPlaceholder = placeholder;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCover(@Nullable Bitmap image) {
        getView().setImageBitmap(image != null ? image : mPlaceholder);
    }
}
