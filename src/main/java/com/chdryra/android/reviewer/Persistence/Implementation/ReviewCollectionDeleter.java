/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;

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
        void onDeletedFromPlaylist(String playlistName, ReviewId reviewId, CallbackMessage message);
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
    public void onAddedToCollection(CallbackMessage message) {

    }

    @Override
    public void onRemovedFromCollection(CallbackMessage message) {
        mCallback.onDeletedFromPlaylist(mReviewCollection.getName(), mReviewId, message);
    }

    @Override
    public void onCollectionHasReview(boolean hasReview, CallbackMessage message) {
        if (!hasReview && message.isOk()) {
            String name = mReviewCollection.getName();
            mCallback.onDeletedFromPlaylist(name, mReviewId, CallbackMessage.ok(Strings.REVIEW + " not in " + name));
        } else {
            mReviewCollection.removeEntry(mReviewId, this);
        }
    }
}
