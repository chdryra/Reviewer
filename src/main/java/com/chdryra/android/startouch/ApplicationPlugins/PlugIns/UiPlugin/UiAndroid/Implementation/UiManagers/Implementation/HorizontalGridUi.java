/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalGridUi<T extends HasReviewId> extends ViewUi<RecyclerView, IdableList<T>> {
    private final GvDataAdapterBindable<T, ?, ?> mAdapter;

    public HorizontalGridUi(Context context,
                            RecyclerView view,
                            GvDataAdapterBindable<T, ?, ?> adapter,
                            int span,
                            @Nullable Command onClick) {
        super(view);
        mAdapter = adapter;
        getView().setHasFixedSize(true);
        GridLayoutManager layout
                = new GridLayoutManager(context, span, GridLayoutManager.HORIZONTAL, false);

        getView().setLayoutManager(layout);
        getView().setAdapter(mAdapter);
        if (onClick != null) getView().addOnItemTouchListener(new TouchIsClickListener(onClick));
    }

    @Override
    public void update(IdableList<T> value) {
        mAdapter.update(value);
    }

    @Override
    public void onInvalidated() {
        mAdapter.onInvalidated();
    }
}
