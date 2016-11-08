/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalGridUi<T extends HasReviewId> extends ViewUi<RecyclerView, RefDataList<T>>{
    public HorizontalGridUi(Context context,
                            RecyclerView view,
                            HorizontalAdapterRef<T, ?, ?> adapter,
                            int span,
                            Command onClick) {
        this(context, view, adapter, span);
        getView().addOnItemTouchListener(new TouchIsClickListener(onClick));
    }

    public HorizontalGridUi(Context context,
                            RecyclerView view,
                            HorizontalAdapterRef<T, ?, ?> adapter,
                            int span) {
        super(view, adapter);
        getView().setHasFixedSize(true);
        GridLayoutManager layout
                = new GridLayoutManager(context, span, GridLayoutManager.HORIZONTAL, false);

        getView().setLayoutManager(layout);
        getView().setAdapter(adapter);
    }

    @Override
    public void update() {

    }
}
