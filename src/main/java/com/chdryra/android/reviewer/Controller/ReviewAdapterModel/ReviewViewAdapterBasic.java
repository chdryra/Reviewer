/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 February, 2015
 */

package com.chdryra.android.reviewer.Controller.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Primary implementation of {@link ReviewViewAdapter}.
 */
public abstract class ReviewViewAdapterBasic implements ReviewViewAdapter {
    final ArrayList<GridDataObservable.GridDataObserver> mObservers = new ArrayList<>();
    private GridDataViewer mWrapper;
    private GridDataExpander mExpander;

    @Override
    public GvDataList getGridData() {
        return mWrapper != null ? mWrapper.getGridData() : null;
    }

    public void registerGridDataObserver(GridDataObservable.GridDataObserver observer) {
        mObservers.add(observer);
    }

    public void notifyGridDataObservers() {
        for (GridDataObservable.GridDataObserver observer : mObservers) {
            observer.onGridDataChanged();
        }
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mExpander != null && mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return isExpandable(datum) ? mExpander.expandItem(datum) : null;
    }

    public void setWrapper(GridDataViewer wrapper) {
        mWrapper = wrapper;
        notifyGridDataObservers();
    }

    public void setExpander(GridDataExpander expander) {
        mExpander = expander;
    }
}
