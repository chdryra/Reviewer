/*
* Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Author: Rizwan Choudrey
* Date: 24 January, 2015
*/

package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;

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

    ReviewViewActions getActions();

    FragmentReviewView getParentFragment();

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
    String getLaunchTag();

    @Override
    void launch(LauncherUi launcher);
}