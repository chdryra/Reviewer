/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataBuilderAdapter<T extends GvDataParcelable> extends ReviewViewAdapter<T> {
    float getCriteriaAverage();

    boolean add(T datum);

    void delete(T datum);

    void deleteAll();

    void replace(T oldDatum, T newDatum);

    void commitData();

    void resetData();

    boolean isRatingAverage();

    void setRatingIsAverage(boolean ratingIsAverage);

    void setSubject(String subject, boolean adjustTags);

    String getSubject();

    float getRating();

    void setRating(float rating);

    GvImage getCover();

    ReviewNode buildPreview();

    @Override
    GvDataType<T> getGvDataType();
}

