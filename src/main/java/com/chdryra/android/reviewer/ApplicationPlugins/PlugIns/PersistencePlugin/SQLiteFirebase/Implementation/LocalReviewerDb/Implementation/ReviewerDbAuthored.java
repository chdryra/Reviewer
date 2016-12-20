/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbAuthored implements ReferencesRepository, ReviewsSubscriber {
    private final AuthorId mAuthorId;
    private final ReviewerDbRepository mRepo;
    private final List<ReviewsSubscriber> mSubscribers;

    public ReviewerDbAuthored(AuthorId authorId, ReviewerDbRepository repo) {
        mAuthorId = authorId;
        mRepo = repo;
        mSubscribers = new ArrayList<>();
    }

    ReviewerDbRepository getRepo() {
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
    public void getReference(ReviewId reviewId, final RepositoryCallback callback) {
        mRepo.getReference(reviewId, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                RepositoryResult repoResult = result;
                if (!result.isReference()) {
                    repoResult = new RepositoryResult(CallbackMessage.error("Error retrieving reference"));
                }
                callback.onRepositoryCallback(repoResult);
            }
        });
    }
}
