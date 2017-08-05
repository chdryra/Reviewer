/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.annotation.NonNull;
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
public class GridViewUi<T extends GvData> extends DataViewUi<GridView, T>{
    private final FactoryGridCellAdapter mFactory;
    private final CellDimensionsCalculator mCalculator;

    public GridViewUi(final ReviewView<T> reviewView,
                      GridView view,
                      FactoryGridCellAdapter factory,
                      CellDimensionsCalculator calculator) {
        super(view, new ReferenceValueGetter<GvDataList<T>>() {
            @Override
            public GvDataList<T> getValue() {
                return reviewView.getGridData();
            }
        }, reviewView);
        mFactory = factory;
        mCalculator = calculator;
        inititialise(reviewView);
    }

    @Override
    public void update() {
        getAdapter().setData(getReferenceValue());
    }

    private ViewHolderAdapter getAdapter() {
        return (ViewHolderAdapter) getView().getAdapter();
    }

    private void inititialise(ReviewView<T> reviewView) {
        ReviewViewParams.GridViewParams params = reviewView.getParams().getGridViewParams();
        CellDimensionsCalculator.Dimensions dims
                = mCalculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 0);
        int cell_width = dims.getCellWidth();
        int cell_height = dims.getCellHeight();

        GridView view = getView();
        view.setDrawSelectorOnTop(true);
        view.setAdapter(mFactory.newAdapter(getReferenceValue(), cell_width, cell_height));
        view.setColumnWidth(cell_width);
        view.setNumColumns(params.getCellWidth().getDivider());

        GridItemAction<T> action = reviewView.getActions().getGridItemAction();
        view.setOnItemClickListener(newGridItemClickListener(action));
        view.setOnItemLongClickListener(newGridItemLongClickListener(action));
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
