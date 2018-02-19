/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryDataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeDefault;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsSubscriber;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeRepo extends NodeDefault implements ReviewsSubscriber, ReviewNode {
    private final FactoryReviewNode mNodeFactory;
    private ReviewsRepoReadable mRepo;

    ReviewNodeRepo(DataReview meta,
                   ReviewsRepoReadable repo,
                   FactoryDataReference referenceFactory,
                   FactoryReviewNode nodeFactory) {
        super(meta, referenceFactory);
        mRepo = repo;
        mNodeFactory = nodeFactory;
        mRepo.subscribe(this);
    }

    @Override
    public void onItemAdded(ReviewReference item) {
        addChild(item);
    }

    @Override
    public void onItemRemoved(ReviewReference item) {
        removeChild(item.getReviewId());
    }

    @Override
    public void onCollectionChanged(Collection<ReviewReference> newItems) {
        IdableList<ReviewNode> children = getChildren();

        for (ReviewNode child : children) {
            removeChild(child.getReviewId());
        }

        for (ReviewReference reference : newItems) {
            addChild(reference);
        }
    }

    @Override
    public void onInvalidated(CollectionReference<ReviewReference, ?, ?> reference) {
        IdableList<ReviewNode> children = getChildren();
        for (ReviewNode child : children) removeChild(child.getReviewId());
        mRepo.unsubscribe(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeRepo)) return false;
        if (!super.equals(o)) return false;

        ReviewNodeRepo that = (ReviewNodeRepo) o;

        return mRepo.equals(that.mRepo);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mRepo.hashCode();
        return result;
    }

    void detachFromRepo() {
        mRepo.unsubscribe(this);
        for (ReviewNode child : getChildren()) {
            removeChild(child.getReviewId());
        }
        mRepo = null;
    }

    private void addChild(ReviewReference review) {
        addChild(mNodeFactory.createLeafNode(review));
    }
}
