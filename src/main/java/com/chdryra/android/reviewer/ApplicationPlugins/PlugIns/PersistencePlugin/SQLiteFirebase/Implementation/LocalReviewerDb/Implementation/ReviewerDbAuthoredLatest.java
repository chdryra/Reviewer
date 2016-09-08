/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
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
public class ReviewerDbAuthoredLatest implements ReferencesRepository, ReviewsSubscriber {
    private ReviewReference mLatest;
    private final ReviewerDbAuthored mRepo;
    private final List<ReviewsSubscriber> mSubscribers;

    public ReviewerDbAuthoredLatest(ReviewerDbAuthored repo) {
        mRepo = repo;
        mSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        if(!mSubscribers.contains(subscriber)) {
            mSubscribers.add(subscriber);
            if(mSubscribers.size() == 1) {
                mRepo.subscribe(this);
            } else {
                subscriber.onReviewAdded(mLatest);
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
        if(mLatest != null) onReviewRemoved(mLatest);
        mLatest = reference;
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewAdded(mLatest);
        }
    }

    @Override
    public void onReviewEdited(ReviewReference reference) {

    }

    @Override
    public void onReviewRemoved(ReviewReference reference) {
        if(reference == mLatest) {
            for (ReviewsSubscriber subscriber : mSubscribers) {
                subscriber.onReviewRemoved(mLatest);
            }
            mLatest = null;
        }
    }

    @Override
    public void getReference(ReviewId reviewId, final RepositoryCallback callback) {
        RepositoryResult repoResult;
        if(reviewId.equals(mLatest.getReviewId())) {
            repoResult = new RepositoryResult(mLatest);
        } else {
            repoResult = new RepositoryResult(CallbackMessage.error("Reference not found"));
        }

        callback.onRepositoryCallback(repoResult);
    }
}
