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
import android.util.Log;

import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewId;
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

    public void addReviewToDb(ReviewNode node) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ReviewNode parent = node.getParent();
        if (parent != null && !isReviewInDb(parent, db)) {
            String parentId = parent.getReview().getId().toString();
            throw new IllegalStateException("Please ensure parent review " + parentId + " has " +
                    "been saved first!");
        }

        addReviewToDb(node, db);
        db.close();
    }

    public ReviewerDbRows.ReviewTreesRow getReviewTreeRowFor(ReviewId nodeId, SQLiteDatabase db) {
        String table = ReviewerDbContract.TableReviewTrees.TABLE_NAME;
        String nodeIdCol = ReviewerDbContract.TableReviewTrees.COLUMN_NAME_REVIEW_NODE_ID;

        Cursor cursor = getRowCursor(db, table, nodeIdCol, nodeId.toString());
        if (cursor == null || cursor.getCount() == 0) return null;

        cursor.moveToFirst();
        ReviewerDbRows.ReviewTreesRow row = new ReviewerDbRows.ReviewTreesRow(cursor);
        cursor.close();

        return row;
    }

    private Cursor getRowCursor(SQLiteDatabase db, String table, String pkColumn, String pkValue) {
        String query = SQL.SELECT + SQL.SPACE + SQL.ALL + SQL.SPACE + SQL.FROM + SQL.SPACE + table +
                SQL.WHERE + SQL.SPACE + pkColumn + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{pkValue});
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }

    private void addReviewToDb(ReviewNode node, SQLiteDatabase db) {
        addToReviewsTable(node, db);
        addToReviewsTreeTable(node, db);
        addToCommentsTable(node, db);
        addToFactsTable(node, db);
        addToLocationsTable(node, db);
        addToImagesTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            if (!isReviewInDb(child, db)) addReviewToDb(child, db);
        }
    }

    private void addToReviewsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRows.newRow(node.getReview()),
                ReviewerDbContract.TableComments.get(), db);
    }

    private void addToReviewsTreeTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRows.newRow(node), ReviewerDbContract.TableComments.get(), db);
    }

    private void addToCommentsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : node.getReview().getComments()) {
            insertRow(ReviewerDbRows.newRow(datum, i++), ReviewerDbContract.TableComments.get(),
                    db);
        }
    }

    private void addToFactsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : node.getReview().getFacts()) {
            insertRow(ReviewerDbRows.newRow(datum, i++), ReviewerDbContract.TableFacts.get(), db);
        }
    }

    private void addToLocationsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : node.getReview().getLocations()) {
            insertRow(ReviewerDbRows.newRow(datum, i++), ReviewerDbContract.TableImages.get(), db);
        }
    }

    private void addToImagesTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : node.getReview().getImages()) {
            insertRow(ReviewerDbRows.newRow(datum, i++), ReviewerDbContract.TableImages.get(), db);
        }
    }

    private void insertRow(ReviewerDbRows.TableRow row, ReviewerDbContract.ReviewerDbTable table,
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
        String reviewId = ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID;

        Cursor cursor = getRowCursor(db, table, reviewId, node.getReview().getId().toString());

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }
}
