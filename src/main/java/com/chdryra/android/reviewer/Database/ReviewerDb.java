/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdData;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.ReviewTag;
import com.chdryra.android.reviewer.Model.TagsModel.ReviewTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDb implements ReviewerDbTables {
    private static final String TAG = "ReviewerDb";

    private final ReviewerDbTables mTables;
    private final SQLiteOpenHelper mHelper;
    private final TagsManager mTagsManager;
    private final ReviewLoader mReviewLoader;
    private final FactoryDbTableRow mRowFactory;
    private final ArrayList<ReviewerDbObserver> mObservers;

    public interface ReviewLoader {
        Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db);
    }

    public ReviewerDb(DbHelper<ReviewerDbContract> dbHelper,
                      ReviewLoader reviewLoader,
                      FactoryDbTableRow rowFactory,
                      TagsManager tagsManager) {
        mHelper = dbHelper;
        mTables = dbHelper.getContract();
        mTagsManager = tagsManager;
        mReviewLoader = reviewLoader;
        mRowFactory = rowFactory;
        mObservers = new ArrayList<>();
    }

    //public methods
    public String getDatabaseName() {
        return mHelper.getDatabaseName();
    }

    public SQLiteOpenHelper getHelper() {
        return mHelper;
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
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

    //API
    public void registerObserver(ReviewerDbObserver observer) {
        mObservers.add(observer);
    }

    public void addReviewToDb(Review review) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        boolean success = addReviewToDb(review, db);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        if (success) notifyOnAddReview(review);
    }

    public Review loadReviewFromDb(String reviewId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.beginTransaction();
        RowReview row = getRowWhere(db, getReviewsTable(), RowReview.COLUMN_REVIEW_ID, reviewId);
        Review review = loadReview(row, db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return review;
    }

    public IdableList<Review> loadReviewsFromDb() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.beginTransaction();
        IdableList<Review> reviews = loadReviewsFromDbWhere(db, RowReview.COLUMN_PARENT_ID, null);
        loadTags(db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return reviews;
    }

    public void deleteReviewFromDb(String reviewId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        boolean success = deleteReviewFromDb(reviewId, db);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        if (success) notifyOnDeleteReview(reviewId);
    }

    public IdableList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, String val) {
        TableRowList<RowReview> reviewsList = getRowsWhere(db, getReviewsTable(), col, val);

        IdableList<Review> reviews = new IdableList<>();
        for (RowReview reviewRow : reviewsList) {
            reviews.add(loadReview(reviewRow, db));
        }

        return reviews;
    }

    public <T extends DbTableRow> T getRowWhere(SQLiteDatabase db,
                                                            DbTable<T> table, String col,
                                                            String val) {
        Cursor cursor = getCursorWhere(db, table.getName(), col, val);

        if (cursor.getCount() == 0) return mRowFactory.emptyRow(table.getRowClass());

        cursor.moveToFirst();
        T row = mRowFactory.newRow(cursor, table.getRowClass());
        cursor.close();

        return row;
    }

    public <T1 extends MdData, T2 extends MdDataList<T1>, T3 extends ReviewDataRow<T1>> T2
    loadFromDataTable(SQLiteDatabase db, DbTable<T3> table, String reviewId, Class<T2> listClass) {
        TableRowList<T3> rows = getRowsWhere(db, table, getColumnNameReviewId(), reviewId);
        T2 dataList = newMdList(listClass, reviewId);
        for (T3 row : rows) {
            dataList.add(row.toMdData());
        }

        return dataList;
    }

    public <T1 extends DbTableRow> ArrayList<T1>
    loadFromDataTable(SQLiteDatabase db, DbTable<T1> table, String reviewId) {
        TableRowList<T1> rows = getRowsWhere(db, table, getColumnNameReviewId(), reviewId);
        ArrayList<T1> results = new ArrayList<>();
        for (T1 row : rows) {
            results.add(row);
        }

        return results;
    }

    //Private methods
    private void loadTags(SQLiteDatabase db) {
        for (RowTag tag : getRowsWhere(db, getTagsTable(), null, null)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagReview(ReviewId.fromString(reviewId), tag.getTag());
            }
        }
    }

    private void notifyOnAddReview(Review review) {
        for (ReviewerDbObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void notifyOnDeleteReview(String reviewId) {
        for (ReviewerDbObserver observer : mObservers) {
            observer.onReviewDeleted(reviewId);
        }
    }

    private Review loadReview(RowReview reviewRow, SQLiteDatabase db) {
        if (!reviewRow.hasData()) return null;
        Review review = mReviewLoader.loadReview(reviewRow, this, db);
        setTags(review.getId(), db);

        return review;
    }

    private void setTags(ReviewId id, SQLiteDatabase db) {
        ReviewTagCollection tags = mTagsManager.getTags(id);
        if (tags.size() == 0) loadTags(db);
    }

    private <T extends DbTableRow> TableRowList<T> getRowsWhere(SQLiteDatabase db,
                                                                            DbTable<T>
                                                                                    table, String
                                                                                    col, String
                                                                                    val) {
        Cursor cursor = getFromTableWhere(db, table.getName(), col, val);

        TableRowList<T> list = new TableRowList<>();
        while (cursor.moveToNext()) {
            list.add(mRowFactory.newRow(cursor, table.getRowClass()));
        }
        cursor.close();

        return list;
    }

    private Cursor getCursorWhere(SQLiteDatabase db, String table, String pkColumn, String
            pkValue) {
        Cursor cursor = getFromTableWhere(db, table, pkColumn, pkValue);
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }

    private Cursor getFromTableWhere(SQLiteDatabase db, String table, String column, String value) {
        boolean isNull = value == null;
        String val = isNull ? SQL.SPACE + SQL.IS_NULL : SQL.SPACE + SQL.BIND_STRING;
        String whereClause = column != null ? " " + SQL.WHERE + column + val : "";
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + whereClause;
        String[] args = isNull ? null : new String[]{value};
        return db.rawQuery(query, args);
    }

    private boolean addReviewToDb(Review review, SQLiteDatabase db) {
        if (isReviewInDb(review, db)) return false;

        Author author = review.getAuthor();
        String userId = author.getUserId().toString();
        DbTable<RowAuthor> authorsTable = getAuthorsTable();
        if (!isIdInTable(userId, authorsTable.getColumn(RowAuthor.COLUMN_USER_ID), authorsTable, db)) {
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

    private boolean deleteReviewFromDb(String reviewId, SQLiteDatabase db) {
        RowReview row = getRowWhere(db, getReviewsTable(), getColumnNameReviewId(), reviewId);
        if (!row.hasData()) return false;

        String userId = row.getContentValues().getAsString(RowReview.COLUMN_AUTHOR_ID);

        deleteFromImagesTable(reviewId, db);
        deleteFromLocationsTable(reviewId, db);
        deleteFromFactsTable(reviewId, db);
        deleteFromCommentsTable(reviewId, db);
        deleteCriteriaFromReviewsTable(reviewId, db);
        deleteFromReviewsTable(reviewId, db);

        TableRowList<RowReview> authored = getRowsWhere(db, getReviewsTable(),
                RowReview.COLUMN_AUTHOR_ID, userId);
        if (authored.size() == 0) deleteFromAuthorsTable(userId, db);

        ReviewTagCollection tags = mTagsManager.getTags(ReviewId.fromString(reviewId));
        for (ReviewTag tag : tags) {
            if (mTagsManager.untagReview(ReviewId.fromString(reviewId), tag)) {
                deleteFromTagsTable(tag.getTag(), db);
            }
        }

        return true;
    }

    private void addToAuthorsTable(Author author, SQLiteDatabase db) {
        insertRow(mRowFactory.newRow(author), getAuthorsTable(), db);
    }

    private void deleteFromAuthorsTable(String userId, SQLiteDatabase db) {
        deleteRows(RowAuthor.COLUMN_USER_ID, userId, getAuthorsTable(), db);
    }

    private void addToTagsTable(Review review, SQLiteDatabase db) {
        ReviewTagCollection tags = mTagsManager.getTags(review.getId());
        for (ReviewTag tag : tags) {
            insertOrReplaceRow(mRowFactory.newRow(tag), getTagsTable(), db);
        }
    }

    private void deleteFromTagsTable(String tag, SQLiteDatabase db) {
        deleteRows(RowTag.COLUMN_TAG, tag, getTagsTable(), db);
    }

    private void addToReviewsTable(Review review, SQLiteDatabase db) {
        insertRow(mRowFactory.newRow(review), getReviewsTable(), db);
    }

    private void addCriteriaToReviewsTable(Review review, SQLiteDatabase db) {
        for (MdCriterionList.MdCriterion criterion : review.getCriteria()) {
            insertRow(mRowFactory.newRow(criterion), getReviewsTable(), db);
        }
    }

    private void deleteFromReviewsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(getColumnNameReviewId(), reviewId, getReviewsTable(), db);
    }

    private void deleteCriteriaFromReviewsTable(String reviewId, SQLiteDatabase db) {
        TableRowList<RowReview> rows = getRowsWhere(db, getReviewsTable(),
                RowReview.COLUMN_PARENT_ID, reviewId);
        for (RowReview row : rows) {
            deleteReviewFromDb(row.getRowId(), db);
        }
    }

    private void deleteFromCommentsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(getColumnNameReviewId(), reviewId, getCommentsTable(), db);
    }

    private void deleteFromFactsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(getColumnNameReviewId(), reviewId, getFactsTable(), db);
    }

    private void deleteFromLocationsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(getColumnNameReviewId(), reviewId, getLocationsTable(), db);
    }

    private void deleteFromImagesTable(String reviewId, SQLiteDatabase db) {
        deleteRows(getColumnNameReviewId(), reviewId, getImagesTable(), db);
    }

    private void addToCommentsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : review.getComments()) {
            insertRow(mRowFactory.newRow(datum, i++), getCommentsTable(), db);
        }
    }

    private void addToFactsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : review.getFacts()) {
            insertRow(mRowFactory.newRow(datum, i++), getFactsTable(), db);
        }
    }

    private void addToLocationsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : review.getLocations()) {
            insertRow(mRowFactory.newRow(datum, i++), getLocationsTable(), db);
        }
    }

    private void addToImagesTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : review.getImages()) {
            insertRow(mRowFactory.newRow(datum, i++), getImagesTable(), db);
        }
    }

    private void insertRow(DbTableRow row, DbTable table, SQLiteDatabase db) {
        DbTable.DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        String tableName = table.getName();
        if (isIdInTable(id, idCol, table, db)) {
            Log.i(TAG, "Id " + id + " already in table " + table.getName() + ". Ignoring insert");
            return;
        }

        String message = id + " into " + tableName + " table ";
        try {
            long rowId = db.insertOrThrow(tableName, null, row.getContentValues());
            Log.i(TAG, "Inserted " + message + " at row " + String.valueOf(rowId));
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    private void deleteRows(String col, String val, DbTable table, SQLiteDatabase db) {
        String tableName = table.getName();

        String message = val + " from " + tableName + " table ";
        String whereClause = col + SQL.BIND_STRING;
        try {
            long rowId = db.delete(tableName, whereClause, new String[]{val});
            Log.i(TAG, "Deleted " + message + " at row " + String.valueOf(rowId));
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't delete " + message, e);
        }
    }

    private void insertOrReplaceRow(DbTableRow row, DbTable
            table, SQLiteDatabase db) {
        DbTable.DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        String tableName = table.getName();
        if (isIdInTable(id, idCol, table, db)) {
            String message = id + " in " + tableName + " table ";
            try {
                long rowId = db.replaceOrThrow(tableName, null, row.getContentValues());
                Log.i(TAG, "Replaced " + message + " at row " + String.valueOf(rowId));
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't replace " + message, e);
            }
        } else {
            insertRow(row, table, db);
        }
    }

    private boolean isReviewInDb(Review review, SQLiteDatabase db) {
        DbTable.DbColumnDef reviewIdCol = getReviewsTable().getColumn(getColumnNameReviewId());
        return isIdInTable(review.getId().toString(), reviewIdCol, getReviewsTable(), db);
    }

    private boolean isIdInTable(String id, DbTable.DbColumnDef idCol, DbTable table,
                                SQLiteDatabase db) {
        String pkCol = idCol.getName();
        Cursor cursor = getCursorWhere(db, table.getName(), pkCol, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }

    private <T1 extends MdData, T2 extends MdDataList<T1>> T2 newMdList(Class<T2> listClass,
                                                                        String reviewId) {
        ReviewId id = ReviewId.fromString(reviewId);
        try {
            Constructor c = listClass.getConstructor(ReviewId.class);
            return listClass.cast(c.newInstance(id));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find ReviewId constructor for " + listClass
                    .getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + listClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + listClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Couldn't invoke class " + listClass.getName(), e);
        }
    }

}
