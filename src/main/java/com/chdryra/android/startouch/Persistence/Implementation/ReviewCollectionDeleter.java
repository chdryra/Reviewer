/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;


import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewCollectionDeleter implements ReviewCollection.Callback {
    private final ReviewId mReviewId;
    private final ReviewCollection mReviewCollection;
    private final DeleteCallback mCallback;

    public interface DeleteCallback {
        void onDeletedFromCollection(String name, ReviewId reviewId, CallbackMessage message);
    }

    public ReviewCollectionDeleter(ReviewId reviewId, ReviewCollection reviewCollection, DeleteCallback callback) {
        mReviewId = reviewId;
        mReviewCollection = reviewCollection;
        mCallback = callback;
    }

    public void delete() {
        mReviewCollection.hasEntry(mReviewId, this);
    }

    @Override
    public void onAddedToCollection(String name, CallbackMessage message) {

    }

    @Override
    public void onRemovedFromCollection(String name, CallbackMessage message) {
        mCallback.onDeletedFromCollection(mReviewCollection.getName(), mReviewId, message);
    }

    @Override
    public void onCollectionHasEntry(String name, boolean hasEntry, CallbackMessage message) {
        if (!hasEntry && message.isOk()) {
            mCallback.onDeletedFromCollection(name, mReviewId, CallbackMessage.ok(Strings.REVIEW + " not in " + name));
        } else {
            mReviewCollection.removeEntry(mReviewId, this);
        }
    }
}
