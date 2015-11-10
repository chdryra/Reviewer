/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataRating;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;

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
 * {@link ReviewTreeNode} as the root review for a different
 * review structure that may be expanded.
 *
 * @see ReviewNode
 */

public interface Review extends MdReviewId.IdAble, DataReview {
    //abstract
    DataSubject getSubject();

    DataRating getRating();

    DataAuthorReview getAuthor();

    DataDateReview getPublishDate();

    ReviewNode getTreeRepresentation();

    boolean isRatingAverageOfCriteria();

    //Optional data
    IdableList<? extends DataCriterion> getCriteria();

    IdableList<? extends DataComment> getComments();

    IdableList<? extends DataFact> getFacts();

    IdableList<? extends DataImage> getImages();

    IdableList<? extends DataLocation> getLocations();

    //Overridden
    //Core data
    @Override
    String getReviewId();

    @Override
    MdReviewId getMdReviewId();

    //For speed and comparison
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
