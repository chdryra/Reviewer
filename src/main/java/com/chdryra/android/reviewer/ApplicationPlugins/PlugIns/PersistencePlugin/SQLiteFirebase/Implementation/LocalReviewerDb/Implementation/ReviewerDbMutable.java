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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbMutable extends ReviewerDbAuthored implements ReviewsRepoMutable {
    public ReviewerDbMutable(AuthorId authorId, ReviewerDbRepo repo, ReviewDereferencer dereferencer) {
        super(authorId, repo, dereferencer);
    }

    @Override
    public void addReview(Review review, Callback callback) {
        if (isCorrectAuthor(review)) {
            getRepo().addReview(review, callback);
        } else {
            callback.onAddedToRepo(wrongAuthor());
        }
    }

    @Override
    public void removeReview(final ReviewId reviewId, final Callback callback) {
        getReview(reviewId, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReview()) {
                    Review review = result.getReview();
                    if (isCorrectAuthor(review)) {
                        getRepo().removeReview(reviewId, callback);
                    } else {
                        callback.onRemovedFromRepo(wrongAuthor());
                    }
                }
            }
        });
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        getRepo().getReview(reviewId, callback);
    }

    private boolean isCorrectAuthor(Review review) {
        return review.getAuthorId().toString().equals(getAuthorId().toString());
    }

    @NonNull
    private RepoResult wrongAuthor() {
        return new RepoResult(CallbackMessage.error("Wrong author"));
    }
}
