/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeDataCollector<T extends HasReviewId>  {
    private IdableList<ReviewNode> mNodes;
    private IdableList<T> mData;
    private AsyncMethodTracker mTracker;

    public NodeDataCollector(IdableList<ReviewNode> nodes) {
        mNodes = nodes;
    }

    public abstract void doAsyncMethod(ReviewNode node);
    public abstract void onCompleted(IdableList<T> data);

    public void collect() {
        mData = new IdableDataList<>(mNodes.getReviewId());
        mTracker = new AsyncMethodTracker(mNodes);

        mTracker.collect();
    }

    protected void onNodeReturned(IdableList<T> childData, CallbackMessage message) {
        if(!message.isError()) mData.addAll(childData);
        mTracker.onNodeReturned();
    }

    public void onCompleted() {
        onCompleted(mData);
    }

    private class AsyncMethodTracker {
        private IdableList<ReviewNode> mNodes;
        private int mNumNodes = 0;
        private boolean mIsExecuting = false;

        public AsyncMethodTracker(IdableList<ReviewNode> nodes) {
            mNodes = nodes;
        }

        public void collect() {
            if(!mIsExecuting) {
                mIsExecuting = true;
                mNumNodes = mNodes.size();
                for (ReviewNode node : mNodes) {
                    doAsyncMethod(node);
                }
            }
        }

        protected void onNodeReturned() {
            mNumNodes--;
            if(mNumNodes == 0) {
                onCompleted();
                mIsExecuting = false;
            }
        }
    }
}
