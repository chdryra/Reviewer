/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataHolder {
    ReviewId getReviewId();

    AuthorId getAuthorId();

    DateTime getPublishDate();

    String getSubject();

    float getRating();

    int getRatingWeight();

    Iterable<? extends DataTag> getTags();

    Iterable<? extends DataComment> getComments();

    Iterable<? extends DataImage> getImages();

    Iterable<? extends DataFact> getFacts();

    Iterable<? extends DataLocation> getLocations();

    Iterable<? extends DataCriterion> getCriteria();

    boolean isValid(DataValidator validator);
}
