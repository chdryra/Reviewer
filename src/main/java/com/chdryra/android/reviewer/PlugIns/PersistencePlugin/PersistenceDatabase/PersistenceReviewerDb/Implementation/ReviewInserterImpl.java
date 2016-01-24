package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewInserter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.FactoryDbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInserterImpl implements ReviewInserter {
    private FactoryDbTableRow mRowFactory;

    public ReviewInserterImpl(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    @Override
    public void addReviewToDb(Review review, ReviewerDb db, TableTransactor transactor) {
        addToTable(review, db.getReviewsTable(), transactor);
        addToTable(review.getCriteria(), db.getReviewsTable(), transactor);
        addToDataTable(review.getComments(), db.getCommentsTable(), transactor);
        addToDataTable(review.getFacts(), db.getFactsTable(), transactor);
        addToDataTable(review.getLocations(), db.getLocationsTable(), transactor);
        addToDataTable(review.getImages(), db.getImagesTable(), transactor);
        addToTagsTable(review.getReviewId(), db.getTagsTable(), db.getTagsManager(), transactor);
        addToAuthorsTableIfNecessary(review.getAuthor(), db.getAuthorsTable(), transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(T data,
                                                          DbTable<DbRow> table,
                                                          TableTransactor transactor) {
        insertIntoTable(mRowFactory.newRow(table.getRowClass(), data), table, transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(IdableList<? extends T> data,
                            DbTable<DbRow> table,
                            TableTransactor transactor) {
        for (T datum : data) {
            insertIntoTable(mRowFactory.newRow(table.getRowClass(), datum), table, transactor);
        }
    }

    private <DbRow extends DbTableRow, T> void addToDataTable(IdableList<? extends T> data,
                                                              DbTable<DbRow> table,
                                                              TableTransactor transactor) {
        int i = 1;
        for (T datum : data) {
            insertIntoTable(mRowFactory.newRow(table.getRowClass(), datum, i++), table, transactor);
        }
    }

    private void addToAuthorsTableIfNecessary(DataAuthor author, DbTable<RowAuthor> table,
                                              TableTransactor transactor) {
        String userId = author.getUserId().toString();
        if (!transactor.isIdInTable(userId, table.getColumn(RowAuthor.USER_ID.getName()), table)) {
            insertIntoTable(mRowFactory.newRow(table.getRowClass(), author), table, transactor);
        }
    }

    private void addToTagsTable(ReviewId id, DbTable<RowTag> table, TagsManager tagsManager,
                                TableTransactor transactor) {
        ItemTagCollection tags = tagsManager.getTags(id.toString());
        for (ItemTag tag : tags) {
            transactor.insertOrReplaceRow(mRowFactory.newRow(table.getRowClass(), tag), table);
        }
    }

    private <T extends DbTableRow> void insertIntoTable(T row, DbTable<T> table, TableTransactor
            transactor) {
        transactor.insertRow(row, table);
    }
}
