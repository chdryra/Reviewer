/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

public interface Review extends DataReview {
    DataImage getCover();

    IdableList<? extends DataTag> getTags();

    IdableList<? extends DataCriterion> getCriteria();

    IdableList<? extends DataComment> getComments();

    IdableList<? extends DataFact> getFacts();

    IdableList<? extends DataImage> getImages();

    IdableList<? extends DataLocation> getLocations();

    boolean isCacheable();

    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataAuthorId getAuthorId();

    @Override
    DataDate getPublishDate();

    @Override
    ReviewId getReviewId();

    //For speed and comparison
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
