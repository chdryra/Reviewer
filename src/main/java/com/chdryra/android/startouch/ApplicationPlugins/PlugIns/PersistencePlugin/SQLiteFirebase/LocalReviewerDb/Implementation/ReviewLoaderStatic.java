/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewDataHolderImpl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewDataHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewMaker;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewLoader {
    private final DataValidator mValidator;
    private final ReviewMaker mMaker;

    public ReviewLoaderStatic(ReviewMaker maker, DataValidator validator) {
        mMaker = maker;
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
        Iterable<RowCriterion> criteria = loadData(db.getCriteriaTable(),
                asClause(RowCriterion.class, RowCriterion.REVIEW_ID, id), db, transactor);
        Iterable<RowTag> tags = loadData(db.getTagsTable(),
                asClause(RowTag.class, RowTag.REVIEW_ID, id), db, transactor);
        
        ReviewDataHolder reviewDb
                = newDataHolder(reviewRow, tags, comments, facts, locations, images, criteria);

        return mMaker.makeReview(reviewDb);
    }

    @NonNull
    private ReviewDataHolder newDataHolder(RowReview reviewRow,
                                           Iterable<RowTag> tags,
                                           Iterable<RowComment> comments,
                                           Iterable<RowFact> facts,
                                           Iterable<RowLocation> locations,
                                           Iterable<RowImage> images,
                                           Iterable<RowCriterion> criteria) {
        ReviewId reviewId = reviewRow.getReviewId();
        String subject = reviewRow.getSubject();
        float rating = reviewRow.getRating();
        int ratingWeight = reviewRow.getRatingWeight();
        PublishDate publishDate = new PublishDate(reviewRow.getPublishDate());

        AuthorId authorId = new AuthorIdParcelable(reviewRow.getAuthorId());
        return new ReviewDataHolderImpl(reviewId, authorId, publishDate, subject, rating, 
                ratingWeight, tags, comments, images, facts, locations, criteria);
    }

    private <DbRow extends DbTableRow> Iterable<DbRow> loadData(DbTable<DbRow> table,
                                                                RowEntry<DbRow, ?> idClause,
                                                                ReviewerDbReadable db,
                                                                TableTransactor transactor) {
        ArrayList<DbRow> data = new ArrayList<>();
        data.addAll(db.getRowsWhere(table, idClause, transactor));
        return data;
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
