/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbAuthored implements ReviewsRepoReadable, ReviewsSubscriber {
    private final AuthorId mAuthorId;
    private final ReviewerDbRepo mRepo;
    private final ReviewDereferencer mDereferencer;
    private final List<ReviewsSubscriber> mSubscribers;

    public ReviewerDbAuthored(AuthorId authorId,
                              ReviewerDbRepo repo,
                              ReviewDereferencer dereferencer) {
        mAuthorId = authorId;
        mRepo = repo;
        mDereferencer = dereferencer;
        mSubscribers = new ArrayList<>();
    }

    ReviewerDbRepo getRepo() {
        return mRepo;
    }

    AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        if(!mSubscribers.contains(subscriber)) {
            mSubscribers.add(subscriber);
            if(mSubscribers.size() == 1) {
                mRepo.subscribe(this);
            } else {
                mRepo.getReferences(subscriber, mAuthorId);
            }
        }
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        if(mSubscribers.contains(subscriber)) mSubscribers.remove(subscriber);
        if(mSubscribers.size() == 0) mRepo.unsubscribe(this);
    }

    @Override
    public String getSubscriberId() {
        return toString();
    }

    @Override
    public void onReviewAdded(ReviewReference reference) {
        if (isCorrectAuthor(reference)) {
            for (ReviewsSubscriber subscriber : mSubscribers) {
                subscriber.onReviewAdded(reference);
            }
        }
    }

    private boolean isCorrectAuthor(ReviewReference reference) {
        return reference.getAuthorId().toString().equals(mAuthorId.toString());
    }

    @Override
    public void onReviewEdited(ReviewReference reference) {

    }

    @Override
    public void onReviewRemoved(ReviewReference reference) {
        if (isCorrectAuthor(reference)) {
            for (ReviewsSubscriber subscriber : mSubscribers) {
                subscriber.onReviewRemoved(reference);
            }
        }
    }

    @Override
    public void onReferenceInvalidated(ReviewId reviewId) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReferenceInvalidated(reviewId);
        }
    }

    @Override
    public void getReference(ReviewId reviewId, final RepoCallback callback) {
        mRepo.getReference(reviewId, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                RepoResult repoResult = result;
                if (!result.isReference()) {
                    repoResult = new RepoResult(CallbackMessage.error("Error retrieving reference"));
                }
                callback.onRepoCallback(repoResult);
            }
        });
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }
}
