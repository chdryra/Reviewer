/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbMutable extends ReviewerDbAuthored implements MutableRepository {
    public ReviewerDbMutable(DataAuthor author, ReviewerDbRepository repo) {
        super(author, repo);
    }

    @Override
    public void addReview(Review review, MutableRepoCallback callback) {
        if (isCorrectAuthor(review)) {
            getRepo().addReview(review, callback);
        } else {
            callback.onAddedToRepoCallback(wrongAuthor());
        }
    }

    @Override
    public void removeReview(final ReviewId reviewId, final MutableRepoCallback callback) {
        getRepo().getReview(reviewId, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                Review review = result.getReview();
                if (result.isReview() && review != null) {
                    if (isCorrectAuthor(review)) {
                        getRepo().removeReview(reviewId, callback);
                    } else {
                        callback.onRemovedFromRepoCallback(wrongAuthor());
                    }
                }
            }
        });
    }

    private boolean isCorrectAuthor(Review review) {
        return review.getAuthor().getAuthorId().equals(getAuthor().getAuthorId());
    }

    @NonNull
    private RepositoryResult wrongAuthor() {
        return new RepositoryResult(CallbackMessage.error("Wrong author"));
    }
}
