/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeLeaf;

/**
 * The fundamental interface for all review classes.
 * <p/>
 * All reviews are primarily data holders that are expected to have 3 items of core data:
 * <ul>
 * <li>A unique identifier</li>
 * <li>A subject </li>
 * <li>A rating</li>
 * </ul>
 * <p/>
 * In addition, reviews may have some optional data:
 * <ul>
 * <li>Comments</li>
 * <li>Images</li>
 * <li>Locations</li>
 * <li>Facts</li>
 * <li>URLs</li>
 * </ul>
 * <p/>
 * Reviews can be nodes in a review tree with children sub-reviews and/or be a sub-review of a
 * parent review. They can be represented as a ReviewNode with zero or more children and/or a
 * parent if necessary.
 * <p/>
 * Reviews may or may not be published (have non-null Author and Publish Date). Published reviews
 * should not be editable reviews or expandable nodes themselves. They may, however, be passed to a
 * {@link NodeLeaf} as the root review for a different
 * review structure that may be expanded.
 *
 * @see ReviewNode
 */

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
    DataAuthorReview getAuthor();

    @Override
    DataDateReview getPublishDate();

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
