/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewEditor<T extends GvData> extends ReviewView<T> {
    void setSubject();

    void setRatingIsAverage(boolean isAverage);

    void setRating(float rating, boolean fromUser);

    GvImage getCover();

    void setCover(GvImage image);

    void notifyBuilder();

    ImageChooser getImageChooser();

    @Override
    ReviewViewAdapter<T> getAdapter();

    @Override
    ReviewViewParams getParams();

    @Override
    Activity getActivity();

    @Override
    String getSubject();

    @Override
    float getRating();

    @Override
    GvDataList<T> getGridData();

    @Override
    GvDataList<T> getGridViewData();

    @Override
    void setGridViewData(GvDataList<T> dataToShow);

    @Override
    ReviewViewActions<T> getActions();

    @Override
    boolean isEditable();

    @Override
    String getFragmentSubject();

    @Override
    float getFragmentRating();

    @Override
    void attachFragment(FragmentReviewView parent);

    @Override
    void updateCover();

    @Override
    void registerDataObserver(DataObservable.DataObserver observer);

    @Override
    void unregisterDataObserver(DataObservable.DataObserver observer);

    @Override
    void notifyDataObservers();

    @Override
    View modifyIfNeccessary(View v, LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState);

    @Override
    void onDataChanged();

    @Override
    String getLaunchTag();

    @Override
    void launch(LauncherUi launcher);
}
