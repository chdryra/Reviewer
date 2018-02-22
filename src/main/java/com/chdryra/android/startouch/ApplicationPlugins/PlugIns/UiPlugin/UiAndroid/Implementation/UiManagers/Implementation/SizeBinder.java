/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.widget.Button;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class SizeBinder implements DataReference.ValueSubscriber<Size> {
    private final DataReference<Size> mSize;
    private final Button mView;
    private final String mSizeType;

    public SizeBinder(DataReference<Size> size, Button view, String sizeType) {
        mSize = size;
        mView = view;
        mSizeType = sizeType;
    }

    @Override
    public void onReferenceValue(Size value) {
        setText(value.getSize());
    }

    @Override
    public void onInvalidated(DataReference<Size> reference) {
        setText(0);
    }

    private void setText(int number) {
        String text = String.valueOf(number) + "\n" + mSizeType;
        mView.setText(text);
    }

    public void unbind() {
        mSize.unsubscribe(this);
    }

    public void bind() {
        mView.setText(Strings.EditTexts.FETCHING);
        mSize.subscribe(this);
    }
}
