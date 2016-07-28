/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

public interface Review extends DataReviewInfo {
    interface ReviewObserver {
        void onReviewChanged();
    }

    void registerObserver(ReviewObserver observer);

    void unregisterObserver(ReviewObserver observer);

    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataAuthorId getAuthorId();

    @Override
    DataDate getPublishDate();

    DataImage getCover();

    IdableList<? extends DataCriterion> getCriteria();

    IdableList<? extends DataComment> getComments();

    IdableList<? extends DataFact> getFacts();

    IdableList<? extends DataImage> getImages();

    IdableList<? extends DataLocation> getLocations();

    boolean isCacheable();

    @Override
    ReviewId getReviewId();

    //For speed and comparison
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
