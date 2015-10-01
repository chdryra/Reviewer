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

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdData;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewUser;
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
public class ReviewerDb {
    private static final String                         TAG                   = "ReviewerDb";
    private static final String                         DATABASE_NAME         = "Reviewer.db";
    private static final int                            VERSION_NUMBER        = 1;
    private static final ReviewerDbTable<RowReview>     REVIEWS               =
            ReviewerDbContract.REVIEWS_TABLE;
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

    private SQLiteOpenHelper mHelper;
    private String           mDatabaseName;
    private TagsManager mTagsManager;

    private ReviewerDb(Context context, String databaseName, TagsManager tagsManager) {
        mDatabaseName = databaseName;
        DbContract contract = ReviewerDbContract.getContract();
        mHelper = new DbHelper(context, new DbManager(contract), mDatabaseName, VERSION_NUMBER);
        mTagsManager = tagsManager;
    }

    //API
    public static ReviewerDb getDatabase(Context context, TagsManager tagsManager) {
        return new ReviewerDb(context, DATABASE_NAME, tagsManager);
    }

    public static ReviewerDb getTestDatabase(Context context, TagsManager tagsManager) {
        return new ReviewerDb(context, "Test" + DATABASE_NAME, tagsManager);
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public SQLiteOpenHelper getHelper() {
        return mHelper;
    }

    public void addReviewToDb(Review review) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        addReviewToDb(review, db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    public Review loadReviewFromDb(String reviewId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.beginTransaction();
        RowReview row = getRowWhere(db, REVIEWS, RowReview.REVIEW_ID, reviewId);
        Review review = buildReview(row, db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return review;
    }

    public IdableList<Review> loadReviewsFromDb() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.beginTransaction();
        IdableList<Review> reviews = loadReviewsFromDbWhere(db, RowReview.PARENT_ID, null);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return reviews;
    }

    private void loadTags(SQLiteDatabase db) {
        for (RowTag tag : getRowsWhere(db, TAGS, null, null)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagReview(ReviewId.fromString(reviewId), tag.getTag());
            }
        }
    }

    public void deleteReviewFromDb(String reviewId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();
        deleteReviewFromDb(reviewId, db);
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    //Private methods
    private IdableList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, String val) {
        TableRowList<RowReview> reviewsList = getRowsWhere(db, REVIEWS, col, val);

        IdableList<Review> reviews = new IdableList<>();
        for (RowReview reviewRow : reviewsList) {
            reviews.add(buildReview(reviewRow, db));
        }

        return reviews;
    }

    private Review buildReview(RowReview reviewRow, SQLiteDatabase db) {
        ContentValues values = reviewRow.getContentValues();

        String reviewId = values.getAsString(RowReview.REVIEW_ID);
        ReviewId id = ReviewId.fromString(values.getAsString(RowReview.REVIEW_ID));
        String userId = values.getAsString(RowReview.AUTHOR_ID);
        Author author = getRowWhere(db, AUTHORS, RowAuthor.USER_ID, userId).toAuthor();
        PublishDate publishDate = PublishDate.then(values.getAsLong(RowReview.PUBLISH_DATE));
        String subject = values.getAsString(RowReview.SUBJECT);
        float rating = values.getAsFloat(RowReview.RATING);
        MdCommentList comments = loadFromDataTable(db, COMMENTS, reviewId, MdCommentList.class);
        MdFactList facts = loadFromDataTable(db, FACTS, reviewId, MdFactList.class);
        MdLocationList locations = loadFromDataTable(db, LOCATIONS, reviewId, MdLocationList.class);
        MdImageList images = loadFromDataTable(db, IMAGES, reviewId, MdImageList.class);
        IdableList<Review> critList = loadReviewsFromDbWhere(db, RowReview.PARENT_ID, reviewId);
        boolean isAverage = values.getAsBoolean(RowReview.IS_AVERAGE);

        ReviewUser review = new ReviewUser(id, author, publishDate, subject, rating, comments,
                images, facts, locations, critList, isAverage);

        setTags(id, db);

        return review;
    }

    private void setTags(ReviewId id, SQLiteDatabase db) {
        TagsManager.ReviewTagCollection tags = mTagsManager.getTags(id);
        if (tags.size() == 0) loadTags(db);
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
    loadFromDataTable(SQLiteDatabase db, ReviewerDbTable<T3> table, String reviewId, Class<T2>
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

    private void addReviewToDb(Review review, SQLiteDatabase db) {
        if (isReviewInDb(review, db)) return;

        Author author = review.getAuthor();
        String userId = author.getUserId().toString();
        if (!isIdInTable(userId, AUTHORS.getColumn(RowAuthor.USER_ID), AUTHORS, db)) {
            addToAuthorsTable(author, db);
        }

        addToReviewsTable(review, db);
        addCriteriaToReviewsTable(review, db);
        addToCommentsTable(review, db);
        addToFactsTable(review, db);
        addToLocationsTable(review, db);
        addToImagesTable(review, db);
        addToTagsTable(review, db);
    }

    private void deleteReviewFromDb(String reviewId, SQLiteDatabase db) {
        RowReview row = getRowWhere(db, REVIEWS, COLUMN_NAME_REVIEW_ID, reviewId);
        String userId = row.getContentValues().getAsString(RowReview.AUTHOR_ID);

        deleteFromImagesTable(reviewId, db);
        deleteFromLocationsTable(reviewId, db);
        deleteFromFactsTable(reviewId, db);
        deleteFromCommentsTable(reviewId, db);
        deleteCriteriaFromReviewsTable(reviewId, db);
        deleteFromReviewsTable(reviewId, db);

        TableRowList<RowReview> authored = getRowsWhere(db, REVIEWS, RowReview.AUTHOR_ID, userId);
        if (authored.size() == 0) deleteFromAuthorsTable(userId, db);

        TagsManager.ReviewTagCollection tags = mTagsManager.getTags(ReviewId.fromString(reviewId));
        for (TagsManager.ReviewTag tag : tags) {
            if (mTagsManager.untagReview(ReviewId.fromString(reviewId), tag)) {
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

    private void addToTagsTable(Review review, SQLiteDatabase db) {
        TagsManager.ReviewTagCollection tags = mTagsManager.getTags(review.getId());
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

    private void addCriteriaToReviewsTable(Review review, SQLiteDatabase db) {
        for (MdCriterionList.MdCriterion criterion : review.getCriteria()) {
            insertRow(ReviewerDbRow.newRow(criterion), REVIEWS, db);
        }
    }

    private void deleteFromReviewsTable(String reviewId, SQLiteDatabase db) {
        deleteRows(COLUMN_NAME_REVIEW_ID, reviewId, REVIEWS, db);
    }

    private void deleteCriteriaFromReviewsTable(String reviewId, SQLiteDatabase db) {
        TableRowList<RowReview> rows = getRowsWhere(db, REVIEWS, RowReview.PARENT_ID, reviewId);
        for (RowReview row : rows) {
            deleteReviewFromDb(row.getRowId(), db);
        }
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

    private boolean isReviewInDb(Review review, SQLiteDatabase db) {
        DbTableDef.DbColumnDef reviewIdCol = REVIEWS.getColumn(COLUMN_NAME_REVIEW_ID);
        return isIdInTable(review.getId().toString(), reviewIdCol, REVIEWS, db);
    }

    private boolean isIdInTable(String id, DbTableDef.DbColumnDef idCol, ReviewerDbTable table,
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
