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

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageBinder<T> implements ReferenceBinder<T> {
    private final DataReference<T> mReference;
    private final BitmapGetter<T> mGetter;
    private final ImageView mView;
    private final int mPlaceholder;

    public interface BitmapGetter<T> {
        @Nullable
        Bitmap getBitmap(T value);
    }

    public ImageBinder(DataReference<T> reference, ImageView view, int placeholder,
                       BitmapGetter<T> getter) {
        mReference = reference;
        mGetter = getter;
        mView = view;
        mPlaceholder = placeholder;
    }

    @Override
    public void bind() {
        setPlaceholder();
        mReference.subscribe(this);
    }

    @Override
    public void unbind() {
        setPlaceholder();
        mReference.unsubscribe(this);
    }

    @Override
    public void onReferenceValue(T value) {
        Bitmap bitmap = mGetter.getBitmap(value);
        if (bitmap != null) {
            mView.setImageBitmap(bitmap);
        } else {
            setPlaceholder();
        }
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        setPlaceholder();
        unbind();
    }

    private void setPlaceholder() {
        mView.setImageResource(mPlaceholder);
    }
}
