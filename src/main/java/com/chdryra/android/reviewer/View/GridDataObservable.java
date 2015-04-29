/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 April, 2015
 */

package com.chdryra.android.reviewer.View;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataObservable {
    interface GridDataObserver {
        public void onGridDataChanged();
    }

    public void registerGridDataObserver(GridDataObserver observer);

    public void notifyGridDataObservers();
}
