/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CoverUi extends SimpleViewUi<ImageView, Bitmap> {
    public CoverUi(final ImageView view, ReferenceValueGetter<Bitmap> getter) {
        super(view, getter, new ViewValueGetter<Bitmap>() {
            @Override
            public Bitmap getValue() {
                return ((BitmapDrawable)view.getDrawable()).getBitmap();
            }
        }, new ViewValueSetter<Bitmap>() {
            @Override
            public void setValue(Bitmap value) {
                view.setImageBitmap(value);
            }
        });
    }
}
