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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewBuilder extends DataBuilder.DataBuilderObserver{
    String getSubject();

    void setSubject(String subject);

    boolean isRatingAverage();

    float getRating();

    void setRating(float rating);

    float getAverageRating();

    void setRatingIsAverage(boolean ratingIsAverage);

    <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType);

    GvImageList getCovers();

    boolean hasTags();

    Review buildReview();

    @Override
    <T extends GvData> void onDataPublished(DataBuilder<T> dataBuilder);
}
