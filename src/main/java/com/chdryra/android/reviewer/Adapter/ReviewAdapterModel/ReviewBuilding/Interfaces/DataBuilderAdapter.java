package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataBuilderAdapter<T extends GvData> extends ReviewViewAdapter<T>{
    GvDataType<T> getDataType();

    ReviewBuilderAdapter getParentBuilder();

    boolean isRatingAverage();

    float getAverageRating();

    boolean add(T datum);

    void delete(T datum);

    void deleteAll();

    void replace(T oldDatum, T newDatum);

    void setData();

    void reset();

    void setRatingIsAverage(boolean ratingIsAverage);

    void setSubject(String subject);

    void setRating(float rating);
}
