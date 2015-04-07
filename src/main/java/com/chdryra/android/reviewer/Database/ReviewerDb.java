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

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDb {
    private static final String             TAG            = "ReviewerDb";
    private static final String             DATABASE_NAME  = "Reviewer.db";
    private static final int                VERSION_NUMBER = 1;
    private static final ReviewerDbContract CONTRACT       = ReviewerDbContract.getContract();

    private static ReviewerDb       sDatabase;
    private        ReviewerDbHelper mHelper;
    private String mDatabaseName;

    private ReviewerDb(Context context, String databaseName) {
        mHelper = new ReviewerDbHelper(context, this);
        mDatabaseName = databaseName;
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

    public int getDatabaseVersion() {
        return VERSION_NUMBER;
    }

    public DbContract getContract() {
        return CONTRACT;
    }

    public ReviewerDbHelper getHelper() {
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

    public <T extends ReviewerDbRow.TableRow> ReviewerDbRow.TableRow getRowFor
            (ReviewerDbContract.ReviewerDbTable table, SQLiteTableDefinition.SQLiteColumn idCol,
                    String id, Class<T> rowClass) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = getRowCursor(db, table.getName(), idCol.getName(), id);

        if (cursor == null || cursor.getCount() == 0) return ReviewerDbRow.emptyRow(rowClass);

        cursor.moveToFirst();
        ReviewerDbRow.TableRow row = ReviewerDbRow.newRow(cursor, rowClass);
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

        for (ReviewNode child : node.getChildren()) {
            if (!isReviewInDb(child, db)) addReviewNodeToDb(child, db);
        }
    }

    private void addToReviewsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node.getReview()),
                ReviewerDbContract.TableReviews.get(), db);
    }

    private void addToReviewsTreeTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node), ReviewerDbContract.TableReviewTrees.get(), db);
    }

    private void addToCommentsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : node.getReview().getComments()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.TableComments.get(),
                    db);
        }
    }

    private void addToFactsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : node.getReview().getFacts()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.TableFacts.get(), db);
        }
    }

    private void addToLocationsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : node.getReview().getLocations()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.TableLocations.get(),
                    db);
        }
    }

    private void addToImagesTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : node.getReview().getImages()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), ReviewerDbContract.TableImages.get(), db);
        }
    }

    private void insertRow(ReviewerDbRow.TableRow row, ReviewerDbContract.ReviewerDbTable table,
            SQLiteDatabase db) {
        String tableName = table.getName();
        String message = row.getRowId() + " into " + tableName + " table ";
        try {
            long rowId = db.insertOrThrow(tableName, null, row.getContentValues());
            Log.i(TAG, "Inserted " + message + " at row " + String.valueOf(rowId));
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    private boolean isReviewInDb(ReviewNode node, SQLiteDatabase db) {
        String table = ReviewerDbContract.TableReviews.TABLE_NAME;
        String reviewIdCol = ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID;
        String id = node.getReview().getId().toString();

        Cursor cursor = getRowCursor(db, table, reviewIdCol, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }

    public static class ReviewerDbHelper extends SQLiteOpenHelper {
        private DbCreator mDbCreator;

        private ReviewerDbHelper(Context context, ReviewerDb db) {
            super(context, db.getDatabaseName(), null, db.getDatabaseVersion());
            mDbCreator = new DbCreator(db.getContract());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDbCreator.createDatabase(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            mDbCreator.upgradeDatabase(db, oldVersion, newVersion);
        }
    }
}
