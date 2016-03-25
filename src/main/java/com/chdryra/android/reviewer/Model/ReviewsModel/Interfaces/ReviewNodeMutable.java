/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

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
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeMutable extends ReviewNode {

    boolean addChild(ReviewNodeMutable childNode);

    void removeChild(ReviewId reviewId);

    void setParent(@Nullable ReviewNodeMutable parent);

    @Override
    Review getReview();

    @Override
    ReviewNode getParent();

    @Override
    ReviewNode getRoot();

    @Override
    boolean isExpandable();

    @Override
    ReviewNode expand();

    @Override
    IdableList<ReviewNode> getChildren();

    @Override
    ReviewNode getChild(ReviewId reviewId);

    @Override
    boolean hasChild(ReviewId reviewId);

    @Override
    void acceptVisitor(VisitorReviewNode visitor);

    @Override
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
