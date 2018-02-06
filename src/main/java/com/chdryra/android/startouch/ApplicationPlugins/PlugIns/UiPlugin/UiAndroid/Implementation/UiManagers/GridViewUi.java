/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class GridViewUi<V extends View, T extends GvData> extends ViewUi<V, GvDataList<T>> {
    public GridViewUi(V view,
                      ReferenceValueGetter<GvDataList<T>> getter) {
        super(view, getter);
    }

    void setOpaque() {
        setBackgroundAlpha(ReviewViewParams.Alpha.OPAQUE.getAlpha());
    }

    void setTransparent() {
        setBackgroundAlpha(ReviewViewParams.Alpha.TRANSPARENT.getAlpha());
    }
}
