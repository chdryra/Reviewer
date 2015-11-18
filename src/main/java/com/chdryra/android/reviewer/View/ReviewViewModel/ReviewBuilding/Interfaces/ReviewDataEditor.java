package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridDataObservable;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataEditor<T extends GvData> extends ReviewView {
    //public methods
    boolean isRatingAverage();

    boolean add(T datum);

    void replace(T oldDatum, T newDatum);

    void delete(T datum);

    void commitEdits();

    void discardEdits();

    void setSubject();

    void setRatingIsAverage(boolean isAverage);

    void setRating(float rating, boolean fromUser);

    GvImageList.GvImage getCover();

    @Override
    ReviewViewAdapter getAdapter();

    @Override
    ReviewViewParams getParams();

    @Override
    FragmentReviewView getParentFragment();

    @Override
    Activity getActivity();

    @Override
    String getSubject();

    @Override
    float getRating();

    @Override
    GvDataList getGridData();

    @Override
    GvDataList getGridViewData();

    @Override
    void setGridViewData(GvDataList dataToShow);

    @Override
    ReviewViewActions getActions();

    @Override
    boolean isEditable();

    @Override
    String getFragmentSubject();

    @Override
    float getFragmentRating();

    @Override
    void attachFragment(FragmentReviewView parent);

    @Override
    void resetGridViewData();

    @Override
    void updateCover();

    @Override
    void registerGridDataObserver(GridDataObservable.GridDataObserver observer);

    @Override
    void unregisterGridDataObserver(GridDataObservable.GridDataObserver observer);

    @Override
    void notifyObservers();

    @Override
    View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    void onGridDataChanged();

    @Override
    String getLaunchTag();

    @Override
    void launch(LauncherUi launcher);
}
