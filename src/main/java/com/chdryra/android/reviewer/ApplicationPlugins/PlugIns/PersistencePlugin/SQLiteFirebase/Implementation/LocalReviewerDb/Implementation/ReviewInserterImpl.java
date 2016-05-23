/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewInserter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;

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
    public void addReviewToDb(Review review, TagsManager tagsManager,
                              ReviewerDb db, TableTransactor transactor) {
        addToTable(review, db.getReviewsTable(), transactor);
        addToTable(review.getCriteria(), db.getCriteriaTable(), transactor, true);
        addToTable(review.getComments(), db.getCommentsTable(), transactor, true);
        addToTable(review.getFacts(), db.getFactsTable(), transactor, true);
        addToTable(review.getLocations(), db.getLocationsTable(), transactor, true);
        addToTable(review.getImages(), db.getImagesTable(), transactor, true);
        addToTagsTable(tagsManager.getTags(review.getReviewId().toString()), db.getTagsTable(), transactor);
        addToAuthorsTableIfNecessary(review.getAuthor(), db.getAuthorsTable(), transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(T data,
                                                          DbTable<DbRow> table,
                                                          TableTransactor transactor) {
        insertIntoTable(mRowFactory.newRow(table.getRowClass(), data), table, transactor);
    }

    private <DbRow extends DbTableRow, T> void addToTable(Iterable<? extends T> data,
                            DbTable<DbRow> table,
                            TableTransactor transactor, boolean indexed) {
        int i = 1;
        for (T datum : data) {
            DbRow row = indexed ? mRowFactory.newRow(table.getRowClass(), datum, i++) :
                    mRowFactory.newRow(table.getRowClass(), datum);
            insertIntoTable(row, table, transactor);
        }
    }

    private void addToAuthorsTableIfNecessary(DataAuthor author, DbTable<RowAuthor> table,
                                              TableTransactor transactor) {
        String userId = author.getAuthorId().toString();
        if (!transactor.isIdInTable(userId, table.getColumn(RowAuthor.USER_ID.getName()), table)) {
            insertIntoTable(mRowFactory.newRow(table.getRowClass(), author), table, transactor);
        }
    }

    private void addToTagsTable(ItemTagCollection tags, DbTable<RowTag> table, TableTransactor transactor) {
        for (ItemTag tag : tags) {
            transactor.insertOrReplaceRow(mRowFactory.newRow(table.getRowClass(), tag), table);
        }
    }

    private <T extends DbTableRow> void insertIntoTable(T row, DbTable<T> table, TableTransactor
            transactor) {
        transactor.insertRow(row, table);
    }
}
