/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeDataCollector<T extends HasReviewId>  {
    private IdableList<ReviewNode> mNodes;
    private ReviewListReference<T> mData;
    private AsyncMethodTracker mTracker;
    private boolean mCollecting = false;

    public NodeDataCollector(IdableList<ReviewNode> nodes) {
        mNodes = nodes;
    }

    public abstract CallbackMessage doAsyncMethod(ReviewNode node);
    public abstract void onCompleted(ReviewListReference<T> data, AsyncMethodTracker.AsyncErrors errors);

    public void collect() {
        if(!mCollecting) {
            mCollecting = true;

            mData = new IdableDataList<>(mNodes.getReviewId());

            mTracker = new AsyncMethodTracker(mNodes, new AsyncMethodTracker.AsyncMethod() {
                @Override
                public CallbackMessage execute(ReviewNode node) {
                    return doAsyncMethod(node);
                }

                @Override
                public void onNodesCompleted(AsyncMethodTracker.AsyncErrors errors) {
                    mCollecting = false;
                    onCompleted(errors);
                }
            });

            mTracker.collect();
        }
    }

    private void onCompleted(AsyncMethodTracker.AsyncErrors errors) {
        onCompleted(mData, errors);
    }

    protected void onNodeReturned(ReviewId nodeId, T childData, CallbackMessage message) {
        if(!message.isError()) mData.add(childData);
        mTracker.onNodeReturned(nodeId, message);
    }

    protected void onNodeReturned(ReviewId nodeId, IdableList<? extends T> childData, CallbackMessage message) {
        if(!message.isError()) mData.addAll(childData);
        mTracker.onNodeReturned(nodeId, message);
    }

    protected ReviewId getReviewId() {
        return mNodes.getReviewId();
    }

    public abstract static class Size extends NodeDataCollector<DataSize>  {
        protected abstract void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors);

        public Size(IdableList<ReviewNode> nodes) {
            super(nodes);
        }


        @Override
        public void onCompleted(IdableList<DataSize> data, AsyncMethodTracker.AsyncErrors errors) {
            int size = 0;
            if(!errors.hasErrors()) {
                for (DataSize datum : data) {
                    size += datum.getSize();
                }
            }

            onCompleted(new DatumSize(getReviewId(), size), errors);
        }
    }
}
