/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbAuthored implements AuthorsRepository, ReviewsSubscriber {
    private DataAuthor mAuthor;
    private ReviewerDbRepository mRepo;
    protected List<ReviewsSubscriber> mSubscribers;

    public ReviewerDbAuthored(DataAuthor author, ReviewerDbRepository repo) {
        mAuthor = author;
        mRepo = repo;
        mSubscribers = new ArrayList<>();
    }

    public ReviewerDbRepository getRepo() {
        return mRepo;
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public void bind(ReviewsSubscriber subscriber) {
        if(!mSubscribers.contains(subscriber)) mSubscribers.add(subscriber);
        if(mSubscribers.size() == 1) {
            mRepo.bind(this);
        } else {
            mRepo.getReferences(subscriber, mAuthor);
        }
    }

    @Override
    public void unbind(ReviewsSubscriber subscriber) {
        if(mSubscribers.contains(subscriber)) mSubscribers.remove(subscriber);
        if(mSubscribers.size() == 0) mRepo.unbind(this);
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
        return reference.getAuthor().getAuthorId().equals(mAuthor.getAuthorId());
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
    public void getReference(ReviewId reviewId, final RepositoryCallback callback) {
        mRepo.getReference(reviewId, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                RepositoryResult repoResult = result;
                if (!result.isReference() || result.getReference() == null) {
                    repoResult = new RepositoryResult(CallbackMessage.error("Error retrieving reference"));
                }
                callback.onRepositoryCallback(repoResult);
            }
        });
    }
}
