/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.AsyncUtils.DelayTask;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeDefault;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeRepo extends NodeDefault implements ReviewsSubscriber, ReviewNode {
    private static final long WAIT_TIME = 200L;
    private final FactoryReviewNode mNodeFactory;
    private ReviewsRepoReadable mRepo;
    private List<ReviewReference> mBatchPending;
    private DelayAddChildTask mTask;

    ReviewNodeRepo(DataReviewInfo meta,
                   ReviewsRepoReadable repo,
                   FactoryMdReference referenceFactory,
                   FactoryReviewNode nodeFactory) {
        super(meta, referenceFactory);
        mRepo = repo;
        mNodeFactory = nodeFactory;
        mBatchPending = new ArrayList<>();
        mRepo.bindToItems(this);
    }

    @Override
    public String getSubscriberId() {
        return getReviewId().toString();
    }

    @Override
    public void onItemAdded(ReviewReference item) {
        if (mTask != null) mTask.cancel(true);
        mTask = new DelayAddChildTask(item);
        mTask.execute(WAIT_TIME);
    }

    @Override
    public void onItemRemoved(ReviewReference item) {
        removeChild(item.getReviewId());
    }

    @Override
    public void onCollectionChanged(Collection<ReviewReference> newItems) {
        IdableList<ReviewNode> children = getChildren();
        for(ReviewNode child : children) {
            removeChild(child.getReviewId());
        }

        for(ReviewReference reference : newItems) {
            addChild(reference);
        }
    }

    @Override
    public void onInvalidated(CollectionReference<ReviewReference, ?, ?> reference) {
        IdableList<ReviewNode> children = getChildren();
        for(ReviewNode child : children) removeChild(child.getReviewId());
        mRepo.unbindFromItems(this);
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
        mRepo.unbindFromItems(this);
        for (ReviewNode child : getChildren()) {
            removeChild(child.getReviewId());
        }
        mRepo = null;
    }

    private void addPendingChildren() {
        for (ReviewReference child : mBatchPending) {
            addChild(child);
        }

        mTask = null;
        mBatchPending.clear();
    }

    private void addChild(ReviewReference review) {
        addChild(mNodeFactory.createLeafNode(review));
    }

    //Hacky way of collecting a bunch of reviews first before adding when subscribing to repo,
    //rather than continuous notifying of listeners on first download.
    private class DelayAddChildTask extends DelayTask {
        private final ReviewReference mReference;

        private DelayAddChildTask(ReviewReference reference) {
            mReference = reference;
        }

        @Override
        protected void onCancelled() {
            mBatchPending.add(mReference);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            onCancelled();
            addPendingChildren();
        }
    }
}
