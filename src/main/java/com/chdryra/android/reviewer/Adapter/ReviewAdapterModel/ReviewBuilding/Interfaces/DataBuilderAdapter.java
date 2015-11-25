package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataBuilderAdapter<T extends GvData> extends ReviewViewAdapter<T>{
    ReviewBuilderAdapter<?> getParentBuilder();

    boolean isRatingAverage();

    float getAverageRating();

    boolean add(T datum);

    void delete(T datum);

    void deleteAll();

    void replace(T oldDatum, T newDatum);

    void publishData();

    void resetData();

    void setRatingIsAverage(boolean ratingIsAverage);

    void setSubject(String subject);

    void setRating(float rating);

    @Override
    GvDataType<T> getGvDataType();

    @Override
    void attachReviewView(ReviewView<T> view);

    @Override
    ReviewView<T> getReviewView();

    @Override
    String getSubject();

    @Override
    float getRating();

    @Override
    GvImageList getCovers();

    @Override
    GvDataList<T> getGridData();

    @Override
    boolean isExpandable(T datum);

    @Override
    ReviewViewAdapter<?> expandGridCell(T datum);

    @Override
    ReviewViewAdapter<?> expandGridData();

    @Override
    void registerGridDataObserver(GridDataObserver observer);

    @Override
    void unregisterGridDataObserver(GridDataObserver observer);

    @Override
    void notifyGridDataObservers();
}
