/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HideableHorizontalGridUi<T extends GvData> extends HorizontalGridUi<T>{
    private View mParent;

    public HideableHorizontalGridUi(Context context,
                                    View parent,
                                    int recyclerViewId,
                                    ViewHolderFactory<?> vhFactory,
                                    ValueGetter<GvDataList<T>> getter,
                                    int span,
                                    CellDimensionsCalculator.Dimensions dims) {
        super(context, (RecyclerView) parent.findViewById(recyclerViewId), vhFactory, getter, span, dims);
    }

    @Override
    public void update() {
        GvDataList<T> value = getValue();
        if(value.size() == 0) {
            mParent.setVisibility(View.GONE);
        } else {
            super.update();
        }
    }
}
