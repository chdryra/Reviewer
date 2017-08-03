/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.mygenerallibrary.Ui.GridItemDecoration;
import com.chdryra.android.mygenerallibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RecyclerViewUi<T extends GvData> implements RecyclerAdapterBasic.OnItemClickListener<T>{
    private final ReviewView<T> mReviewView;
    private final RecyclerView mView;
    private final GridItemAction<T> mClickAction;
    private final CellDimensionsCalculator.Dimensions mDims;

    public RecyclerViewUi(ReviewView<T> reviewView, RecyclerView view, CellDimensionsCalculator calculator) {
        mReviewView = reviewView;
        mView = view;
        ReviewViewParams.GridViewParams params = mReviewView.getParams().getGridViewParams();

        int span = params.getCellWidth().getDivider();
        GridLayoutManager manager = new GridLayoutManager(mView.getContext(), span);

        mView.setLayoutManager(manager);

        mDims = calculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 10);

        mView.addItemDecoration(new GridItemDecoration(span, 10, false));
        mClickAction = mReviewView.getActions().getGridItemAction();

        update();
    }

    public void update() {
        mView.setAdapter(new GvDataAdapter<>(mReviewView.getGridData(), mDims.getCellWidth(), mDims.getCellHeight(), this));
    }

    @Override
    public void onItemClick(T datum, int position, View v) {
        mClickAction.onGridItemClick(datum, position, v);
    }

    @Override
    public void onItemLongClick(T datum, int position, View v) {
        mClickAction.onGridItemLongClick(datum, position, v);
    }

    void setOpaque() {
        setAlpha(ReviewViewParams.GridViewAlpha.OPAQUE.getAlpha());
    }

    void setTransparent() {
        setAlpha(mReviewView.getParams().getGridViewParams().getGridAlpha());
    }

    private void setAlpha(int gridAlpha) {
        Drawable background = mView.getBackground();
        if (background != null) background.setAlpha(gridAlpha);
    }
}
