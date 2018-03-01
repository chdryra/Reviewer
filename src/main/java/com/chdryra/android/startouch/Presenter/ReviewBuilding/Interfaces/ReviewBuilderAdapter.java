/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * For building reviews. Collects appropriate data and builds a {@link com.chdryra.android
 * .reviewer.Model.Review} object
 */
public interface ReviewBuilderAdapter<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewViewAdapter<GC>, DataObservable.DataObserver{
    ReviewBuilder getBuilder();

    String getSubject();

    float getRating();

    void setSubject(String subject, boolean adjustTags);

    void setRating(float rating);

    void setCover(GvImage cover);

    void setRatingIsAverage(boolean ratingIsAverage);

    GvImage getCover();

    void setView(ReviewEditor.EditMode uiType);

    <T extends GvDataParcelable> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    Review buildReview();

    ReviewNode buildPreview();

    ReviewNode buildPreview(String subject, float rating);

    @Override
    GvDataType<GC> getGvDataType();
}
