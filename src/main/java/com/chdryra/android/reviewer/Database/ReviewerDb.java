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
        insertReviewsTable(node, db);
        insertReviewsTreeTable(node, db);
        insertCommentsTable(node, db);
        insertFactsTable(node, db);
        insertLocationsTable(node, db);
        insertImagesTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            if (!isReviewInDb(child, db)) addReviewToDb(child, db);
        }
    }

    private void insertReviewsTable(ReviewNode node, SQLiteDatabase db) {
        ReviewerDbRows.ReviewsRow row = new ReviewerDbRows.ReviewsRow(node.getReview());
        insertRow(row, ReviewerDbContract.TableComments.get(), db);
    }

    private void insertReviewsTreeTable(ReviewNode node, SQLiteDatabase db) {
        ReviewerDbRows.ReviewTreesRow row = new ReviewerDbRows.ReviewTreesRow(node);
        insertRow(row, ReviewerDbContract.TableComments.get(), db);

    }

    private void insertCommentsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment comment : node.getReview().getComments()) {
            ReviewerDbRows.CommentsRow row = new ReviewerDbRows.CommentsRow(comment, i++);
            insertRow(row, ReviewerDbContract.TableComments.get(), db);
        }
    }

    private void insertFactsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact fact : node.getReview().getFacts()) {
            ReviewerDbRows.FactsRow row = new ReviewerDbRows.FactsRow(fact, i++);
            insertRow(row, ReviewerDbContract.TableFacts.get(), db);
        }
    }

    private void insertLocationsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation location : node.getReview().getLocations()) {
            ReviewerDbRows.LocationsRow row = new ReviewerDbRows.LocationsRow(location, i++);
            insertRow(row, ReviewerDbContract.TableImages.get(), db);
        }
    }

    private void insertImagesTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage image : node.getReview().getImages()) {
            ReviewerDbRows.ImagesRow row = new ReviewerDbRows.ImagesRow(image, i++);
            insertRow(row, ReviewerDbContract.TableImages.get(), db);
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
