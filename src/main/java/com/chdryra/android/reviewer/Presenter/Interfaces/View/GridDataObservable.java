/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.View;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataObservable {
    interface GridDataObserver {
        void onGridDataChanged();
    }

    void registerGridDataObserver(GridDataObserver observer);

    void unregisterGridDataObserver(GridDataObserver observer);

    void notifyGridDataObservers();
}
