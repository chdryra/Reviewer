/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepository implements ReviewsRepositoryMutable{
    public static final RowEntryImpl<RowReview, String> REVIEW_CLAUSE
            = new RowEntryImpl<>(RowReview.class, RowReview.PARENT_ID, null);
    private final ReviewerDb mDatabase;
    private final TagsManager mTagsManager;
    private final DbTable<RowReview> mTable;
    private final ArrayList<ReviewsRepositoryObserver> mObservers;

    public ReviewerDbRepository(ReviewerDb database, TagsManager tagsManager) {
        mDatabase = database;
        mTagsManager = tagsManager;
        mTable = database.getReviewsTable();
        mObservers = new ArrayList<>();
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public void addReview(Review review) {
        TableTransactor db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.addReviewToDb(review, db);
        mDatabase.endTransaction(db);

        if (success) notifyOnAddReview(review);
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, clause, transactor);
        mDatabase.endTransaction(transactor);

        Iterator<Review> iterator = reviews.iterator();

        Review review = null;
        if(iterator.hasNext()) review = iterator.next();
        if(iterator.hasNext()) {
            throw new IllegalStateException("There is more than 1 review with id " + reviewId);
        }

        return review;
    }

    @Override
    public Collection<Review> getReviews() {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, REVIEW_CLAUSE, transactor);
        mDatabase.endTransaction(transactor);

        return reviews;
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.deleteReviewFromDb(reviewId, transactor);
        mDatabase.endTransaction(transactor);
        if (success) notifyOnDeleteReview(reviewId);
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
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
