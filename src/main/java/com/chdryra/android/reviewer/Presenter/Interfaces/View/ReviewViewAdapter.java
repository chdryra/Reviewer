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
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;

/**
 * Adapter for {@link Review} model data to be presented in a {@link ReviewView} View layer using
 * {@link GvData}
 */
public interface ReviewViewAdapter<T extends GvData> extends GridDataViewer<T>, GridDataObservable {
    void attachReviewView(ReviewView<T> view);

    ReviewView<T> getReviewView();

    String getSubject();

    float getRating();

    GvImageList getCovers();

    @Override
    GvDataType<? extends GvData> getGvDataType();

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
