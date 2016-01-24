/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewView<T extends GvData> extends GridDataObservable.GridDataObserver, LaunchableUi {
    String getSubject();

    float getRating();

    GvDataList<T> getGridData();

    GvDataList<T> getGridViewData();

    void setGridViewData(GvDataList<T> dataToShow);

    ReviewViewAdapter<T> getAdapter();

    ReviewViewParams getParams();

    ReviewViewActions<T> getActions();

    Activity getActivity();

    boolean isEditable();

    String getFragmentSubject();

    float getFragmentRating();

    void attachFragment(FragmentReviewView parent);

    void resetGridViewData();

    void updateCover();

    void registerGridDataObserver(GridDataObservable.GridDataObserver observer);

    void unregisterGridDataObserver(GridDataObservable.GridDataObserver observer);

    void notifyObservers();

    View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState);

    @Override
    void onGridDataChanged();

    @Override
    void launch(LauncherUi launcher);
}