package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ImageChooser;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridDataObservable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewEditor<T extends GvData> extends ReviewView<T> {
    //public methods
    void setSubject();

    void setRatingIsAverage(boolean isAverage);

    void setRating(float rating, boolean fromUser);

    GvImage getCover();

    void setCover(GvImage image);

    void notifyBuilder();

    boolean hasTags();

    ImageChooser getImageChooser();

    @Override
    ReviewViewAdapter<T> getAdapter();

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
