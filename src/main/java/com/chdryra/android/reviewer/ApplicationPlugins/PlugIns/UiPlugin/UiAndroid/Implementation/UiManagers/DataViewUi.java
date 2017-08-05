/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.drawable.Drawable;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DataViewUi<V extends View, T extends GvData> extends ViewUi<V, GvDataList<T>> {
    private final ReviewView<T> mReviewView;

    public DataViewUi(V view, ReferenceValueGetter<GvDataList<T>> getter, final ReviewView<T>
            reviewView) {
        super(view, getter);
        mReviewView = reviewView;
    }

    void setOpaque() {
        setAlpha(ReviewViewParams.GridViewAlpha.OPAQUE.getAlpha());
    }

    void setTransparent() {
        setAlpha(mReviewView.getParams().getGridViewParams().getGridAlpha());
    }

    private void setAlpha(int gridAlpha) {
        Drawable background = getView().getBackground();
        if (background != null) background.setAlpha(gridAlpha);
    }
}
