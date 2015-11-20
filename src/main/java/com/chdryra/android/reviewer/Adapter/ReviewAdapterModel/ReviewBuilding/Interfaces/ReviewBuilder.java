package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewBuilder {
    String getSubject();

    void setSubject(String subject);

    boolean isRatingAverage();

    float getRating();

    void setRating(float rating);

    float getAverageRating();

    void setRatingIsAverage(boolean ratingIsAverage);

    <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType);

    <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType);

    <T extends GvData> void setData(DataBuilder<T> dataBuilder);

    Review buildReview();
}
