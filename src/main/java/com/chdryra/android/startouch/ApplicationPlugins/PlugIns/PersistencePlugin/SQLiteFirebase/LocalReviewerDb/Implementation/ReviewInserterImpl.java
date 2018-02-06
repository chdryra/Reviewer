/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewInserter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInserterImpl implements ReviewInserter {
    private final FactoryDbTableRow mRowFactory;

    public ReviewInserterImpl(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    @Override
    public void addReviewToDb(Review review,
                              ReviewerDb db,
                              TableTransactor transactor) {
        deleteOldReviewIfNecessary(review.getReviewId(), db, transactor);
        addToTable(review, db.getReviewsTable(), transactor);
        addToTable(review.getCriteria(), db.getCriteriaTable(), transactor);
        addToTable(review.getComments(), db.getCommentsTable(), transactor);
        addToTable(review.getFacts(), db.getFactsTable(), transactor);
        addToTable(review.getLocations(), db.getLocationsTable(), transactor);
        addToTable(review.getImages(), db.getImagesTable(), transactor);
        addToTable(review.getTags(), db.getTagsTable(), transactor);
    }

    private void deleteOldReviewIfNecessary(ReviewId reviewId, ReviewerDb db, TableTransactor
            transactor) {
        boolean idInTable = transactor.isIdInTable(reviewId.toString(), db
                .getReviewsTable().getColumn(RowReview.REVIEW_ID.getName()), db.getReviewsTable());
        if(idInTable) db.deleteReviewFromDb(reviewId, transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(T data,
                                                          DbTable<DbRow> table,
                                                          TableTransactor transactor) {
        insert(mRowFactory.newRow(table.getRowClass(), data), table, transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(Iterable<? extends T> data,
                            DbTable<DbRow> table,
                            TableTransactor transactor) {
        int i = 1;
        for (T datum : data) {
            insert(mRowFactory.newRow(table.getRowClass(), datum, i++), table, transactor);
        }
    }

    private <T extends DbTableRow> void insert(T row, DbTable<T> table, TableTransactor transactor) {
        transactor.insertRow(row, table);
    }
}
