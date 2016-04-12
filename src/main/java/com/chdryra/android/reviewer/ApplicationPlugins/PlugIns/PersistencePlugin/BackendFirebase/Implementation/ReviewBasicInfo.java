/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBasicInfo implements ReviewNode {
    private ReviewId mId;
    private DataRating mRating;

    public ReviewBasicInfo(ReviewId id, DataRating rating) {
        mId = id;
        mRating = rating;
    }

    @Override
    public DataSubject getSubject() {
        return new DatumSubject(mId, mId.toString());
    }

    @Override
    public DataRating getRating() {
        return mRating;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return new DatumAuthorReview(mId, mId.toString(), new DatumUserId(mId.toString()));
    }

    @Override
    public DataDateReview getPublishDate() {
        return new DatumDateReview(mId, 0);
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return false;
    }

    @Override
    public IdableList<? extends DataCriterionReview> getCriteria() {
        return new IdableDataList<>(mId);
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return new IdableDataList<>(mId);
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return new IdableDataList<>(mId);
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return new IdableDataList<>(mId);
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return new IdableDataList<>(mId);
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return new IdableDataList<>(mId);

    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
    }

    @Override
    public Review getReview() {
        return this;
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public ReviewNode getRoot() {
        return this;
    }

    @Override
    public boolean isExpandable() {
        return false;
    }

    @Override
    public ReviewNode expand() {
        return this;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return new IdableDataList<>(mId);
    }

    @Nullable
    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return false;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {

    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return false;
    }
}
