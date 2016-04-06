/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.RowTag;

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
    public void addReview(Review review, CallbackRepositoryMutable callback) {
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

        callback.onAddedCallback(review, result);
    }

    @Override
    public void getReview(ReviewId reviewId, CallbackRepository callback) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, clause, transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);

        Iterator<Review> iterator = reviews.iterator();

        Review review = null;
        CallbackMessage message = CallbackMessage.ok(reviewId.toString() + " found");
        if(iterator.hasNext()) review = iterator.next();
        if(iterator.hasNext()) {
            message = CallbackMessage.error("There is more than 1 review with id: " + reviewId);
        } else if(review == null) {
            message = CallbackMessage.error("Review not found: " + reviewId);
        }

        callback.onFetchedFromRepo(review, message);
    }

    @Override
    public void getReviews(CallbackRepository callback) {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, REVIEW_CLAUSE, transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);

        CallbackMessage result = CallbackMessage.ok(reviews.size() + " reviews found");
        callback.onFetchedFromRepo(reviews, result);
    }

    @Override
    public void removeReview(ReviewId reviewId, CallbackRepositoryMutable callback) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.deleteReviewFromDb(reviewId, mTagsManager, transactor);
        mDatabase.endTransaction(transactor);

        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(reviewId + " removed");
            notifyOnDeleteReview(reviewId);
        } else{
            result = CallbackMessage.error("Problems deleting review: " + reviewId);
        }

        callback.onRemovedCallback(reviewId, result);
    }

    private void loadTagsIfNecessary(TableTransactor transactor) {
        if(mTagsLoaded) return;
        TableRowList<RowTag> rows = mDatabase.loadTable(mDatabase.getTagsTable(), transactor);
        for (RowTag row : rows) {
            ArrayList<String> reviewIds = row.getReviewIds();
            for(String reviewId : reviewIds) {
                if(!mTagsManager.tagsItem(reviewId, row.getTag())){
                    mTagsManager.tagItem(reviewId, row.getTag());
                }
            }
        }

        mTagsLoaded = true;
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
