/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GridViewUi<T extends GvData> {
    private final ReviewView<T> mReviewView;
    private final GridView mView;
    private final FactoryGridCellAdapter mFactory;
    private final CellDimensionsCalculator mCalculator;

    public GridViewUi(ReviewView<T> reviewView,
                      GridView view,
                      FactoryGridCellAdapter factory,
                      CellDimensionsCalculator calculator) {
        mReviewView = reviewView;
        mView = view;
        mFactory = factory;
        mCalculator = calculator;
        inititialise();
    }

    public void update() {
        getAdapter().setData(mReviewView.getGridData());
    }

    private ViewHolderAdapter getAdapter() {
        return (ViewHolderAdapter) mView.getAdapter();
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

    private void inititialise() {
        ReviewViewParams.GridViewParams params = mReviewView.getParams().getGridViewParams();
        CellDimensionsCalculator.Dimensions dims
                = mCalculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 0);
        int cell_width = dims.getCellWidth();
        int cell_height = dims.getCellHeight();

        mView.setDrawSelectorOnTop(true);
        mView.setAdapter(mFactory.newAdapter(mReviewView.getGridData(), cell_width, cell_height));
        mView.setColumnWidth(cell_width);
        mView.setNumColumns(params.getCellWidth().getDivider());

        GridItemAction<T> action = mReviewView.getActions().getGridItemAction();
        mView.setOnItemClickListener(newGridItemClickListener(action));
        mView.setOnItemLongClickListener(newGridItemLongClickListener(action));
    }

    @NonNull
    private AdapterView.OnItemLongClickListener newGridItemLongClickListener(final
                                                                             GridItemAction<T>
                                                                                         action) {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemLongClick(getItem(parent, position), position, v);
                return true;
            }
        };
    }

    @NonNull
    private AdapterView.OnItemClickListener newGridItemClickListener(final GridItemAction<T>
                                                                             action) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemClick(getItem(parent, position), position, v);
            }
        };
    }

    private T getItem(AdapterView<?> parent, int position) {
        //TODO make type safe
        return (T) parent.getItemAtPosition(position);
    }

}
