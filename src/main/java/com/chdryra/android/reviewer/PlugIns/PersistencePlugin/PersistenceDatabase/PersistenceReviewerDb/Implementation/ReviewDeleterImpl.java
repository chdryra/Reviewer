/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterImpl implements ReviewDeleter {
    private FactoryDbTableRow mRowFactory;

    public ReviewDeleterImpl(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    @Override
    public boolean deleteReviewFromDb(RowReview row, TagsManager tagsManager,
                                      ReviewerDb db, TableTransactor transactor) {
        String idString = row.getReviewId().toString();

        deleteFromTable(db.getImagesTable(),
                asClause(RowImage.class, RowImage.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getLocationsTable(),
                asClause(RowLocation.class, RowLocation.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getFactsTable(),
                asClause(RowFact.class, RowFact.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getCommentsTable(),
                asClause(RowComment.class, RowComment.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getReviewsTable(),
                asClause(RowReview.class, RowReview.REVIEW_ID, idString), transactor);
        deleteCriteria(db.getReviewsTable(), idString, transactor);
        deleteTagsIfNecessary(db.getTagsTable(), idString, tagsManager, transactor);
        deleteAuthorIfNecessary(db, row, transactor);

        return true;
    }

    private void deleteCriteria(DbTable<RowReview> reviewsTable,
                                String reviewId,
                                TableTransactor transactor) {
        RowEntry<RowReview, String> reviewIsParent
                = asClause(RowReview.class, RowReview.PARENT_ID, reviewId);
        TableRowList<RowReview> rows
                = transactor.getRowsWhere(reviewsTable, reviewIsParent, mRowFactory);
        for (RowReview row : rows) {
            RowEntry<RowReview, String> idClause = asClause(RowReview.class, RowReview.REVIEW_ID,
                    row.getReviewId().toString());
            deleteFromTable(reviewsTable, idClause, transactor);
        }
    }

    private void deleteTagsIfNecessary(DbTable<RowTag> tagsTable, String reviewId,
                                       TagsManager tagsManager, TableTransactor transactor) {
        ItemTagCollection tags = tagsManager.getTags(reviewId);
        for (ItemTag tag : tags) {
            if (tagsManager.untagItem(reviewId, tag)) {
                RowEntry<RowTag, String> tagClause
                        = asClause(RowTag.class, RowTag.TAG, tag.getTag());
                deleteFromTable(tagsTable, tagClause, transactor);
            }
        }
    }

    private void deleteAuthorIfNecessary(ReviewerDb db, RowReview row,
                                         TableTransactor transactor) {
        String userId = row.getAuthorId();
        DbTable<RowReview> reviewsTable = db.getReviewsTable();
        RowEntry<RowReview, String> clause = asClause(RowReview.class, RowReview.USER_ID, userId);

        TableRowList<RowReview> authored = transactor.getRowsWhere(reviewsTable, clause, mRowFactory);
        if (authored.size() == 0) {
            RowEntry<RowAuthor, String> userIdClause
                    = asClause(RowAuthor.class, RowAuthor.USER_ID, userId);
            deleteFromTable(db.getAuthorsTable(), userIdClause, transactor);
        }
    }

    private <DbRow extends DbTableRow> void deleteFromTable(DbTable<DbRow> table,
                                                            RowEntry<DbRow, ?> clause,
                                                            TableTransactor transactor) {
        transactor.deleteRowsWhere(table, clause);
    }

    @NonNull
    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                               ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
