/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

/**
 * Tree representation of a review.
 * <p/>
 * <p>
 * Can have other reviews as children or as a parent allowing a structured representation of how
 * reviews may relate to each other, for example a user review with sub-criteria as children
 * or a meta review with other reviews as children.
 * </p>
 */
public interface ReviewNode extends Review {
    Review getReview();

    @Nullable
    ReviewNode getParent();

    ReviewNode getRoot();

    boolean isExpandable();

    ReviewNode expand();

    IdableList<ReviewNode> getChildren();

    @Nullable
    ReviewNode getChild(ReviewId reviewId);

    boolean hasChild(ReviewId reviewId);

    void acceptVisitor(VisitorReviewNode visitor);

    boolean isRatingAverageOfChildren();

    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataAuthorReview getAuthor();

    @Override
    DataDateReview getPublishDate();

    @Override
    ReviewNode getTreeRepresentation();

    @Override
    boolean isRatingAverageOfCriteria();

    @Override
    IdableList<? extends DataCriterionReview> getCriteria();

    @Override
    IdableList<? extends DataComment> getComments();

    @Override
    IdableList<? extends DataFact> getFacts();

    @Override
    IdableList<? extends DataImage> getImages();

    @Override
    IdableList<? extends DataImage> getCovers();

    @Override
    IdableList<? extends DataLocation> getLocations();

    @Override
    ReviewId getReviewId();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
