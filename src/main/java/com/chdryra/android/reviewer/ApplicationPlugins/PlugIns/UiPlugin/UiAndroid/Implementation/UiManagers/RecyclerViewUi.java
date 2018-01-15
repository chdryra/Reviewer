/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.mygenerallibrary.Ui.GridItemDecoration;
import com.chdryra.android.mygenerallibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RecyclerViewUi<T extends GvData> extends GridViewUi<RecyclerView, T> implements
        RecyclerAdapterBasic.OnItemClickListener<T>, OptionSelectListener {
    private static final int SPACING = R.dimen.grid_spacing;
    private final GridItemAction<T> mClickAction;
    private final CellDimensionsCalculator.Dimensions mDims;
    private final GridLayoutManager mManager;

    public RecyclerViewUi(final ReviewView<T> reviewView,
                          RecyclerView view,
                          CellDimensionsCalculator calculator) {
        super(view,
                new ReferenceValueGetter<GvDataList<T>>() {
            @Override
            public GvDataList<T> getValue() {
                return reviewView.getGridData();
            }
        });

        ReviewViewParams.GridView params = reviewView.getParams().getGridViewParams();

        int span = params.getCellWidth().getDivider();
        mManager = new GridLayoutManager(getView().getContext(), span);
        getView().setLayoutManager(mManager);
        int spacing = (int) getView().getContext().getResources().getDimension(SPACING);
        getView().addItemDecoration(new GridItemDecoration(span, spacing, false));
        mDims = calculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 0);

        mClickAction = reviewView.getActions().getGridItemAction();

        update();
    }

    @Override
    public void update() {
        getView().setAdapter(new GvDataAdapter<>(getReferenceValue(), mDims.getCellWidth(),
                mDims.getCellHeight(), this));
    }

    @Override
    public void onItemClick(T datum, int position, View v) {
        mClickAction.onGridItemClick(datum, position, v);
    }

    @Override
    public void onItemLongClick(T datum, int position, View v) {
        mClickAction.onGridItemLongClick(datum, position, v);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        boolean consumed = false;
        int first = mManager.findFirstVisibleItemPosition();
        int last = mManager.findLastVisibleItemPosition();
        for(int i = first; i < last + 1; ++i) {
            GvDataAdapter.ViewHolderOptionable<T> delegate = getClickDelegate(i);
            if(delegate != null) consumed = delegate.onOptionSelected(requestCode, option);
            if(consumed) break;
        }

        return consumed || mClickAction.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        boolean consumed = false;
        int first = mManager.findFirstVisibleItemPosition();
        int last = mManager.findLastVisibleItemPosition();
        for(int i = first; i < last + 1; ++i) {
            GvDataAdapter.ViewHolderOptionable<T> delegate = getClickDelegate(i);
            if(delegate != null) consumed = delegate.onOptionsCancelled(requestCode);
            if(consumed) break;
        }

        return consumed || mClickAction.onOptionsCancelled(requestCode);
    }

    private GvDataAdapter.ViewHolderOptionable<T> getClickDelegate(View v) {
        RecyclerView.ViewHolder vh = getView().getChildViewHolder(v);
        GvDataAdapter.ViewHolderOptionable<T> delegate = null;
        try {
            delegate = (GvDataAdapter.ViewHolderOptionable<T>) vh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delegate;
    }

    private GvDataAdapter.ViewHolderOptionable<T> getClickDelegate(int position) {
        RecyclerView.ViewHolder vh = getView().findViewHolderForAdapterPosition(position);
        GvDataAdapter.ViewHolderOptionable<T> delegate = null;
        try {
            delegate = (GvDataAdapter.ViewHolderOptionable<T>) vh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delegate;
    }
}
