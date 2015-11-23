package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

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
