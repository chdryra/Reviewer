/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncMethodTracker {
    private IdableList<ReviewNode> mNodes;
    private int mNumNodes = 0;
    private AsyncMethod mMethod;
    private AsyncErrors mErrors;
    private boolean mAbort = false;
    private boolean mIsExecuting = false;

    interface AsyncMethod {
        CallbackMessage execute(ReviewNode node);

        void onNodesCompleted(AsyncErrors errors);
    }

    public AsyncMethodTracker(IdableList<ReviewNode> nodes, AsyncMethod method) {
        mNodes = nodes;
        mMethod = method;
        mErrors = new AsyncErrors();
    }

    public void collect() {
        if (!mIsExecuting) {

            mIsExecuting = true;
            mNumNodes = mNodes.size();

            for (ReviewNode node : mNodes) {
                CallbackMessage message = mMethod.execute(node);

                if(message.isError()) {
                    addError(node.getReviewId(), message);
                    mAbort = true;
                }

                if(mAbort) {
                    onCollectingFinished();
                    break;
                }
            }
        }
    }

    protected void addError(ReviewId reviewId, CallbackMessage message) {
        mErrors.addError(reviewId, message);
    }

    protected void onNodeReturned(ReviewId id, CallbackMessage message) {
        if(mIsExecuting) {
            mNumNodes--;
            if (message.isError()) addError(id, message);
            if (mNumNodes == 0 || message.isError()) onCollectingFinished();
        }
    }

    protected void onCollectingFinished() {
        if(mIsExecuting) {
            mIsExecuting = false;
            mMethod.onNodesCompleted(mErrors);
        }
    }

    public class AsyncErrors {
        private Map<ReviewId, CallbackMessage> mErrors;

        public AsyncErrors() {
            mErrors = new HashMap<>();
        }

        private void addError(ReviewId id, CallbackMessage message) {
            if(message.isError()) mErrors.put(id, message);
        }

        public boolean hasErrors() {
            return mErrors.size() > 0;
        }

        public CallbackMessage getMessage() {
            if(hasErrors()) {
                String message = "AsyncMethodTracking error:\n";
                for (Map.Entry<ReviewId, CallbackMessage> errors : mErrors.entrySet()) {
                    message += errors.getKey().toString() + ": " + errors.getValue().getMessage() + ".\n";

                }

                return CallbackMessage.error(message);
            } else {
                return CallbackMessage.ok();
            }
        }
    }
}
