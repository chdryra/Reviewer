/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 February, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.View.GridDataObservable;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewViewAdapterBasic implements ReviewViewAdapter {
    final ArrayList<GridDataObservable.GridDataObserver> mObservers = new ArrayList<>();
    private GridDataWrapper  mWrapper;
    private GridDataExpander mExpander;

    public void registerGridDataObserver(GridDataObservable.GridDataObserver observer) {
        mObservers.add(observer);
    }

    public void notifyGridDataObservers() {
        for (GridDataObservable.GridDataObserver observer : mObservers) {
            observer.onGridDataChanged();
        }
    }

    @Override
    public GvDataList getGridData() {
        return mWrapper != null ? mWrapper.getGridData() : null;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mExpander != null && mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return isExpandable(datum) ? mExpander.expandItem(datum) : null;
    }

    public void setWrapper(GridDataWrapper wrapper) {
        mWrapper = wrapper;
        notifyGridDataObservers();
    }

    public void setExpander(GridDataExpander expander) {
        mExpander = expander;
    }
}
