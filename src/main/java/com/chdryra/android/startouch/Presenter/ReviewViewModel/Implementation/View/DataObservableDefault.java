/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;

import java.util.ArrayList;


/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class DataObservableDefault implements DataObservable {
    private final ArrayList<DataObserver> mObservers = new ArrayList<>();

    @Override
    public void registerObserver(DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DataObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    protected void notifyDataObservers() {
        for (DataObserver observer : mObservers) {
            observer.onDataChanged();
        }
    }
}
