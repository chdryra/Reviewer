/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDb {
    private static final String             TAG            = "ReviewerDb";
    private static final String             DATABASE_NAME  = "Reviewer.db";
    private static final int                VERSION_NUMBER = 1;

    private static ReviewerDb       sDatabase;
    private SQLiteOpenHelper mHelper;
    private String mDatabaseName;

    private ReviewerDb(Context context, String databaseName) {
        mDatabaseName = databaseName;
        DbContract contract = ReviewerDbContract.getContract();
        mHelper = new DbHelper(context, new DbManager(contract), mDatabaseName, VERSION_NUMBER);
    }

    public static ReviewerDb getDatabase(Context context) {
        if (sDatabase == null) sDatabase = new ReviewerDb(context, DATABASE_NAME);
        return sDatabase;
    }

    public static ReviewerDb getTestDatabase(Context context) {
        return new ReviewerDb(context, "Test" + DATABASE_NAME);
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public SQLiteOpenHelper getHelper() {
        return mHelper;
    }

    public void addReviewNodeToDb(ReviewNode node) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ReviewNode parent = node.getParent();
        if (parent != null && !isReviewInDb(parent, db)) {
            addReviewNodeToDb(parent);
            db.close();
            return;
        }

        db.beginTransaction();

        addReviewNodeToDb(node, db);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public <T extends ReviewerDbRow.TableRow> T getRowFor
            (ReviewerDbContract.ReviewerDbTable table, DbTableDef.DbColumnDef idCol,
                    String id, Class<T> rowClass) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = getRowCursor(db, table.getName(), idCol.getName(), id);

        if (cursor == null || cursor.getCount() == 0) return ReviewerDbRow.emptyRow(rowClass);

        cursor.moveToFirst();
        T row = ReviewerDbRow.newRow(cursor, rowClass);
        cursor.close();

        return row;
    }

    private Cursor getRowCursor(SQLiteDatabase db, String table, String pkColumn, String pkValue) {
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + " " + SQL.WHERE + pkColumn + " =" +
                " ?";
        Cursor cursor = db.rawQuery(query, new String[]{pkValue});
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }

    private void addReviewNodeToDb(ReviewNode node, SQLiteDatabase db) {
        if (isReviewInDb(node, db)) return;

        addToReviewsTable(node, db);
        addToReviewsTreeTable(node, db);
        addToCommentsTable(node, db);
        addToFactsTable(node, db);
        addToLocationsTable(node, db);
        addToImagesTable(node, db);
        addToAuthorsTable(node, db);
        addToTagsTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            if (!isReviewInDb(child, db)) addReviewNodeToDb(child, db);
        }
    }

    private void addToAuthorsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node.getAuthor()), ReviewerDbContract.AUTHORS_TABLE, db);
    }

    private void addToTagsTable(ReviewNode node, SQLiteDatabase db) {
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(node);
        for (TagsManager.ReviewTag tag : tags) {
            insertOrReplaceRow(ReviewerDbRow.newRow(tag), ReviewerDbContract.TAGS_TABLE, db);
        }
    }

    private void addToReviewsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node.getReview()), ReviewerDbContract.REVIEWS_TABLE, db);
    }

    private void addToReviewsTreeTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node), ReviewerDbContract.TREES_TABLE, db);
    }

    private void addToCommentsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : node.getReview().getComments()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.COMMENTS_TABLE, db);
        }
    }

    private void addToFactsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : node.getReview().getFacts()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.FACTS_TABLE, db);
        }
    }

    private void addToLocationsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : node.getReview().getLocations()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.LOCATIONS_TABLE, db);
        }
    }

    private void addToImagesTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : node.getReview().getImages()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.IMAGES_TABLE, db);
        }
    }

    private void insertRow(ReviewerDbRow.TableRow row, ReviewerDbContract.ReviewerDbTable table,
            SQLiteDatabase db) {
        DbTableDef.DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
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

    private void insertOrReplaceRow(ReviewerDbRow.TableRow row, ReviewerDbContract.ReviewerDbTable
            table, SQLiteDatabase db) {
        DbTableDef.DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
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

    private boolean isReviewInDb(ReviewNode node, SQLiteDatabase db) {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.REVIEWS_TABLE;
        DbTableDef.DbColumnDef idCol = table.getColumn(ReviewerDbContract.TableReviews
                .COLUMN_NAME_REVIEW_ID);
        String id = node.getReview().getId().toString();

        return isIdInTable(id, idCol, table, db);
    }

    private boolean isIdInTable(String id, DbTableDef.DbColumnDef idCol, ReviewerDbContract
            .ReviewerDbTable table, SQLiteDatabase db) {
        String reviewIdCol = idCol.getName();

        Cursor cursor = getRowCursor(db, table.getName(), reviewIdCol, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }
}
