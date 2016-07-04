/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.app.ActionBar;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GridViewUi {
    private ReviewView<?> mReviewView;
    private GridView mView;
    private FactoryGridCellAdapter mFactory;

    public GridViewUi(ReviewView<?> reviewView, GridView view, FactoryGridCellAdapter factory, Activity activity) {
        mReviewView = reviewView;
        mView = view;
        mFactory = factory;
        inititialise(activity);
    }

    public void update() {
        GvDataList<?> gridViewData = mReviewView.getGridViewData();
        ((ViewHolderAdapter) mView.getAdapter()).setData(gridViewData);
    }

    public void setOpaque() {
        mView.getBackground().setAlpha(ReviewViewParams.GridViewAlpha.OPAQUE.getAlpha());
    }

    public void setTransparent() {
        mView.getBackground().setAlpha(mReviewView.getParams().getGridViewParams().getGridAlpha());
    }

    public void wrap() {
        mView.getLayoutParams().height = ActionBar.LayoutParams.WRAP_CONTENT;
    }

    private void inititialise(Activity activity) {
        ReviewViewParams.GridViewParams params = mReviewView.getParams().getGridViewParams();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int maxCellSize = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        int widthDivider = params.getCellWidth().getDivider();
        int cell_width = maxCellSize / widthDivider;
        int cell_height = maxCellSize / params.getCellHeight().getDivider();

        ViewHolderAdapter adapter = mFactory.newAdapter(activity,
                mReviewView.getGridViewData(), cell_width, cell_height);
        mView.setDrawSelectorOnTop(true);
        mView.setAdapter(adapter);
        mView.setColumnWidth(cell_width);
        mView.setNumColumns(widthDivider);

        GridItemAction<?> action = mReviewView.getActions().getGridItemAction();
        mView.setOnItemClickListener(newGridItemClickListener(action));
        mView.setOnItemLongClickListener(newGridItemLongClickListener(action));
    }

    @NonNull
    private AdapterView.OnItemLongClickListener newGridItemLongClickListener(final GridItemAction
                                                                                         action) {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                //TODO make type safe
                action.onGridItemLongClick(item, position, v);
                return true;
            }
        };
    }

    @NonNull
    private AdapterView.OnItemClickListener newGridItemClickListener(final GridItemAction action) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                //TODO make type safe
                action.onGridItemClick(item, position, v);
            }
        };
    }
}
