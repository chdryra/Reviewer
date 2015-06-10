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
import com.chdryra.android.reviewer.Model.PublishDate;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.ReviewUser;
import com.chdryra.android.reviewer.Model.TagsManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

    //API
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

        db.beginTransaction();
        addReviewTreeToDb(node, db, false);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    public ReviewIdableList<ReviewNode> getReviewTreesFromDb() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.beginTransaction();
        ReviewIdableList<ReviewNode> trees = getReviewTreesFromDb(db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return trees;
    }

    public void loadTags() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        for (RowTag tag : getRowsWhere(db, TAGS, null, null)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                TagsManager.tag(ReviewId.fromString(reviewId), tag.getTag());
            }
        }
        db.close();
    }

    public void deleteReviewTreeFromDb(String nodeId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        deleteReviewTreeFromDb(nodeId, db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    //Private methods
    private ReviewIdableList<ReviewNode> getReviewTreesFromDb(SQLiteDatabase db) {
        TableRowList<RowReviewNode> nodes = getRowsWhere(db, TREES, RowReviewNode.PARENT_ID, null);

        ReviewIdableList<ReviewNode> trees = new ReviewIdableList<>();
        for (RowReviewNode node : nodes) {
            ReviewTreeNode tree = getSubTree(node.getRowId(), db);
            trees.add(tree.createTree());
        }

        return trees;
    }

    private void deleteReviewTreeFromDb(String nodeId, SQLiteDatabase db) {
        deleteSubTree(getRootNodeRow(nodeId, db).getNodeId(), db);
    }

    private RowReviewNode getRootNodeRow(String nodeId, SQLiteDatabase db) {
        RowReviewNode row = getRowWhere(db, TREES, RowReviewNode.NODE_ID, nodeId);
        String parentId = row.getParentId();
        if (parentId != null) return getRootNodeRow(parentId, db);

        return row;
    }

    private Review getReview(String reviewId, SQLiteDatabase db) {
        RowReview reviewRow = getRowWhere(db, REVIEWS, COLUMN_NAME_REVIEW_ID, reviewId);
        if (!reviewRow.hasData()) {
            throw new IllegalArgumentException("ReviewId " + reviewId + " not found!");
        }


        ContentValues values = reviewRow.getContentValues();
        String subject = values.getAsString(RowReview.SUBJECT);
        float rating = values.getAsFloat(RowReview.RATING);
        ReviewId id = ReviewId.fromString(values.getAsString(RowReview.REVIEW_ID));
        PublishDate publishDate = PublishDate.then(values.getAsLong(RowReview.PUBLISH_DATE));
        String userId = values.getAsString(RowReview.AUTHOR_ID);
        RowAuthor authorRow = getRowWhere(db, AUTHORS, RowAuthor.USER_ID, userId);
        Author author = authorRow.toAuthor();

        MdCommentList comments = getFromDataTable(db, COMMENTS, reviewId, MdCommentList.class);
        MdFactList facts = getFromDataTable(db, FACTS, reviewId, MdFactList.class);
        MdLocationList locations = getFromDataTable(db, LOCATIONS, reviewId, MdLocationList.class);
        MdImageList images = getFromDataTable(db, IMAGES, reviewId, MdImageList.class);

        return new ReviewUser(id, author, publishDate, subject, rating, comments,
                images, facts, locations);
    }

    private ReviewTreeNode getSubTree(String nodeId, SQLiteDatabase db) {
        RowReviewNode nodeRow = getRowWhere(db, TREES, RowReviewNode.NODE_ID, nodeId);
        if (!nodeRow.hasData()) {
            throw new IllegalArgumentException("NodeId " + nodeId + " not found!");
        }

        ContentValues values = nodeRow.getContentValues();
        Boolean isAverage = values.getAsBoolean(RowReviewNode.IS_AVERAGE);
        String reviewId = values.getAsString(RowReviewNode.REVIEW_ID);
        Review review = getReview(reviewId, db);
        ReviewTreeNode rootNode = new ReviewTreeNode(review, isAverage, ReviewId.fromString
                (nodeId));

        TableRowList<RowReviewNode> children = getRowsWhere(db, TREES, RowReviewNode.PARENT_ID,
                nodeId);
        for (RowReviewNode child : children) {
            rootNode.addChild(getSubTree(child.getRowId(), db));
        }

        return rootNode;
    }

    private <T extends ReviewerDbRow.TableRow> T getRowWhere(SQLiteDatabase db,
            ReviewerDbTable<T> table, String col, String val) {
        Cursor cursor = getCursorWhere(db, table.getName(), col, val);

        if (cursor.getCount() == 0) return ReviewerDbRow.emptyRow(table.getRowClass());

        cursor.moveToFirst();
        T row = ReviewerDbRow.newRow(cursor, table.getRowClass());
        cursor.close();

        return row;
    }

    private <T1 extends MdData, T2 extends MdDataList<T1>, T3 extends MdDataRow<T1>> T2
    getFromDataTable(SQLiteDatabase db, ReviewerDbTable<T3> table, String reviewId, Class<T2>
            listClass) {
        TableRowList<T3> rows = getRowsWhere(db, table, COLUMN_NAME_REVIEW_ID, reviewId);
        T2 dataList = newMdList(listClass, reviewId);
        for (T3 row : rows) {
            dataList.add(row.toMdData());
        }

        return dataList;
    }

    private <T extends ReviewerDbRow.TableRow> TableRowList<T> getRowsWhere(SQLiteDatabase db,
            ReviewerDbTable<T> table, String col, String val) {
        Cursor cursor = getFromTableWhere(db, table.getName(), col, val);

        TableRowList<T> list = new TableRowList<>();
        while (cursor.moveToNext()) {
            list.add(ReviewerDbRow.newRow(cursor, table.getRowClass()));
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

    private void addReviewTreeToDb(ReviewNode node, SQLiteDatabase db, boolean ignoreParent) {
        ReviewNode parent = node.getParent();
        if (parent != null && !ignoreParent && !isReviewTreeInDb(parent, db)) {
            addReviewTreeToDb(parent, db, false);
            return;
        }

        addToReviewTreesTable(node, db);
        addReviewToDb(node.getReview(), db);
        addToAuthorsTable(node.getAuthor(), db);
        addToTagsTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            addReviewTreeToDb(child, db, true);
        }
    }

    private void deleteSubTree(String nodeId, SQLiteDatabase db) {
        ReviewId id = ReviewId.fromString(nodeId);
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(id);

        RowReviewNode nodeRow = getRowWhere(db, TREES, RowReviewNode.NODE_ID, nodeId);
        if (!nodeRow.hasData()) {
            throw new IllegalArgumentException("NodeId " + nodeId + " not found!");
        }

        deleteRows(RowReviewNode.NODE_ID, nodeId, TREES, db);

        for (TagsManager.ReviewTag tag : tags) {
            if (TagsManager.untag(id, tag)) {
                deleteFromTagsTable(tag.get(), db);
            }
        }

        String reviewId = nodeRow.getReviewId();
        TableRowList<RowReviewNode> nodes = getRowsWhere(db, TREES, RowReviewNode.REVIEW_ID,
                reviewId);
        if (nodes.size() == 0) deleteReviewFromDb(reviewId, db);

        TableRowList<RowReviewNode> children = getRowsWhere(db, TREES, RowReviewNode.PARENT_ID,
                nodeId);
        for (RowReviewNode child : children) {
            deleteSubTree(child.getRowId(), db);
        }
    }

    private void addReviewToDb(Review review, SQLiteDatabase db) {
        if (isReviewInDb(review, db)) return;

        addToReviewsTable(review, db);
        addToCommentsTable(review, db);
        addToFactsTable(review, db);
        addToLocationsTable(review, db);
        addToImagesTable(review, db);
    }

    private void deleteReviewFromDb(String reviewId, SQLiteDatabase db) {
        Review review = getReview(reviewId, db);
        Author author = review.getAuthor();
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(review.getId());

        deleteFromImagesTable(reviewId, db);
        deleteFromLocationsTable(reviewId, db);
        deleteFromFactsTable(reviewId, db);
        deleteFromCommentsTable(reviewId, db);
        deleteFromReviewsTable(reviewId, db);

        String userId = author.getUserId().toString();
        TableRowList<RowReview> authored = getRowsWhere(db, REVIEWS, RowReview.AUTHOR_ID, userId);
        if (authored.size() == 0) deleteFromAuthorsTable(userId, db);
        for (TagsManager.ReviewTag tag : tags) {
            if (TagsManager.untag(ReviewId.fromString(reviewId), tag)) {
                deleteFromTagsTable(tag.get(), db);
            }
        }
    }

    private void addToAuthorsTable(Author author, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(author), AUTHORS, db);
    }

    private void deleteFromAuthorsTable(String userId, SQLiteDatabase db) {
        deleteRows(RowAuthor.USER_ID, userId, AUTHORS, db);
    }

    private void addToTagsTable(ReviewNode node, SQLiteDatabase db) {
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(node.getId());
        for (TagsManager.ReviewTag tag : tags) {
            insertOrReplaceRow(ReviewerDbRow.newRow(tag), TAGS, db);
        }
    }

    private void deleteFromTagsTable(String tag, SQLiteDatabase db) {
        deleteRows(RowTag.TAG, tag, TAGS, db);
    }

    private void addToReviewsTable(Review review, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(review), REVIEWS, db);
    }

    private void addToReviewTreesTable(ReviewNode node, SQLiteDatabase db) {
        insertRow(ReviewerDbRow.newRow(node), TREES, db);
    }

    private void deleteFromReviewsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, REVIEWS, db);
    }

    private void deleteFromCommentsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, COMMENTS, db);
    }

    private void deleteFromFactsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, FACTS, db);
    }

    private void deleteFromLocationsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, LOCATIONS, db);
    }

    private void deleteFromImagesTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, IMAGES, db);
    }

    private void addToCommentsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdCommentList.MdComment datum : review.getComments()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), COMMENTS, db);
        }
    }

    private void addToFactsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdFactList.MdFact datum : review.getFacts()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), FACTS, db);
        }
    }

    private void addToLocationsTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdLocationList.MdLocation datum : review.getLocations()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), LOCATIONS, db);
        }
    }

    private void addToImagesTable(Review review, SQLiteDatabase db) {
        int i = 1;
        for (MdImageList.MdImage datum : review.getImages()) {
            insertRow(ReviewerDbRow.newRow(datum, i++), IMAGES, db);
        }
    }

    private void insertRow(ReviewerDbRow.TableRow row, ReviewerDbTable table, SQLiteDatabase db) {
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

    private void deleteRows(String col, String val, ReviewerDbTable table, SQLiteDatabase db) {
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

    private boolean isReviewTreeInDb(ReviewNode node, SQLiteDatabase db) {
        DbTableDef.DbColumnDef nodeIdCol = TREES.getColumn(RowReviewNode.NODE_ID);
        return isIdInTable(node.getId().toString(), nodeIdCol, TREES, db);
    }

    private boolean isReviewInDb(Review review, SQLiteDatabase db) {
        DbTableDef.DbColumnDef reviewIdCol = REVIEWS.getColumn(COLUMN_NAME_REVIEW_ID);
        return isIdInTable(review.getId().toString(), reviewIdCol, REVIEWS, db);
    }

    private boolean isIdInTable(String id, DbTableDef.DbColumnDef idCol, ReviewerDbTable table,
            SQLiteDatabase db) {
        String reviewIdCol = idCol.getName();

        Cursor cursor = getCursorWhere(db, table.getName(), reviewIdCol, id);

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
