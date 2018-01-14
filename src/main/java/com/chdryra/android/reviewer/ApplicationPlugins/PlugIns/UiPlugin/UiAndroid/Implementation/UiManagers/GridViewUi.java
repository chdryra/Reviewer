/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class GridViewUi<V extends View, T extends GvData> extends ViewUi<V, GvDataList<T>> {

    private final ReviewViewParams.GridView mParams;

    public GridViewUi(V view,
                      ReviewViewParams.GridView params,
                      ReferenceValueGetter<GvDataList<T>> getter) {
        super(view, getter);
        mParams = params;
    }

    void setOpaque() {
        setBackgroundAlpha(ReviewViewParams.Alpha.OPAQUE.getAlpha());
    }

    void setTransparent() {
        setBackgroundAlpha(ReviewViewParams.Alpha.TRANSPARENT.getAlpha());
    }
}
