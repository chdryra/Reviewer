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
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.chdryra.android.reviewer.Persistence.Interfaces.PlaylistCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbMutable extends ReviewerDbAuthored implements MutableRepository {
    public ReviewerDbMutable(AuthorId authorId, ReviewerDbRepository repo) {
        super(authorId, repo);
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
                if (result.isReview()) {
                    Review review = result.getReview();
                    if (isCorrectAuthor(review)) {
                        getRepo().removeReview(reviewId, callback);
                    } else {
                        callback.onRemovedFromRepoCallback(wrongAuthor());
                    }
                }
            }
        });
    }

    @Override
    public Playlist getPlaylist(String name) {
        return nullPlaylist();
    }

    @NonNull
    private Playlist nullPlaylist() {
        return new Playlist() {
            @Override
            public void addEntry(ReviewId reviewId, PlaylistCallback callback) {
                callback.onAddedToPlaylistCallback(error());
            }

            @Override
            public void removeEntry(ReviewId reviewId, PlaylistCallback callback) {
                callback.onRemovedFromPlaylistCallback(error());
            }

            @Override
            public void hasEntry(ReviewId reviewId, PlaylistCallback callback) {
                callback.onPlaylistHasReviewCallback(false, error());
            }

            @Override
            public void subscribe(ReviewsSubscriber subscriber) {

            }

            @Override
            public void unsubscribe(ReviewsSubscriber subscriber) {

            }

            @Override
            public void getReference(ReviewId reviewId, RepositoryCallback callback) {

            }

            @NonNull
            private CallbackMessage error() {
                return CallbackMessage.error("null playlist");
            }
        };
    }

    private boolean isCorrectAuthor(Review review) {
        return review.getAuthorId().toString().equals(getAuthorId().toString());
    }

    @NonNull
    private RepositoryResult wrongAuthor() {
        return new RepositoryResult(CallbackMessage.error("Wrong author"));
    }
}
