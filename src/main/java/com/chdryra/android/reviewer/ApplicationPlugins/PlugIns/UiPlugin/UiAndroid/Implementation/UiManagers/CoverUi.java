/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CoverUi<T> extends ViewUi<ImageView, T>{
    public CoverUi(ImageView view, ValueGetter<T> getter) {
        super(view, getter);
    }

    public void setCover(@Nullable Bitmap image) {
        getView().setImageBitmap(image);
    }
}
