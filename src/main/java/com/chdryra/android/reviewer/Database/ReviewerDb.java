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
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDb {
    private static final String             TAG       = "ReviewerDb";
    private static final String             SEPARATOR = ":";
    private static final ReviewerDbContract CONTRACT  = ReviewerDbContract.getContract();
    private static ReviewerDb       sDatabase;
    private        ReviewerDbHelper mHelper;

    private ReviewerDb(Context context) {
        mHelper = new ReviewerDbHelper(context, CONTRACT);
    }

    public static ReviewerDb getDatabase(Context context) {
        if (sDatabase == null) sDatabase = new ReviewerDb(context);
        return sDatabase;
    }

    public void addReviewToDb(ReviewNode node) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        insertReviewsTable(node, db);
        insertReviewsTreeTable(node, db);
        insertCommentsTable(node, db);
        insertFactsTable(node, db);
        insertLocationsTable(node, db);
        insertImagesTable(node, db);

        for (ReviewNode child : node.getChildren()) {
            addReviewToDb(child);
        }
    }

    private void insertReviewsTable(ReviewNode node, SQLiteDatabase db) {
        Review review = node.getReview();

        String reviewId = review.getId().toString();
        String authorId = review.getAuthor().getUserId().toString();
        long publishDate = review.getPublishDate().getTime();
        String subject = review.getSubject().get();
        float rating = review.getRating().get();

        ContentValues values = new ContentValues();
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID, reviewId);
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_AUTHOR_ID, authorId);
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_PUBLISH_DATE, publishDate);
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_SUBJECT, subject);
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_RATING, rating);

        insertRow(db, ReviewerDbContract.TableReviews.get(), values, reviewId);
    }

    private void insertReviewsTreeTable(ReviewNode node, SQLiteDatabase db) {
        String nodeId = node.getId().toString();
        String reviewId = node.getReview().getId().toString();

        ContentValues values = new ContentValues();
        values.put(ReviewerDbContract.TableReviewTrees.COLUMN_NAME_REVIEW_NODE_ID, nodeId);
        values.put(ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID, reviewId);

        ReviewNode parent = node.getParent();
        if (parent != null) {
            String parentId = parent.getId().toString();
            values.put(ReviewerDbContract.TableReviewTrees.COLUMN_NAME_PARENT_NODE_ID, parentId);
        }

        insertRow(db, ReviewerDbContract.TableReviewTrees.get(), values, nodeId);
    }

    private void insertCommentsTable(ReviewNode node, SQLiteDatabase db) {
        Review review = node.getReview();
        MdCommentList comments = review.getComments();

        int i = 1;
        for (MdCommentList.MdComment comment : comments) {
            String reviewId = review.getId().toString();
            String commentId = reviewId + SEPARATOR + "c" + String.valueOf(i++);
            String commentString = comment.getComment();
            int isHeadline = booleanToInt(comment.isHeadline());

            ContentValues values = new ContentValues();
            values.put(ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID, commentId);
            values.put(ReviewerDbContract.TableComments.COLUMN_NAME_REVIEW_ID, reviewId);
            values.put(ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT, commentString);
            values.put(ReviewerDbContract.TableComments.COLUMN_NAME_IS_HEADLINE, isHeadline);

            insertRow(db, ReviewerDbContract.TableComments.get(), values, commentId);
        }
    }

    private void insertFactsTable(ReviewNode node, SQLiteDatabase db) {
        Review review = node.getReview();
        MdFactList facts = review.getFacts();

        int i = 1;
        for (MdFactList.MdFact fact : facts) {
            String reviewId = review.getId().toString();
            String factId = reviewId + SEPARATOR + "c" + String.valueOf(i++);
            String label = fact.getLabel();
            String value = fact.getValue();
            int isUrl = booleanToInt(fact.isUrl());

            ContentValues values = new ContentValues();
            values.put(ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID, factId);
            values.put(ReviewerDbContract.TableFacts.COLUMN_NAME_REVIEW_ID, reviewId);
            values.put(ReviewerDbContract.TableFacts.COLUMN_NAME_LABEL, label);
            values.put(ReviewerDbContract.TableFacts.COLUMN_NAME_VALUE, value);
            values.put(ReviewerDbContract.TableFacts.COLUMN_NAME_IS_URL, isUrl);

            insertRow(db, ReviewerDbContract.TableFacts.get(), values, factId);
        }
    }

    private void insertLocationsTable(ReviewNode node, SQLiteDatabase db) {
        Review review = node.getReview();
        MdLocationList locations = review.getLocations();

        int i = 1;
        for (MdLocationList.MdLocation location : locations) {
            String reviewId = review.getId().toString();
            String locationId = reviewId + SEPARATOR + "f" + String.valueOf(i++);
            LatLng latlng = location.getLatLng();
            double latitude = latlng.latitude;
            double longitude = latlng.longitude;
            String name = location.getName();

            ContentValues values = new ContentValues();
            values.put(ReviewerDbContract.TableLocations.COLUMN_NAME_LOCATION_ID, locationId);
            values.put(ReviewerDbContract.TableLocations.COLUMN_NAME_REVIEW_ID, reviewId);
            values.put(ReviewerDbContract.TableLocations.COLUMN_NAME_LATITUDE, latitude);
            values.put(ReviewerDbContract.TableLocations.COLUMN_NAME_LONGITUDE, longitude);
            values.put(ReviewerDbContract.TableLocations.COLUMN_NAME_NAME, name);

            insertRow(db, ReviewerDbContract.TableLocations.get(), values, locationId);
        }
    }

    private void insertImagesTable(ReviewNode node, SQLiteDatabase db) {
        Review review = node.getReview();
        MdImageList images = review.getImages();

        int i = 1;
        for (MdImageList.MdImage image : images) {
            String reviewId = review.getId().toString();
            String imageId = reviewId + SEPARATOR + "i" + String.valueOf(i++);
            Bitmap bitmap = image.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] blob = bos.toByteArray();
            String caption = image.getCaption();
            int isCover = booleanToInt(image.isCover());

            ContentValues values = new ContentValues();
            values.put(ReviewerDbContract.TableImages.COLUMN_NAME_IMAGE_ID, imageId);
            values.put(ReviewerDbContract.TableImages.COLUMN_NAME_REVIEW_ID, reviewId);
            values.put(ReviewerDbContract.TableImages.COLUMN_NAME_BITMAP, blob);
            values.put(ReviewerDbContract.TableImages.COLUMN_NAME_IS_COVER, isCover);
            if (DataValidator.validateString(caption)) {
                values.put(ReviewerDbContract.TableImages.COLUMN_NAME_CAPTION, caption);
            }

            insertRow(db, ReviewerDbContract.TableImages.get(), values, imageId);
        }
    }

    private int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    private void insertRow(SQLiteDatabase db, SQLiteTableDefinition table, ContentValues values,
            String item) {
        String tableName = table.getName();
        String message = item + " into " + tableName + " table";
        try {

            db.insertOrThrow(tableName, null, values);
            Log.i(TAG, "Inserted " + message);
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }
}
