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
import android.util.DisplayMetrics;
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
    private final DisplayMetrics mMetrics;

    public GridViewUi(ReviewView<T> reviewView,
                      GridView view,
                      FactoryGridCellAdapter factory,
                      DisplayMetrics metrics) {
        mReviewView = reviewView;
        mView = view;
        mFactory = factory;
        mMetrics = metrics;
        inititialise();
    }

    public void update() {
        getAdapter().setData(mReviewView.getGridData());
    }

    public void setCellDimension(ReviewViewParams.CellDimension width, ReviewViewParams
            .CellDimension height) {
        int maxCellSize = Math.min(mMetrics.widthPixels, mMetrics.heightPixels);
        int widthDivider = width.getDivider();
        int cell_width = maxCellSize / widthDivider;
        int cell_height = maxCellSize / height.getDivider();
        mView.setColumnWidth(cell_width);
        mView.setNumColumns(widthDivider);
        getAdapter().setCellDimensions(cell_width, cell_height);
    }

    void setOpaque() {
        setAlpha(ReviewViewParams.GridViewAlpha.OPAQUE.getAlpha());
    }

    void setTransparent() {
        setAlpha(mReviewView.getParams().getGridViewParams().getGridAlpha());
    }

    private ViewHolderAdapter getAdapter() {
        return (ViewHolderAdapter) mView.getAdapter();
    }

    private void setAlpha(int gridAlpha) {
        Drawable background = mView.getBackground();
        if (background != null) background.setAlpha(gridAlpha);
    }

    private void inititialise() {
        ReviewViewParams.GridViewParams params = mReviewView.getParams().getGridViewParams();

        int maxCellSize = Math.min(mMetrics.widthPixels, mMetrics.heightPixels);
        int widthDivider = params.getCellWidth().getDivider();
        int cell_width = maxCellSize / widthDivider;
        int cell_height = maxCellSize / params.getCellHeight().getDivider();

        ViewHolderAdapter adapter
                = mFactory.newAdapter(mReviewView.getGridData(), cell_width, cell_height);

        mView.setDrawSelectorOnTop(true);
        mView.setAdapter(adapter);
        mView.setColumnWidth(cell_width);
        mView.setNumColumns(widthDivider);

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
