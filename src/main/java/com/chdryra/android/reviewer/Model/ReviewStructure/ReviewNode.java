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
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewNode;

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
    //abstract
    Review getReview();

    ReviewNode getParent();

    ReviewNode getRoot();

    ReviewNode expand();

    IdableList<ReviewNode> getChildren();

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
    IdableList<? extends DataCriterion> getCriteria();

    @Override
    IdableList<? extends DataComment> getComments();

    @Override
    IdableList<? extends DataFact> getFacts();

    @Override
    IdableList<? extends DataImage> getImages();

    @Override
    IdableList<? extends DataLocation> getLocations();

    @Override
    String getReviewId();

    @Override
    MdReviewId getMdReviewId();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
