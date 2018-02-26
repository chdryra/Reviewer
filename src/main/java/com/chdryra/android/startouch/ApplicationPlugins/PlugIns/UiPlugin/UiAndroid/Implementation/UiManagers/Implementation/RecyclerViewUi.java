/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.corelibrary.Ui.GridItemDecoration;
import com.chdryra.android.corelibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RecyclerViewUi<T extends GvData> extends GridViewUi<RecyclerView, T> implements
        RecyclerAdapterBasic.OnItemClickListener<T>, OptionSelectListener {
    private static final int SPACING = R.dimen.grid_spacing;
    private final GridItemAction<T> mAction;
    private final CellDimensionsCalculator.Dimensions mDims;
    private final GridLayoutManager mManager;

    public RecyclerViewUi(RecyclerView view,
                          GridItemAction<T> action,
                          ReviewViewParams.GridView params,
                          CellDimensionsCalculator calculator) {
        super(view);

        int span = params.getCellWidth().getDivider();
        mManager = new GridLayoutManager(getView().getContext(), span);
        getView().setLayoutManager(mManager);
        int spacing = (int) getView().getContext().getResources().getDimension(SPACING);
        getView().addItemDecoration(new GridItemDecoration(span, spacing, false));
        mDims = calculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 0);

        mAction = action;
    }

    @Override
    public void update(GvDataList<T> value) {
        setAdapter(value);
    }

    @Override
    public void onInvalidated() {

    }

    @Override
    public void onItemClick(T datum, int position, View v) {
        mAction.onGridItemClick(datum, position, v);
    }

    @Override
    public void onItemLongClick(T datum, int position, View v) {
        mAction.onGridItemLongClick(datum, position, v);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        boolean consumed = false;
        int first = mManager.findFirstVisibleItemPosition();
        int last = mManager.findLastVisibleItemPosition();
        for (int i = first; i < last + 1; ++i) {
            GvDataAdapter.ViewHolderOptionable<T> delegate = getClickDelegate(i);
            if (delegate != null) consumed = delegate.onOptionSelected(requestCode, option);
            if (consumed) break;
        }

        return consumed || mAction.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        boolean consumed = false;
        int first = mManager.findFirstVisibleItemPosition();
        int last = mManager.findLastVisibleItemPosition();
        for (int i = first; i < last + 1; ++i) {
            GvDataAdapter.ViewHolderOptionable<T> delegate = getClickDelegate(i);
            if (delegate != null) consumed = delegate.onOptionsCancelled(requestCode);
            if (consumed) break;
        }

        return consumed || mAction.onOptionsCancelled(requestCode);
    }

    private void setAdapter(GvDataList<T> value) {
        getView().setAdapter(new GvDataAdapter<>(value, mDims.getCellWidth(),
                mDims.getCellHeight(), this));
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
