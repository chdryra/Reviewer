/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdData;
import com.chdryra.android.reviewer.Model.MdDataList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.ReviewUser;
import com.chdryra.android.reviewer.Model.TagsManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDb {
    private static final String                         TAG                   = "ReviewerDb";
    private static final String                         DATABASE_NAME         = "Reviewer.db";
    private static final int                            VERSION_NUMBER        = 1;
    private static final ReviewerDbTable<RowReview>     REVIEWS               =
            ReviewerDbContract.REVIEWS_TABLE;
    private static final ReviewerDbTable<RowReviewNode> TREES                 =
            ReviewerDbContract.TREES_TABLE;
    private static final ReviewerDbTable<RowComment>    COMMENTS              =
            ReviewerDbContract.COMMENTS_TABLE;
    private static final ReviewerDbTable<RowFact>       FACTS                 =
            ReviewerDbContract.FACTS_TABLE;
    private static final ReviewerDbTable<RowLocation>   LOCATIONS             =
            ReviewerDbContract.LOCATIONS_TABLE;
    private static final ReviewerDbTable<RowImage>      IMAGES                =
            ReviewerDbContract.IMAGES_TABLE;
    private static final ReviewerDbTable<RowAuthor>     AUTHORS               =
            ReviewerDbContract.AUTHORS_TABLE;
    private static final ReviewerDbTable<RowTag>        TAGS                  =
            ReviewerDbContract.TAGS_TABLE;
    private static final String                         COLUMN_NAME_REVIEW_ID =
            ReviewerDbContract.NAME_REVIEW_ID;

    private static ReviewerDb       sDatabase;
    private SQLiteOpenHelper mHelper;
    private String           mDatabaseName;

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

    public void addReviewTreeToDb(ReviewNode node) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ReviewNode parent = node.getParent();
        if (parent != null && !isReviewInDb(parent, db)) {
            db.close();
            addReviewTreeToDb(parent);
            return;
        }

        db.beginTransaction();

        addReviewTreeToDb(node, db);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public <T extends ReviewerDbRow.TableRow> T getRowWhere(
            ReviewerDbTable<T> table, DbTableDef.DbColumnDef idCol, String id) {
        return getRowWhere(mHelper.getReadableDatabase(), table, idCol, id);
    }

    public ReviewNode getReviewTreeFromDb(String nodeId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        RowReviewNode rootRow = findRootNode(nodeId, db);
        ReviewTreeNode rootNode = getSubTree(rootRow.getRowId(), db);
        ReviewTree tree = rootNode.createTree();
        db.close();

        return tree;
    }

    public RowReviewNode findRootNode(String nodeId, SQLiteDatabase db) {
        String table = TREES.getName();
        String nodeCol = RowReviewNode.NODE_ID;
        String parentCol = RowReviewNode.PARENT_ID;
        String query = "WITH tree as\n" +
                "(\n" +
                "    SELECT t.* FROM " + table + " AS t WHERE t." + nodeCol + " = ?\n" +
                "\n" +
                "    UNION ALL \n" +
                "\n" +
                "    SELECT t2.* FROM tree JOIN " + table + " AS t2 \n" +
                "on tree." + parentCol + " = t2." + nodeCol + "\n" +
                ") \n" +
                "SELECT * FROM tree WHERE " + parentCol + " is null";

        Log.i(TAG, "Finding root node: " + query);
        Cursor cursor = db.rawQuery(query, new String[]{nodeId});
        RowReviewNode row = new RowReviewNode(cursor);
        cursor.close();
        return row;
    }

    public Review getReviewFromDb(String reviewId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        DbTableDef.DbColumnDef colId = REVIEWS.getColumn(COLUMN_NAME_REVIEW_ID);
        RowReview reviewRow = getRowWhere(db, REVIEWS, colId, reviewId);
        ContentValues values = reviewRow.getContentValues();

        String subject = values.getAsString(RowReview.SUBJECT);
        float rating = values.getAsFloat(RowReview.RATING);
        ReviewId id = ReviewId.fromString(values.getAsString(RowReview.REVIEW_ID));
        Date publishDate = new Date(values.getAsLong(RowReview.PUBLISH_DATE));
        String userId = values.getAsString(RowReview.AUTHOR_ID);
        colId = AUTHORS.getColumn(RowAuthor.USER_ID);
        RowAuthor authorRow = getRowWhere(db, AUTHORS, colId, userId);
        Author author = authorRow.toAuthor();

        MdCommentList comments = getFromDataTable(db, COMMENTS, reviewId, MdCommentList.class);
        MdFactList facts = getFromDataTable(db, FACTS, reviewId, MdFactList.class);
        MdLocationList locations = getFromDataTable(db, LOCATIONS, reviewId, MdLocationList.class);
        MdImageList images = getFromDataTable(db, IMAGES, reviewId, MdImageList.class);

        Review review = new ReviewUser(id, author, publishDate, subject, rating, comments,
                images, facts, locations);

        db.close();

        return review;
    }

    private ReviewTreeNode getSubTree(String nodeId, SQLiteDatabase db) {
        TableRowList<RowReviewNode> nodes = getRowsForId(db, TREES, RowReviewNode.NODE_ID, nodeId);
        if (nodes.size() > 1) {
            throw new IllegalStateException("Can only have 1 node_id = " + nodeId + " in table "
                    + TREES.getName());
        }

        RowReviewNode nodeRow = nodes.getItem(0);
        ContentValues values = nodeRow.getContentValues();
        Boolean isAverage = values.getAsBoolean(RowReviewNode.IS_AVERAGE);
        String reviewId = values.getAsString(RowReviewNode.REVIEW_ID);
        Review review = getReviewFromDb(reviewId);
        ReviewTreeNode rootNode = new ReviewTreeNode(review, isAverage);

        TableRowList<RowReviewNode> children = getRowsForId(db, TREES, RowReviewNode.PARENT_ID,
                nodeId);
        for (RowReviewNode child : children) {
            rootNode.addChild(getSubTree(child.getRowId(), db));
        }

        return rootNode;
    }

    private <T extends ReviewerDbRow.TableRow> T getRowWhere(SQLiteDatabase db,
            ReviewerDbTable<T> table, DbTableDef.DbColumnDef idCol, String id) {
        Cursor cursor = getRowFromTableWhere(db, table.getName(), idCol.getName(), id);

        if (cursor == null || cursor.getCount() == 0) {
            return ReviewerDbRow.emptyRow(table.getRowClass());
        }

        cursor.moveToFirst();
        T row = ReviewerDbRow.newRow(cursor, table.getRowClass());
        cursor.close();

        return row;
    }

    private <T1 extends MdData, T2 extends MdDataList<T1>, T3 extends MdDataRow<T1>> T2
    getFromDataTable(SQLiteDatabase db, ReviewerDbTable<T3> table, String reviewId, Class<T2>
            listClass) {
        TableRowList<T3> rows = getRowsForId(db, table, COLUMN_NAME_REVIEW_ID, reviewId);
        T2 dataList = newMdList(listClass, reviewId);
        for (T3 row : rows) {
            dataList.add(row.toMdData());
        }

        return dataList;
    }

    private <T extends ReviewerDbRow.TableRow> TableRowList<T> getRowsForId(SQLiteDatabase db,
            ReviewerDbTable<T> table, String idCol, String id) {
        Cursor cursor = getFromTableWhere(db, table.getName(), idCol, id);

        TableRowList<T> list = new TableRowList<>(table.getRowClass());
        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null) cursor.close();
            db.close();
            return list;
        }

        while (cursor.moveToNext()) {
            list.add(ReviewerDbRow.newRow(cursor, table.getRowClass()));
        }
        cursor.close();

        return list;
    }

    private Cursor getRowFromTableWhere(SQLiteDatabase db, String table, String pkColumn, String
            pkValue) {
        Cursor cursor = getFromTableWhere(db, table, pkColumn, pkValue);
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }

    private Cursor getFromTableWhere(SQLiteDatabase db, String table, String column, String value) {
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + " " + SQL.WHERE + column + " = ?";
        return db.rawQuery(query, new String[]{value});
    }

    private void addReviewTreeToDb(ReviewNode node, SQLiteDatabase db) {
        if (isReviewInDb(node, db)) return;

        addToReviewsTable(node, db);
        addToReviewTreesTable(node, db);
        addToCommentsTable(node, db);
        addToFactsTable(node, db);
        addToLocationsTable(node, db);
        addToImagesTable(node, db);
        addToAuthorsTable(node, db);
        addToTagsTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            addReviewTreeToDb(child, db);
        }
    }

    private void addToAuthorsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node.getAuthor()), AUTHORS, db);
    }

    private void addToTagsTable(ReviewNode node, SQLiteDatabase db) {
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(node);
        for (TagsManager.ReviewTag tag : tags) {
            insertOrReplaceRow(ReviewerDbRow.newRow(tag), TAGS, db);
        }
    }

    private void addToReviewsTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node.getReview()), REVIEWS, db);
    }

    private void addToReviewTreesTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node), TREES, db);
    }

    private void addToCommentsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : node.getReview().getComments()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), COMMENTS, db);
        }
    }

    private void addToFactsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : node.getReview().getFacts()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), FACTS, db);
        }
    }

    private void addToLocationsTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : node.getReview().getLocations()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), LOCATIONS, db);
        }
    }

    private void addToImagesTable(ReviewNode node, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : node.getReview().getImages()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), IMAGES, db);
        }
    }

    private void insertRow(ReviewerDbRow.TableRow row, ReviewerDbTable table,
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

    private void insertOrReplaceRow(ReviewerDbRow.TableRow row, ReviewerDbTable
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
        DbTableDef.DbColumnDef reviewIdCol = REVIEWS.getColumn(COLUMN_NAME_REVIEW_ID);
        return isIdInTable(node.getReview().getId().toString(), reviewIdCol, REVIEWS, db);
    }

    private boolean isIdInTable(String id, DbTableDef.DbColumnDef idCol, ReviewerDbTable table,
            SQLiteDatabase db) {
        String reviewIdCol = idCol.getName();

        Cursor cursor = getRowFromTableWhere(db, table.getName(), reviewIdCol, id);

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
