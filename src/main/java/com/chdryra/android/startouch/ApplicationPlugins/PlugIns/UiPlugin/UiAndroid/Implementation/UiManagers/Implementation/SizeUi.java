/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeImpl;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class SizeUi extends SimpleViewUi<TextView, Size> {
    private int mSize;
    private final String mSizeType;

    public SizeUi(TextView view, String sizeType, @Nullable Command onClick) {
        super(view, onClick);
        mSizeType = sizeType;
    }

    @Override
    public void update(Size value) {
        mSize = value.getSize();
        String text = String.valueOf(mSize) + "\n" + mSizeType;
        getView().setText(text);
    }

    @Override
    Size getValue() {
        return new SizeImpl(mSize);
    }
}
