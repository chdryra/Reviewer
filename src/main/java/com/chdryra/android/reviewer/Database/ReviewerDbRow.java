/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRow {
    public static final String SEPARATOR = ":";

    public interface TableRow {
        public String getRowId();

        public ContentValues getContentValues();

        public boolean hasData();
    }

    public static <T extends TableRow> T emptyRow(Class<T> rowClass) {
        try {
            return rowClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        }
    }

    public static <T extends TableRow> T newRow(Cursor cursor, Class<T> rowClass) {
        try {
            Constructor c = rowClass.getConstructor(Cursor.class);
            return rowClass.cast(c.newInstance(cursor));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find Cursor constructor for " + rowClass
                    .getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Couldn't invoke class " + rowClass.getName(), e);
        }
    }

    public static TableRow newRow(ReviewNode node) {
        return new ReviewTreesRow(node);
    }

    public static TableRow newRow(Review review) {
        return new ReviewsRow(review);
    }

    public static TableRow newRow(MdCommentList.MdComment comment, int index) {
        return new CommentsRow(comment, index);
    }

    public static TableRow newRow(MdFactList.MdFact fact, int index) {
        return new FactsRow(fact, index);
    }

    public static TableRow newRow(MdLocationList.MdLocation location, int index) {
        return new LocationsRow(location, index);
    }

    public static TableRow newRow(MdImageList.MdImage image, int index) {
        return new ImagesRow(image, index);
    }

    public static class ReviewTreesRow implements TableRow {
        public static final String NODE_ID    = ReviewerDbContract.TableReviewTrees
                .COLUMN_NAME_REVIEW_NODE_ID;
        public static final String REVIEW_ID  = ReviewerDbContract.TableReviewTrees
                .COLUMN_NAME_REVIEW_ID;
        public static final String PARENT_ID  = ReviewerDbContract.TableReviewTrees
                .COLUMN_NAME_PARENT_NODE_ID;
        public static final String IS_AVERAGE = ReviewerDbContract.TableReviewTrees
                .COLUMN_NAME_RATING_IS_AVERAGE;

        private String  mNodeId;
        private String  mReviewId;
        private String  mParentId;
        private boolean mIsAverage;

        public ReviewTreesRow() {
        }

        public ReviewTreesRow(ReviewNode node) {
            mNodeId = node.getId().toString();
            mReviewId = node.getReview().getId().toString();
            ReviewNode parent = node.getParent();
            if (parent != null) mParentId = parent.getId().toString();
            mIsAverage = node.isRatingAverageOfChildren();
        }

        public ReviewTreesRow(Cursor cursor) {
            mNodeId = cursor.getString(cursor.getColumnIndexOrThrow(NODE_ID));
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mParentId = cursor.getString(cursor.getColumnIndexOrThrow(PARENT_ID));
            mIsAverage = cursor.getInt(cursor.getColumnIndexOrThrow(IS_AVERAGE)) == 1;
        }

        @Override
        public String getRowId() {
            return mNodeId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(NODE_ID, mNodeId);
            values.put(REVIEW_ID, mReviewId);
            values.put(PARENT_ID, mParentId);
            values.put(IS_AVERAGE, mIsAverage);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class ReviewsRow implements TableRow {
        public static String REVIEW_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID;
        public static String AUTHOR_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_AUTHOR_ID;
        public static String PUBLISH_DATE = ReviewerDbContract.TableReviews
                .COLUMN_NAME_PUBLISH_DATE;
        public static String SUBJECT      = ReviewerDbContract.TableReviews.COLUMN_NAME_SUBJECT;
        public static String RATING       = ReviewerDbContract.TableReviews.COLUMN_NAME_RATING;

        private String mReviewId;
        private String mAuthorId;
        private long   mPublishDate;
        private String mSubject;
        private float  mRating;

        public ReviewsRow() {
        }

        public ReviewsRow(Review review) {
            mReviewId = review.getId().toString();
            mAuthorId = review.getAuthor().getUserId().toString();
            mPublishDate = review.getPublishDate().getTime();
            mSubject = review.getSubject().get();
            mRating = review.getRating().get();
        }

        public ReviewsRow(Cursor cursor) {
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mAuthorId = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_ID));
            mPublishDate = cursor.getLong(cursor.getColumnIndexOrThrow(PUBLISH_DATE));
            mSubject = cursor.getString(cursor.getColumnIndexOrThrow(SUBJECT));
            mRating = cursor.getFloat(cursor.getColumnIndexOrThrow(RATING));
        }

        @Override
        public String getRowId() {
            return mReviewId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(REVIEW_ID, mReviewId);
            values.put(AUTHOR_ID, mAuthorId);
            values.put(PUBLISH_DATE, mPublishDate);
            values.put(SUBJECT, mSubject);
            values.put(RATING, mRating);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class CommentsRow implements TableRow {
        public static String COMMENT_ID  = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID;
        public static String REVIEW_ID   = ReviewerDbContract.TableComments.COLUMN_NAME_REVIEW_ID;
        public static String COMMENT     = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT;
        public static String IS_HEADLINE = ReviewerDbContract.TableComments.COLUMN_NAME_IS_HEADLINE;

        private String  mCommentId;
        private String  mReviewId;
        private String  mComment;
        private boolean mIsHeadline;

        public CommentsRow() {
        }

        public CommentsRow(MdCommentList.MdComment comment, int index) {
            mReviewId = comment.getReviewId().toString();
            mCommentId = mReviewId + SEPARATOR + "c" + String.valueOf(index);
            mComment = comment.getComment();
            mIsHeadline = comment.isHeadline();
        }

        public CommentsRow(Cursor cursor) {
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_ID));
            mComment = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT));
            mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(IS_HEADLINE)) == 1;
        }

        @Override
        public String getRowId() {
            return mCommentId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(COMMENT_ID, mCommentId);
            values.put(REVIEW_ID, mReviewId);
            values.put(COMMENT, mComment);
            values.put(IS_HEADLINE, mIsHeadline);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class FactsRow implements TableRow {
        public static String FACT_ID   = ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID;
        public static String REVIEW_ID = ReviewerDbContract.TableFacts.COLUMN_NAME_REVIEW_ID;
        public static String LABEL     = ReviewerDbContract.TableFacts.COLUMN_NAME_LABEL;
        public static String VALUE     = ReviewerDbContract.TableFacts.COLUMN_NAME_VALUE;
        public static String IS_URL    = ReviewerDbContract.TableFacts.COLUMN_NAME_IS_URL;

        private String        mFactId;
        private String mReviewId;
        private String        mLabel;
        private String        mValue;
        private boolean       mIsUrl;

        public FactsRow() {
        }

        public FactsRow(MdFactList.MdFact fact, int index) {
            mReviewId = fact.getReviewId().toString();
            mFactId = mReviewId + SEPARATOR + "f" + String.valueOf(index);
            mLabel = fact.getLabel();
            mValue = fact.getValue();
            mIsUrl = fact.isUrl();
        }

        public FactsRow(Cursor cursor) {
            mFactId = cursor.getString(cursor.getColumnIndexOrThrow(FACT_ID));
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mLabel = cursor.getString(cursor.getColumnIndexOrThrow(LABEL));
            mValue = cursor.getString(cursor.getColumnIndexOrThrow(VALUE));
            mIsUrl = cursor.getInt(cursor.getColumnIndexOrThrow(IS_URL)) == 1;
        }

        @Override
        public String getRowId() {
            return mFactId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(FACT_ID, mFactId);
            values.put(REVIEW_ID, mReviewId);
            values.put(LABEL, mLabel);
            values.put(VALUE, mValue);
            values.put(IS_URL, mIsUrl);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class LocationsRow implements TableRow {
        public static String LOCATION_ID = ReviewerDbContract.TableLocations
                .COLUMN_NAME_LOCATION_ID;
        public static String REVIEW_ID   = ReviewerDbContract.TableLocations.COLUMN_NAME_REVIEW_ID;
        public static String LAT         = ReviewerDbContract.TableLocations.COLUMN_NAME_LATITUDE;
        public static String LNG         = ReviewerDbContract.TableLocations.COLUMN_NAME_LONGITUDE;
        public static String NAME        = ReviewerDbContract.TableLocations.COLUMN_NAME_NAME;

        private String        mLocationId;
        private String mReviewId;
        private double mLatitude;
        private double mLongitude;
        private String        mName;

        public LocationsRow() {
        }

        public LocationsRow(MdLocationList.MdLocation location, int index) {
            mReviewId = location.getReviewId().toString();
            mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
            mLatitude = location.getLatLng().latitude;
            mLongitude = location.getLatLng().longitude;
            mName = location.getName();
        }

        public LocationsRow(Cursor cursor) {
            mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_ID));
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LAT));
            mLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LNG));
            mName = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
        }


        @Override
        public String getRowId() {
            return mLocationId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(LOCATION_ID, mLocationId);
            values.put(REVIEW_ID, mReviewId);
            values.put(LAT, mLatitude);
            values.put(LNG, mLongitude);
            values.put(NAME, mName);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class ImagesRow implements TableRow {
        public static String IMAGE_ID  = ReviewerDbContract.TableImages.COLUMN_NAME_IMAGE_ID;
        public static String REVIEW_ID = ReviewerDbContract.TableImages.COLUMN_NAME_REVIEW_ID;
        public static String BITMAP    = ReviewerDbContract.TableImages.COLUMN_NAME_BITMAP;
        public static String CAPTION   = ReviewerDbContract.TableImages.COLUMN_NAME_CAPTION;
        public static String IS_COVER  = ReviewerDbContract.TableImages.COLUMN_NAME_IS_COVER;

        private String  mImageId;
        private String  mReviewId;
        private byte[]  mBitmap;
        private String  mCaption;
        private boolean mIsCover;

        public ImagesRow() {
        }

        public ImagesRow(MdImageList.MdImage image, int index) {
            mReviewId = image.getReviewId().toString();
            mImageId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
            mCaption = image.getCaption();
            mIsCover = image.isCover();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
            mBitmap = bos.toByteArray();
        }

        public ImagesRow(Cursor cursor) {
            mImageId = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_ID));
            mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mBitmap = cursor.getBlob(cursor.getColumnIndexOrThrow(BITMAP));
            mCaption = cursor.getString(cursor.getColumnIndexOrThrow(CAPTION));
            mIsCover = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COVER)) == 1;
        }

        @Override
        public String getRowId() {
            return mImageId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(IMAGE_ID, mImageId);
            values.put(REVIEW_ID, mReviewId);
            values.put(BITMAP, mBitmap);
            values.put(CAPTION, mCaption);
            values.put(IS_COVER, mIsCover);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class TagsRow implements TableRow {
        public static String TAG     = ReviewerDbContract.TableTags.COLUMN_NAME_TAG;
        public static String REVIEWS = ReviewerDbContract.TableTags.COLUMN_NAME_REVIEWS;

        private String mTag;
        private String mReviews;

        public TagsRow() {
        }

        public TagsRow(TagsManager.ReviewTag tag) {
            mTag = tag.get();
            mReviews = "";
            for (ReviewId id : tag.getReviews()) {
                mReviews += id.toString() + ",";
            }
            mReviews = mReviews.substring(0, mReviews.length() - 1);
        }

        public TagsRow(Cursor cursor) {
            mTag = cursor.getString(cursor.getColumnIndexOrThrow(TAG));
            mReviews = cursor.getString(cursor.getColumnIndexOrThrow(REVIEWS));
        }

        @Override
        public String getRowId() {
            return mTag;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(TAG, mTag);
            values.put(REVIEWS, mReviews);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }

    public static class AuthorsRow implements TableRow {
        public static String USER_ID     = ReviewerDbContract.TableAuthors.COLUMN_NAME_USER_ID;
        public static String AUTHOR_NAME = ReviewerDbContract.TableAuthors.COLUMN_NAME_NAME;

        private String mUserId;
        private String mName;

        public AuthorsRow() {
        }

        public AuthorsRow(Author author) {
            mUserId = author.getUserId().toString();
            mName = author.getName();
        }

        public AuthorsRow(Cursor cursor) {
            mUserId = cursor.getString(cursor.getColumnIndexOrThrow(USER_ID));
            mName = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_NAME));
        }

        @Override
        public String getRowId() {
            return mUserId;
        }

        @Override
        public ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(USER_ID, mUserId);
            values.put(AUTHOR_NAME, mName);

            return values;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validateString(getRowId());
        }
    }
}
