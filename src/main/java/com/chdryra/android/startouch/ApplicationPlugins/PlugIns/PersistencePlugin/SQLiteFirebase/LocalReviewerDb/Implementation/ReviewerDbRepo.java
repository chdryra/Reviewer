/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepo implements ReviewsRepoWriteable {
    private final ReviewerDb mDatabase;
    private final List<ReviewsSubscriber> mSubscribers;
    private final FactoryDbReference mReferenceFactory;

    public ReviewerDbRepo(ReviewerDb database,
                          FactoryDbReference referenceFactory) {
        mDatabase = database;
        mReferenceFactory = referenceFactory;
        mSubscribers = new ArrayList<>();
    }

    ReviewerDbReadable getReadableDatabase() {
        return mDatabase;
    }

    public void getReviews(AuthorId authorId, RepoCallback callback) {
        //TODO push author clause to SQL
        Collection<Review> allReviews = getAllReviews();
        ArrayList<Review> reviews = new ArrayList<>();
        for (Review review : allReviews) {
            if (review.getAuthorId().toString().equals(authorId.toString())) reviews.add(review);
        }
        CallbackMessage result = CallbackMessage.ok(reviews.size() + " reviews found");
        callback.onRepoCallback(new RepoResult(authorId, reviews, result));
    }

    @Override
    public void addReview(Review review, Callback callback) {
        TableTransactor db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.addReviewToDb(review, db);
        mDatabase.endTransaction(db);

        String subject = review.getSubject().getSubject();
        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(subject + " added");
            notifyOnAddReview(review);
        } else {
            result = CallbackMessage.error(subject + ": Problem adding review to database");
        }

        callback.onAddedToRepo(new RepoResult(review, result));
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        Iterator<Review> iterator = getReviewIterator(reviewId);
        Review review = null;
        if (iterator.hasNext()) review = iterator.next();

        CallbackMessage message = CallbackMessage.ok(reviewId.toString() + " found");
        if (iterator.hasNext()) {
            message = CallbackMessage.error("There is more than 1 review with id: " + reviewId);
        } else if (review == null) {
            message = CallbackMessage.error("Review not found: " + reviewId);
        }

        callback.onRepoCallback(new RepoResult(review, message));
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        RowEntry<RowReview, String> reviewClause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();

        TableRowList<RowReview> reviews = mDatabase.getRowsWhere(mDatabase.getReviewsTable(),
                reviewClause, transactor);
        Iterator<RowReview> iterator = reviews.iterator();

        RepoResult result;
        if (iterator.hasNext()) {
            result = new RepoResult(newReference(iterator.next()));
        } else {
            result = new RepoResult(CallbackMessage.error("Review not found"));
        }
        callback.onRepoCallback(result);

        mDatabase.endTransaction(transactor);
    }

    @Override
    public void removeReview(ReviewId reviewId, Callback callback) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        Iterator<Review> iterator = getReviewIterator(reviewId);
        Review review = null;
        boolean success = false;
        if (iterator.hasNext()) {
            review = iterator.next();
            success = mDatabase.deleteReviewFromDb(reviewId, transactor);
        }
        mDatabase.endTransaction(transactor);

        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(reviewId + " removed");
            notifyOnDeleteReview(review);
        } else {
            result = CallbackMessage.error("Problems deleting review: " + reviewId);
        }

        callback.onRemovedFromRepo(new RepoResult(reviewId, result));
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        if (!mSubscribers.contains(subscriber)) mSubscribers.add(subscriber);
        getReferences(subscriber, null);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        if (mSubscribers.contains(subscriber)) mSubscribers.remove(subscriber);
    }

    private Collection<Review> getAllReviews() {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviews(transactor);
        mDatabase.endTransaction(transactor);
        return reviews;
    }

    @NonNull
    private Iterator<Review> getReviewIterator(ReviewId reviewId) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews
                = mDatabase.loadReviewsWhere(mDatabase.getReviewsTable(), clause, transactor);
        mDatabase.endTransaction(transactor);

        return reviews.iterator();
    }

    void getReferences(ReviewsSubscriber subscriber, @Nullable AuthorId authorId) {
        RowEntry<RowReview, String> authorClause = authorId == null ? null :
                asClause(RowReview.class, RowReview.AUTHOR_ID, authorId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();

        TableRowList<RowReview> reviews;
        if (authorClause == null) {
            reviews = mDatabase.loadTable(mDatabase.getReviewsTable(), transactor);
        } else {
            reviews = mDatabase.getRowsWhere(mDatabase.getReviewsTable(), authorClause, transactor);
        }

        for (RowReview review : reviews) {
            subscriber.onReviewAdded(newReference(review));
        }

        mDatabase.endTransaction(transactor);
    }

    private ReviewReference newReference(RowReview review) {
        return mReferenceFactory.newReference(review, this);
    }


    private void notifyOnAddReview(Review review) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewAdded(newReference(review));
        }
    }

    private ReviewReference newReference(Review review) {
        return mReferenceFactory.newReference(review, this);
    }

    private void notifyOnDeleteReview(Review review) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewRemoved(newReference(review));
        }
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      @Nullable T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }

}
