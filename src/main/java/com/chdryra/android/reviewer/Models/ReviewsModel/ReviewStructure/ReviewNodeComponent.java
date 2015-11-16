package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataRating;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeComponent extends ReviewNode {
    boolean addChild(ReviewNodeComponent childNode);

    void removeChild(String reviewId);

    void setParent(ReviewNodeComponent parent);

    ReviewNode makeTree();

    @Override
    Review getReview();

    @Override
    ReviewNode getParent();

    @Override
    ReviewNode getRoot();

    @Override
    ReviewNode expand();

    @Override
    IdableList<ReviewNode> getChildren();

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
    IdableList<? extends DataCriterion> getCriteria();

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
    String getReviewId();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
