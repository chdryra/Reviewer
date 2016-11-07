/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverRefUi extends ViewUi<View, ReviewItemReference<DataImage>>{
    private final Activity mActivity;

    public CoverRefUi(View view, ValueGetter<ReviewItemReference<DataImage>> getter, Activity activity) {
        super(view, getter);
        mActivity = activity;
    }

    @Override
    public void update() {
        ReviewItemReference<DataImage> reference = getValue();
        reference.dereference(new DataReference.DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(DataValue<DataImage> value) {
                if(value.hasValue()) setCover(value.getData().getBitmap());
            }
        });
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCover(@Nullable Bitmap image) {
        if (image != null) {
            BitmapDrawable bitmap = new BitmapDrawable(mActivity.getResources(), image);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getView().setBackground(bitmap);
            } else {
                getView().setBackgroundDrawable(bitmap);
            }

        } else {
            getView().setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
