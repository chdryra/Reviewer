/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * For building reviews. Collects appropriate data and builds a {@link com.chdryra.android
 * .reviewer.Model.Review} object
 */
public interface ReviewBuilderAdapter<GC extends GvDataList<?>> extends ReviewViewAdapter<GC> {
    ReviewBuilder getBuilder();

    void setSubject(String subject);

    void setRating(float rating);

    void setRatingIsAverage(boolean ratingIsAverage);

    ImageChooser getImageChooser();

    <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    boolean hasTags();

    Review buildReview();

    @Override
    GvDataType<? extends GvData> getGvDataType();

    @Override
    void attachReviewView(ReviewView<GC> view);

    @Override
    ReviewView<GC> getReviewView();

    @Override
    String getSubject();

    @Override
    float getRating();

    @Override
    GvImageList getCovers();

    @Override
    GvDataList<GC> getGridData();

    @Override
    boolean isExpandable(GC datum);

    @Override
    ReviewViewAdapter<?> expandGridCell(GC datum);

    @Override
    ReviewViewAdapter<?> expandGridData();

    @Override
    void registerDataObserver(DataObserver observer);

    @Override
    void unregisterDataObserver(DataObserver observer);

    @Override
    void notifyDataObservers();
}
