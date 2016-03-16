/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin
        .Api.TableTransactor;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewLoader {
    private DataValidator mValidator;
    private ReviewRecreater mRecreater;

    public ReviewLoaderStatic(ReviewRecreater recreater, DataValidator validator) {
        mRecreater = recreater;
        mValidator = validator;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDbReadable db, TableTransactor
            transactor) {
        if (!reviewRow.hasData(mValidator)) return null;

        String id = reviewRow.getReviewId().toString();

        Iterable<RowComment> comments = loadData(db.getCommentsTable(),
                asClause(RowComment.class, RowComment.REVIEW_ID, id), db, transactor);
        Iterable<RowFact> facts = loadData(db.getFactsTable(),
                asClause(RowFact.class, RowFact.REVIEW_ID, id), db, transactor);
        Iterable<RowLocation> locations = loadData(db.getLocationsTable(),
                asClause(RowLocation.class, RowLocation.REVIEW_ID, id), db, transactor);
        Iterable<RowImage> images = loadData(db.getImagesTable(),
                asClause(RowImage.class, RowImage.REVIEW_ID, id), db, transactor);

        Iterable<Review> critList = loadCriteria(id, db, transactor);
        DataAuthor author = loadAuthor(reviewRow.getAuthorId(), db, transactor);

        ReviewDataHolder reviewDb = newReviewDataHolder(reviewRow, comments, facts, locations,
                images, critList, author);

        return mRecreater.recreateReview(reviewDb);
    }

    @NonNull
    private ReviewDataHolderImpl newReviewDataHolder(RowReview reviewRow,
                                                     Iterable<RowComment> comments,
                                                     Iterable<RowFact> facts,
                                                     Iterable<RowLocation> locations,
                                                     Iterable<RowImage> images,
                                                     Iterable<Review> critList,
                                                     DataAuthor author) {
        String subject = reviewRow.getSubject();
        float rating = reviewRow.getRating();
        int ratingWeight = reviewRow.getRatingWeight();
        boolean isAverage = reviewRow.isRatingIsAverage();
        PublishDate publishDate = new PublishDate(reviewRow.getPublishDate());

        return new ReviewDataHolderImpl(reviewRow.getReviewId(), author,
                publishDate, subject, rating, ratingWeight, comments, images, facts, locations,
                critList, isAverage);
    }

    private <DbRow extends DbTableRow> Iterable<DbRow> loadData(DbTable<DbRow> table,
                                                         RowEntry<DbRow, ?> idClause,
                                                         ReviewerDbReadable db,
                                                         TableTransactor transactor) {
        ArrayList<DbRow> data = new ArrayList<>();
        data.addAll(db.getRowsWhere(table, idClause, transactor));
        return data;
    }

    private Iterable<Review> loadCriteria(String reviewId, ReviewerDbReadable db,
                                           TableTransactor transactor) {
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.PARENT_ID, reviewId);
        return db.loadReviewsWhere(db.getReviewsTable(), clause, transactor);
    }

    @NonNull
    private DataAuthor loadAuthor(String userId, ReviewerDbReadable db,
                                  TableTransactor transactor) {
        RowEntry<RowAuthor, String> clause = asClause(RowAuthor.class, RowAuthor.USER_ID, userId);
        RowAuthor authorRow = db.getUniqueRowWhere(db.getAuthorsTable(), clause, transactor);
        return new DatumAuthor(authorRow.getName(), authorRow.getUserId());
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
