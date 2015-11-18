/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 April, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataObservable {
    interface GridDataObserver {
        //abstract methods
        //abstract
        void onGridDataChanged();
    }

    //abstract methods
    //abstract
    void registerGridDataObserver(GridDataObserver observer);

    void unregisterGridDataObserver(GridDataObserver observer);

    void notifyGridDataObservers();
}
