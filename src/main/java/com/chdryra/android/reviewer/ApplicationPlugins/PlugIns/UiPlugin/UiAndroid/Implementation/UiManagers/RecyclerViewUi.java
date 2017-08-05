/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.mygenerallibrary.Ui.GridItemDecoration;
import com.chdryra.android.mygenerallibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RecyclerViewUi<T extends GvData> extends DataViewUi<RecyclerView, T> implements RecyclerAdapterBasic.OnItemClickListener<T>{
    private final GridItemAction<T> mClickAction;
    private final CellDimensionsCalculator.Dimensions mDims;

    public RecyclerViewUi(final ReviewView<T> reviewView, RecyclerView view, CellDimensionsCalculator calculator) {
        super(view, new ReferenceValueGetter<GvDataList<T>>() {
            @Override
            public GvDataList<T> getValue() {
                return reviewView.getGridData();
            }
        }, reviewView);

        ReviewViewParams.GridViewParams params = reviewView.getParams().getGridViewParams();

        int span = params.getCellWidth().getDivider();
        getView().setLayoutManager(new GridLayoutManager(getView().getContext(), span));
        getView().addItemDecoration(new GridItemDecoration(span, 10, false));
        mDims = calculator.calcDimensions(params.getCellWidth(), params.getCellHeight(), 10);

        mClickAction = reviewView.getActions().getGridItemAction();

        update();
    }

    @Override
    public void update() {
        getView().setAdapter(new GvDataAdapter<>(getReferenceValue(), mDims.getCellWidth(), mDims.getCellHeight(), this));
    }

    @Override
    public void onItemClick(T datum, int position, View v) {
        mClickAction.onGridItemClick(datum, position, v);
    }

    @Override
    public void onItemLongClick(T datum, int position, View v) {
        mClickAction.onGridItemLongClick(datum, position, v);
    }

}
