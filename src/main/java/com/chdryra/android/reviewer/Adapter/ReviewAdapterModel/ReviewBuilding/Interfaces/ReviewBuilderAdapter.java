/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataListImpl;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * For building reviews. Collects appropriate data and builds a {@link com.chdryra.android
 * .reviewer.Model.Review} object
 */
public interface ReviewBuilderAdapter<GC extends GvDataListImpl<?>> extends ReviewViewAdapter<GC> {
    ReviewBuilder getBuilder();

    void setSubject(String subject);

    void setRating(float rating);

    void setRatingIsAverage(boolean ratingIsAverage);

    ImageChooser getImageChooser();

    <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    boolean hasTags();

    Review publishReview();

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
    GvDataListImpl<GC> getGridData();

    @Override
    boolean isExpandable(GC datum);

    @Override
    ReviewViewAdapter<?> expandGridCell(GC datum);

    @Override
    ReviewViewAdapter<?> expandGridData();

    @Override
    void registerGridDataObserver(GridDataObserver observer);

    @Override
    void unregisterGridDataObserver(GridDataObserver observer);

    @Override
    void notifyGridDataObservers();
}
