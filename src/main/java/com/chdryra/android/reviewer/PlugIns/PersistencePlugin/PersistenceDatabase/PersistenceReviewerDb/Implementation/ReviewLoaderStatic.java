/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.TableTransactor;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewerReadableDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
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
    private FactoryReviewFromDataHolder mFactory;

    public ReviewLoaderStatic(FactoryReviewFromDataHolder factory, DataValidator validator) {
        mFactory = factory;
        mValidator = validator;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerReadableDb db, TableTransactor
            transactor) {
        if (!reviewRow.hasData(mValidator)) return null;

        String subject = reviewRow.getSubject();
        float rating = reviewRow.getRating();
        int ratingWeight = reviewRow.getRatingWeight();
        boolean isAverage = reviewRow.isRatingIsAverage();
        PublishDate publishDate = new PublishDate(reviewRow.getPublishDate());

        String reviewId = reviewRow.getReviewId().toString();

        ArrayList<RowComment> comments = loadData(db, transactor, db.getCommentsTable(),
                RowComment.REVIEW_ID, reviewId);
        ArrayList<RowFact> facts = loadData(db, transactor, db.getFactsTable(),
                RowFact.REVIEW_ID, reviewId);
        ArrayList<RowLocation> locations = loadData(db, transactor, db.getLocationsTable(),
                RowLocation.REVIEW_ID, reviewId);
        ArrayList<RowImage> images = loadData(db, transactor, db.getImagesTable(),
                RowImage.REVIEW_ID, reviewId);

        ArrayList<Review> critList = loadCriteria(db, transactor, reviewId);
        DataAuthor author = loadAuthor(db, transactor, reviewRow.getAuthorId());

        ReviewDataHolder reviewDb = new ReviewDataHolderImpl(reviewRow.getReviewId(), author,
                publishDate, subject, rating, ratingWeight, comments, images, facts, locations,
                critList, isAverage);

        return mFactory.recreateReview(reviewDb);
    }

    private <T extends DbTableRow> ArrayList<T> loadData(ReviewerReadableDb database,
                                                         TableTransactor transactor,
                                                         DbTable<T> table,
                                                         ColumnInfo<String> reviewIdCol,
                                                         String reviewId) {
        ArrayList<T> data = new ArrayList<>();
        data.addAll(database.getRowsWhere(table, asClause(reviewIdCol, reviewId), transactor));
        return data;
    }

    private ArrayList<Review> loadCriteria(ReviewerReadableDb db, TableTransactor transactor,
                                           String reviewId) {
        RowEntry<String> clause = asClause(RowReview.PARENT_ID, reviewId);
        return db.loadReviewsWhere(db.getReviewsTable(), clause, transactor);
    }

    @NonNull
    private DataAuthor loadAuthor(ReviewerReadableDb db, TableTransactor transactor, String
            userId) {
        RowEntry<String> clause = asClause(RowAuthor.USER_ID, userId);
        RowAuthor authorRow = db.getUniqueRowWhere(db.getAuthorsTable(), clause, transactor);
        return new DatumAuthor(authorRow.getName(), authorRow.getUserId());
    }

    private <T> RowEntry<T> asClause(ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(column, value);
    }
}
