package com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel;

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
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeComponent extends ReviewNode {
    boolean addChild(ReviewNodeComponent childNode);

    void removeChild(String reviewId);

    void setParent(ReviewNodeComponent parent);

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
    ReviewNode getChild(String reviewId);

    @Override
    boolean hasChild(String reviewId);

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
    String getReviewId();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
