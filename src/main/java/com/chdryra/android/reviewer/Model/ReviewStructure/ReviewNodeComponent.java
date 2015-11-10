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
