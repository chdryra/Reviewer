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
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.LocalRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepository implements LocalRepository {
    private final ReviewerDb mDatabase;
    private final TagsManager mTagsManager;
    private final DbTable<RowReview> mTable;
    private final List<ReviewsSubscriber> mSubscribers;
    private final FactoryDbReference mReferenceFactory;
    private final FactoryReviewsRepository mRepoFactory;
    private boolean mTagsLoaded = false;

    public ReviewerDbRepository(ReviewerDb database,
                                TagsManager tagsManager,
                                FactoryReviewsRepository repoFactory,
                                FactoryDbReference referenceFactory) {
        mDatabase = database;
        mTagsManager = tagsManager;
        mRepoFactory = repoFactory;
        mReferenceFactory = referenceFactory;
        mTable = mDatabase.getReviewsTable();
        mSubscribers = new ArrayList<>();
    }

    public ReviewerDbReadable getReadableDatabase() {
        return mDatabase;
    }

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
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public void addReview(Review review, MutableRepoCallback callback) {
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
        Iterator<Review> iterator = getReviewIterator(reviewId);
        Review review = null;
        if (iterator.hasNext()) review = iterator.next();

        CallbackMessage message = CallbackMessage.ok(reviewId.toString() + " found");
        if (iterator.hasNext()) {
            message = CallbackMessage.error("There is more than 1 review with id: " + reviewId);
        } else if (review == null) {
            message = CallbackMessage.error("Review not found: " + reviewId);
        }

        callback.onRepositoryCallback(new RepositoryResult(review, message));
    }

    @Override
    public AuthorsRepository getRepository(DataAuthor author) {
        return mRepoFactory.newAuthorsRepo(author, this);
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        return mRepoFactory.newMutableRepo(session.getSessionAuthor(), this);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        RowEntry<RowReview, String> reviewClause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();

        TableRowList<RowReview> reviews = mDatabase.getRowsWhere(mDatabase.getReviewsTable(),
                reviewClause, transactor);
        Iterator<RowReview> iterator = reviews.iterator();

        RepositoryResult result;
        if (iterator.hasNext()) {
            RowReview review = iterator.next();
            RowAuthor author = mDatabase.getUniqueRowWhere(mDatabase.getAuthorsTable(),
                    asClause(RowAuthor.class, RowAuthor.AUTHOR_ID, review.getAuthorId()),
                    transactor);
            result = new RepositoryResult(newReference(review, author));
        } else {
            result = new RepositoryResult(CallbackMessage.error("Review not found"));
        }
        callback.onRepositoryCallback(result);

        mDatabase.endTransaction(transactor);
    }

    @Override
    public void removeReview(ReviewId reviewId, MutableRepoCallback callback) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        Iterator<Review> iterator = getReviewIterator(reviewId);
        Review review = null;
        boolean success = false;
        if (iterator.hasNext()) {
            review = iterator.next();
            success = mDatabase.deleteReviewFromDb(reviewId, mTagsManager, transactor);
        }
        mDatabase.endTransaction(transactor);

        CallbackMessage result;
        if (success) {
            result = CallbackMessage.ok(reviewId + " removed");
            notifyOnDeleteReview(review);
        } else {
            result = CallbackMessage.error("Problems deleting review: " + reviewId);
        }

        callback.onRemovedFromRepoCallback(new RepositoryResult(reviewId, result));
    }

    @Override
    public void bind(ReviewsSubscriber subscriber) {
        if (!mSubscribers.contains(subscriber)) mSubscribers.add(subscriber);
        getReferences(subscriber, null);
    }

    @Override
    public void unbind(ReviewsSubscriber subscriber) {
        if (mSubscribers.contains(subscriber)) mSubscribers.remove(subscriber);
    }

    private Collection<Review> getAllReviews() {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviews(transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);
        return reviews;
    }

    @NonNull
    private Iterator<Review> getReviewIterator(ReviewId reviewId) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();
        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, clause, transactor);
        loadTagsIfNecessary(transactor);
        mDatabase.endTransaction(transactor);

        return reviews.iterator();
    }

    public void getReferences(ReviewsSubscriber subscriber, @Nullable DataAuthor author) {
        RowEntry<RowReview, String> authorClause = author == null ? null :
                asClause(RowReview.class, RowReview.AUTHOR_ID, author.getAuthorId().toString());

        TableTransactor transactor = mDatabase.beginReadTransaction();

        TableRowList<RowReview> reviews;
        if (authorClause == null) {
            reviews = mDatabase.loadTable(mDatabase.getReviewsTable(), transactor);
        } else {
            reviews = mDatabase.getRowsWhere(mDatabase.getReviewsTable(), authorClause, transactor);
        }

        Iterator<RowReview> iterator = reviews.iterator();

        for (RowReview review : reviews) {
            review = iterator.next();
            DataAuthor reviewAuthor = author;
            if (reviewAuthor == null) {
                RowEntry<RowAuthor, String> clause = asClause(RowAuthor.class, RowAuthor.AUTHOR_ID,
                        review.getAuthorId());
                reviewAuthor
                        = mDatabase.getUniqueRowWhere(mDatabase.getAuthorsTable(), clause,
                        transactor);
            }

            subscriber.onReviewAdded(newReference(review, reviewAuthor));
        }

        mDatabase.endTransaction(transactor);
    }

    private ReviewReference newReference(RowReview review, DataAuthor reviewAuthor) {
        return mReferenceFactory.newReference(review, reviewAuthor, this);
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
