/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DatabaseProvider;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.DatabaseInstance;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbImpl implements ReviewerDb {
    private final ReviewerDbContract mTables;
    private final DatabaseProvider<ReviewerDbContract> mDbProvider;
    private final TagsManager mTagsManager;
    private final ReviewLoader mReviewLoader;
    private final FactoryReviewerDbTableRow mRowFactory;
    private final DataValidator mDataValidator;

    public ReviewerDbImpl(DatabaseProvider<ReviewerDbContract> dbProvider,
                          ReviewLoader reviewLoader,
                          FactoryReviewerDbTableRow rowFactory,
                          TagsManager tagsManager,
                          DataValidator dataValidator) {
        mDbProvider = dbProvider;
        mTables = dbProvider.getContract();
        mTagsManager = tagsManager;
        mReviewLoader = reviewLoader;
        mRowFactory = rowFactory;
        mDataValidator = dataValidator;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public DatabaseInstance beginWriteTransaction() {
        DatabaseInstance db = mDbProvider.getWriteableInstance(mRowFactory);
        db.beginTransaction();
        return db;
    }

    @Override
    public DatabaseInstance beginReadTransaction() {
        DatabaseInstance db = mDbProvider.getReadableInstance(mRowFactory);
        db.beginTransaction();
        return db;
    }

    @Override
    public void endTransaction(DatabaseInstance db) {
        db.endTransaction();
    }

    @Override
    public ArrayList<Review> loadReviewsFromDbWhere(DatabaseInstance db, String col, @Nullable
    String val) {
        TableRowList<RowReview> reviewsList = db.getRowsWhere(getReviewsTable(), col, val);

        ArrayList<Review> reviews = new ArrayList<>();
        for (RowReview reviewRow : reviewsList) {
            reviews.add(loadReview(reviewRow, db));
        }

        return reviews;
    }

    @Override
    public <T extends DbTableRow> T getRowWhere(DatabaseInstance db, DbTable<T> table,
                                                String col, String val) {
        return db.getRowWhere(table, col, val);
    }

    @Override
    public <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col,
                                                               String val) {
        DatabaseInstance db = beginReadTransaction();
        TableRowList<T> rowList = db.getRowsWhere(table, col, val);
        endTransaction(db);

        return rowList;
    }

    @Override
    public <T1 extends DbTableRow> ArrayList<T1>
    loadFromDataTable(DatabaseInstance db, DbTable<T1> table, String reviewId) {
        TableRowList<T1> rows = db.getRowsWhere(table, getColumnNameReviewId(), reviewId);
        ArrayList<T1> results = new ArrayList<>();
        for (T1 row : rows) {
            results.add(row);
        }

        return results;
    }

    @Override
    public boolean addReviewToDb(Review review, DatabaseInstance db) {
        if (isReviewInDb(review, db)) return false;

        DataAuthor author = review.getAuthor();
        String userId = author.getUserId().toString();
        DbTable<RowAuthor> authorsTable = getAuthorsTable();
        if (!db.isIdInTable(userId, authorsTable.getColumn(RowAuthor.COLUMN_USER_ID),
                authorsTable)) {
            addToAuthorsTable(author, db);
        }

        addToReviewsTable(review, db);
        addCriteriaToReviewsTable(review, db);
        addToCommentsTable(review, db);
        addToFactsTable(review, db);
        addToLocationsTable(review, db);
        addToImagesTable(review, db);
        addToTagsTable(review, db);

        return true;
    }

    @Override
    public boolean deleteReviewFromDb(String reviewId, DatabaseInstance db) {
        RowReview row = getRowWhere(db, getReviewsTable(), getColumnNameReviewId(), reviewId);
        if (!row.hasData(mDataValidator)) return false;

        deleteFromImagesTable(reviewId, db);
        deleteFromLocationsTable(reviewId, db);
        deleteFromFactsTable(reviewId, db);
        deleteFromCommentsTable(reviewId, db);
        deleteCriteriaFromReviewsTable(reviewId, db);
        deleteFromReviewsTable(reviewId, db);

        String userId = row.getAuthorId();
        TableRowList<RowReview> authored = db.getRowsWhere(getReviewsTable(), RowReview
                .COLUMN_AUTHOR_ID, userId);
        if (authored.size() == 0) deleteFromAuthorsTable(userId, db);

        ItemTagCollection tags = mTagsManager.getTags(reviewId);
        for (ItemTag tag : tags) {
            if (mTagsManager.untagItem(reviewId, tag)) {
                deleteFromTagsTable(tag.getTag(), db);
            }
        }

        return true;
    }

    @Override
    public String getColumnNameReviewId() {
        return mTables.getColumnNameReviewId();
    }

    @Override
    public DbTable<RowReview> getReviewsTable() {
        return mTables.getReviewsTable();
    }

    @Override
    public DbTable<RowComment> getCommentsTable() {
        return mTables.getCommentsTable();
    }

    @Override
    public DbTable<RowFact> getFactsTable() {
        return mTables.getFactsTable();
    }

    @Override
    public DbTable<RowLocation> getLocationsTable() {
        return mTables.getLocationsTable();
    }

    @Override
    public DbTable<RowImage> getImagesTable() {
        return mTables.getImagesTable();
    }

    @Override
    public DbTable<RowAuthor> getAuthorsTable() {
        return mTables.getAuthorsTable();
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTables.getTagsTable();
    }

    @Override
    public ArrayList<DbTable<? extends DbTableRow>> getTables() {
        return mTables.getTables();
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTables.getTableNames();
    }

    //Private methods
    private void loadTags(DatabaseInstance db) {
        for (RowTag tag : db.getRowsWhere(getTagsTable(), null, null)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagItem(reviewId, tag.getTag());
            }
        }
    }

    @Nullable
    private Review loadReview(RowReview reviewRow, DatabaseInstance db) {
        if (!reviewRow.hasData(mDataValidator)) return null;
        Review review = mReviewLoader.loadReview(reviewRow, this, db);
        setTags(reviewRow.getReviewId().toString(), db);

        return review;
    }

    private void setTags(String id, DatabaseInstance db) {
        ItemTagCollection tags = mTagsManager.getTags(id);
        if (tags.size() == 0) loadTags(db);
    }

    private void addToAuthorsTable(DataAuthor author, DatabaseInstance db) {
        db.insertRow(mRowFactory.newRow(author), getAuthorsTable());
    }

    private void deleteFromAuthorsTable(String userId, DatabaseInstance db) {
        db.deleteRows(RowAuthor.COLUMN_USER_ID, userId, getAuthorsTable());
    }

    private void addToTagsTable(Review review, DatabaseInstance db) {
        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        for (ItemTag tag : tags) {
            db.insertOrReplaceRow(mRowFactory.newRow(tag), getTagsTable());
        }
    }

    private void deleteFromTagsTable(String tag, DatabaseInstance db) {
        db.deleteRows(RowTag.COLUMN_TAG, tag, getTagsTable());
    }

    private void addToReviewsTable(Review review, DatabaseInstance db) {
        db.insertRow(mRowFactory.newRow(review), getReviewsTable());
    }

    private void addCriteriaToReviewsTable(Review review, DatabaseInstance db) {
        for (DataCriterionReview criterion : review.getCriteria()) {
            db.insertRow(mRowFactory.newRow(criterion), getReviewsTable());
        }
    }

    private void deleteFromReviewsTable(String reviewId, DatabaseInstance db) {
        db.deleteRows(getColumnNameReviewId(), reviewId, getReviewsTable());
    }

    private void deleteCriteriaFromReviewsTable(String reviewId, DatabaseInstance db) {
        TableRowList<RowReview> rows = db.getRowsWhere(getReviewsTable(),
                RowReview.COLUMN_PARENT_ID, reviewId);
        for (RowReview row : rows) {
            deleteReviewFromDb(row.getRowId(), db);
        }
    }

    private void deleteFromCommentsTable(String reviewId, DatabaseInstance db) {
        db.deleteRows(getColumnNameReviewId(), reviewId, getCommentsTable());
    }

    private void deleteFromFactsTable(String reviewId, DatabaseInstance db) {
        db.deleteRows(getColumnNameReviewId(), reviewId, getFactsTable());
    }

    private void deleteFromLocationsTable(String reviewId, DatabaseInstance db) {
        db.deleteRows(getColumnNameReviewId(), reviewId, getLocationsTable());
    }

    private void deleteFromImagesTable(String reviewId, DatabaseInstance db) {
        db.deleteRows(getColumnNameReviewId(), reviewId, getImagesTable());
    }

    private void addToCommentsTable(Review review, DatabaseInstance db) {
        int i = 1;
        for (DataComment datum : review.getComments()) {
            db.insertRow(mRowFactory.newRow(datum, i++), getCommentsTable());
        }
    }

    private void addToFactsTable(Review review, DatabaseInstance db) {
        int i = 1;
        for (DataFact datum : review.getFacts()) {
            db.insertRow(mRowFactory.newRow(datum, i++), getFactsTable());
        }
    }

    private void addToLocationsTable(Review review, DatabaseInstance db) {
        int i = 1;
        for (DataLocation datum : review.getLocations()) {
            db.insertRow(mRowFactory.newRow(datum, i++), getLocationsTable());
        }
    }

    private void addToImagesTable(Review review, DatabaseInstance db) {
        int i = 1;
        for (DataImage datum : review.getImages()) {
            db.insertRow(mRowFactory.newRow(datum, i++), getImagesTable());
        }
    }

    private boolean isReviewInDb(Review review, DatabaseInstance db) {
        DbColumnDef reviewIdCol = getReviewsTable().getColumn(getColumnNameReviewId());
        return db.isIdInTable(review.getReviewId().toString(), reviewIdCol, getReviewsTable());
    }
}
