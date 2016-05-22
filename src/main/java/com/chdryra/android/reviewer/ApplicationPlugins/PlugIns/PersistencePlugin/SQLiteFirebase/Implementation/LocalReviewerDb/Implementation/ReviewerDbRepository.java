/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.TableTransactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepository implements ReviewsRepositoryMutable {
    public static final RowEntryImpl<RowReview, String> REVIEW_CLAUSE
            = new RowEntryImpl<>(RowReview.class, RowReview.PARENT_ID, null);
    private final ReviewerDb mDatabase;
    private final TagsManager mTagsManager;
    private final DbTable<RowReview> mTable;
    private final ArrayList<ReviewsRepositoryObserver> mObservers;
    private boolean mTagsLoaded = false;

    public ReviewerDbRepository(ReviewerDb database, TagsManager tagsManager) {
        mDatabase = database;
        mTagsManager = tagsManager;
        mTable = mDatabase.getReviewsTable();
        mObservers = new ArrayList<>();
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public void addReview(Review review, RepositoryMutableCallback callback) {
        TableTransactor db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.addReviewToDb(review, mTagsManager, db);
        mDatabase.endTransaction(db);

        String subject = review.getSubject().getSubject();
        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(subject + " added");
            notifyOnAddReview(review);
        } else {
            result = CallbackMessage.error(subject + ": Problem adding review to database");
        }

        callback.onAddedToRepoCallback(new RepositoryResult(review, result));
    }

    @Override
    public void getReview(ReviewId reviewId, RepositoryCallback callback) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, clause, transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);

        Iterator<Review> iterator = reviews.iterator();

        Review review = null;
        CallbackMessage message = CallbackMessage.ok(reviewId.toString() + " found");
        if (iterator.hasNext()) review = iterator.next();
        if (iterator.hasNext()) {
            message = CallbackMessage.error("There is more than 1 review with id: " + reviewId);
            review = null;
        } else if (review == null) {
            message = CallbackMessage.error("Review not found: " + reviewId);
        }

        callback.onRepositoryCallback(new RepositoryResult(review, message));
    }


    @Override
    public void getReviews(RepositoryCallback callback) {
        Collection<Review> reviews = getAllReviews();
        CallbackMessage result = CallbackMessage.ok(reviews.size() + " reviews found");
        callback.onRepositoryCallback(new RepositoryResult(null, reviews, result));
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        //TODO push author clause to SQL
        Collection<Review> allReviews = getAllReviews();
        ArrayList<Review> reviews = new ArrayList<>();
        for (Review review : allReviews) {
            if (review.getAuthor().getAuthorId().equals(author.getAuthorId())) reviews.add(review);
        }
        CallbackMessage result = CallbackMessage.ok(reviews.size() + " reviews found");
        callback.onRepositoryCallback(new RepositoryResult(author, reviews, result));
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.deleteReviewFromDb(reviewId, mTagsManager, transactor);
        mDatabase.endTransaction(transactor);

        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(reviewId + " removed");
            notifyOnDeleteReview(reviewId);
        } else {
            result = CallbackMessage.error("Problems deleting review: " + reviewId);
        }

        callback.onRemovedFromRepoCallback(new RepositoryResult(reviewId, result));
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
    }

    private Collection<Review> getAllReviews() {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, REVIEW_CLAUSE, transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);
        return reviews;
    }

    private void loadTagsIfNecessary(TableTransactor transactor) {
        if (mTagsLoaded) return;
        TableRowList<RowTag> rows = mDatabase.loadTable(mDatabase.getTagsTable(), transactor);
        for (RowTag row : rows) {
            ArrayList<String> reviewIds = row.getReviewIds();
            for (String reviewId : reviewIds) {
                if (!mTagsManager.tagsItem(reviewId, row.getTag())) {
                    mTagsManager.tagItem(reviewId, row.getTag());
                }
            }
        }

        mTagsLoaded = true;
    }

    private void notifyOnAddReview(Review review) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void notifyOnDeleteReview(ReviewId reviewId) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      @Nullable T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
