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
    public boolean deleteReviewFromDb(RowReview row, ReviewerDb db, TableTransactor transactor) {
        String idString = row.getReviewId().toString();

        deleteFromTable(db.getImagesTable(), RowImage.REVIEW_ID, idString, transactor);
        deleteFromTable(db.getLocationsTable(), RowLocation.REVIEW_ID, idString, transactor);
        deleteFromTable(db.getFactsTable(), RowFact.REVIEW_ID, idString, transactor);
        deleteFromTable(db.getCommentsTable(), RowComment.REVIEW_ID, idString, transactor);
        deleteFromTable(db.getReviewsTable(), RowReview.REVIEW_ID, idString, transactor);
        deleteCriteriaFromReviewsTable(db.getReviewsTable(), RowReview.REVIEW_ID, idString,
                transactor);
        deleteFromTagsTableIfNecessary(db.getTagsTable(), db.getTagsManager(), idString,
                transactor);
        deleteFromAuthorsTableIfNecessary(db, row, transactor);

        return true;
    }

    private void deleteFromTagsTableIfNecessary(DbTable<RowTag> tagsTable, TagsManager tagsManager,
                                                String reviewId, TableTransactor transactor) {
        ItemTagCollection tags = tagsManager.getTags(reviewId);
        for (ItemTag tag : tags) {
            if (tagsManager.untagItem(reviewId, tag)) {
                deleteFromTable(tagsTable, RowTag.TAG, tag.getTag(), transactor);
            }
        }
    }

    private void deleteFromAuthorsTableIfNecessary(ReviewerDb db, RowReview row,
                                                   TableTransactor transactor) {
        String userId = row.getAuthorId();
        DbTable<RowReview> reviewsTable = db.getReviewsTable();
        RowEntry<String> clause = asEntry(RowReview.USER_ID, userId);

        TableRowList<RowReview> authored = transactor.getRowsWhere(reviewsTable, clause, mRowFactory);
        if (authored.size() == 0) {
            deleteFromTable(db.getAuthorsTable(), RowAuthor.USER_ID, userId, transactor);
        }
    }

    private void deleteCriteriaFromReviewsTable(DbTable<RowReview> reviewsTable,
                                                ColumnInfo<String> idCol, String id,
                                                TableTransactor transactor) {
        RowEntry<String> clause = asEntry(RowReview.PARENT_ID, id);
        TableRowList<RowReview> rows = transactor.getRowsWhere(reviewsTable, clause, mRowFactory);
        for (RowReview row : rows) {
            deleteFromTable(reviewsTable, idCol, row.getReviewId().toString(), transactor);
        }
    }

    private void deleteFromTable(DbTable<?> table, ColumnInfo<String> rowIdCol, String rowId, TableTransactor transactor) {
        transactor.deleteRows(table, asEntry(rowIdCol, rowId));
    }

    @NonNull
    private <T> RowEntry<T> asEntry(ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(column, value);
    }
}
