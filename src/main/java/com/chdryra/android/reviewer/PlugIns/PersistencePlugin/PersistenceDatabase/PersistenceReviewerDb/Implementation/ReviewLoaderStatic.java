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
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewRecreater;
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
    private ReviewRecreater mRecreater;

    public ReviewLoaderStatic(ReviewRecreater recreater, DataValidator validator) {
        mRecreater = recreater;
        mValidator = validator;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerReadableDb db, TableTransactor
            transactor) {
        if (!reviewRow.hasData(mValidator)) return null;

        String id = reviewRow.getReviewId().toString();

        ArrayList<RowComment> comments
                = loadData(db.getCommentsTable(), RowComment.REVIEW_ID, id, db, transactor);
        ArrayList<RowFact> facts
                = loadData(db.getFactsTable(), RowFact.REVIEW_ID, id, db, transactor);
        ArrayList<RowLocation> locations
                = loadData(db.getLocationsTable(), RowLocation.REVIEW_ID, id, db, transactor);
        ArrayList<RowImage> images
                = loadData(db.getImagesTable(), RowImage.REVIEW_ID, id, db, transactor);

        ArrayList<Review> critList = loadCriteria(id, db, transactor);
        DataAuthor author = loadAuthor(reviewRow.getAuthorId(), db, transactor);

        ReviewDataHolder reviewDb = newReviewDataHolder(reviewRow, comments, facts, locations,
                images, critList, author);

        return mRecreater.recreateReview(reviewDb);
    }

    @NonNull
    private ReviewDataHolderImpl newReviewDataHolder(RowReview reviewRow,
                                                     ArrayList<RowComment> comments,
                                                     ArrayList<RowFact> facts,
                                                     ArrayList<RowLocation> locations,
                                                     ArrayList<RowImage> images,
                                                     ArrayList<Review> critList,
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

    private <T extends DbTableRow> ArrayList<T> loadData(DbTable<T> table,
                                                         ColumnInfo<String> idCol,
                                                         String id,
                                                         ReviewerReadableDb database,
                                                         TableTransactor transactor) {
        ArrayList<T> data = new ArrayList<>();
        data.addAll(database.getRowsWhere(table, asClause(idCol, id), transactor));
        return data;
    }

    private ArrayList<Review> loadCriteria(String reviewId, ReviewerReadableDb db,
                                           TableTransactor transactor) {
        RowEntry<String> clause = asClause(RowReview.PARENT_ID, reviewId);
        return db.loadReviewsWhere(db.getReviewsTable(), clause, transactor);
    }

    @NonNull
    private DataAuthor loadAuthor(String userId, ReviewerReadableDb db,
                                  TableTransactor transactor) {
        RowEntry<String> clause = asClause(RowAuthor.USER_ID, userId);
        RowAuthor authorRow = db.getUniqueRowWhere(db.getAuthorsTable(), clause, transactor);
        return new DatumAuthor(authorRow.getName(), authorRow.getUserId());
    }

    private <T> RowEntry<T> asClause(ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(column, value);
    }
}
