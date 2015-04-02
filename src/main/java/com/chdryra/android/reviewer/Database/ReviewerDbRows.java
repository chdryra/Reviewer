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
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRows {
    public static final String SEPARATOR = ":";

    public interface TableRow {
        public String getRowId();

        public ContentValues getContentValues();
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

        private ContentValues mValues;
        private ReviewId      mNodeId;
        private ReviewId      mReviewId;
        private ReviewId      mParentId;
        private boolean       mIsAverage;

        public ReviewTreesRow(ReviewNode node) {
            mNodeId = node.getId();
            mReviewId = node.getReview().getId();
            ReviewNode parent = node.getParent();
            if (parent != null) mParentId = parent.getId();
            mIsAverage = node.isRatingAverageOfChildren();
            setContentValues();
        }

        public ReviewTreesRow(Cursor cursor) {
            String nodeId = cursor.getString(cursor.getColumnIndexOrThrow(NODE_ID));
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            String parentId = cursor.getString(cursor.getColumnIndexOrThrow(PARENT_ID));
            int isAverage = cursor.getInt(cursor.getColumnIndexOrThrow(IS_AVERAGE));

            mNodeId = ReviewId.fromString(nodeId);
            mReviewId = ReviewId.fromString(reviewId);
            if (DataValidator.validateString(parentId)) mParentId = ReviewId.fromString(parentId);
            mIsAverage = isAverage == 1;
            setContentValues();
        }

        @Override
        public String getRowId() {
            return mNodeId.toString();
        }

        private void setContentValues() {
            mValues = new ContentValues();
            mValues.put(NODE_ID, mNodeId.toString());
            mValues.put(REVIEW_ID, mReviewId.toString());
            if (mParentId != null) mValues.put(PARENT_ID, mParentId.toString());
            mValues.put(IS_AVERAGE, mIsAverage);
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }


    }

    public static class ReviewsRow implements TableRow {
        public static String REVIEW_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID;
        public static String AUTHOR_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_AUTHOR_ID;
        public static String PUBLISH_DATE = ReviewerDbContract.TableReviews
                .COLUMN_NAME_PUBLISH_DATE;
        public static String SUBJECT      = ReviewerDbContract.TableReviews.COLUMN_NAME_SUBJECT;
        public static String RATING       = ReviewerDbContract.TableReviews.COLUMN_NAME_RATING;

        private ContentValues mValues;
        private ReviewId      mReviewId;
        private UserId        mAuthorId;
        private Date          mPublishDate;
        private String        mSubject;
        private float         mRating;

        public ReviewsRow(Review review) {
            mReviewId = review.getId();
            mAuthorId = review.getAuthor().getUserId();
            mPublishDate = review.getPublishDate();
            mSubject = review.getSubject().get();
            mRating = review.getRating().get();
            setContentValues();
        }

        public ReviewsRow(Cursor cursor) {
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            String authorId = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_ID));
            long milliseconds = cursor.getLong(cursor.getColumnIndexOrThrow(PUBLISH_DATE));
            mSubject = cursor.getString(cursor.getColumnIndexOrThrow(SUBJECT));
            mRating = cursor.getFloat(cursor.getColumnIndexOrThrow(RATING));
            mReviewId = ReviewId.fromString(reviewId);
            mAuthorId = UserId.fromString(authorId);
            mPublishDate = new Date(milliseconds);
            setContentValues();
        }

        private void setContentValues() {
            mValues = new ContentValues();
            mValues.put(REVIEW_ID, mReviewId.toString());
            mValues.put(AUTHOR_ID, mAuthorId.toString());
            mValues.put(PUBLISH_DATE, mPublishDate.getTime());
            mValues.put(SUBJECT, mSubject);
            mValues.put(RATING, mRating);
        }

        @Override
        public String getRowId() {
            return mReviewId.toString();
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }
    }

    public static class CommentsRow implements TableRow {
        public static String COMMENT_ID  = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID;
        public static String REVIEW_ID   = ReviewerDbContract.TableComments.COLUMN_NAME_REVIEW_ID;
        public static String COMMENT     = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT;
        public static String IS_HEADLINE = ReviewerDbContract.TableComments.COLUMN_NAME_IS_HEADLINE;

        private ContentValues mValues;
        private String        mCommentId;
        private ReviewId      mReviewId;
        private String        mComment;
        private boolean       mIsHeadline;

        public CommentsRow(MdCommentList.MdComment comment, int index) {
            mReviewId = comment.getReviewId();
            mCommentId = mReviewId.toString() + SEPARATOR + "c" + String.valueOf(index);
            mComment = comment.getComment();
            mIsHeadline = comment.isHeadline();
        }

        public CommentsRow(Cursor cursor) {
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mReviewId = ReviewId.fromString(reviewId);
            mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_ID));
            mComment = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT));
            mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(IS_HEADLINE)) == 1;
            setContentValues();
        }

        private void setContentValues() {
            mValues = new ContentValues();
            mValues.put(COMMENT_ID, mCommentId);
            mValues.put(REVIEW_ID, mReviewId.toString());
            mValues.put(COMMENT, mComment);
            mValues.put(IS_HEADLINE, mIsHeadline);
        }

        @Override
        public String getRowId() {
            return mCommentId;
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }
    }

    public static class FactsRow implements TableRow {
        public static String FACT_ID   = ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID;
        public static String REVIEW_ID = ReviewerDbContract.TableFacts.COLUMN_NAME_REVIEW_ID;
        public static String LABEL     = ReviewerDbContract.TableFacts.COLUMN_NAME_LABEL;
        public static String VALUE     = ReviewerDbContract.TableFacts.COLUMN_NAME_VALUE;
        public static String IS_URL    = ReviewerDbContract.TableFacts.COLUMN_NAME_IS_URL;

        private ContentValues mValues;
        private String        mFactId;
        private ReviewId      mReviewId;
        private String        mLabel;
        private String        mValue;
        private boolean       mIsUrl;

        public FactsRow(MdFactList.MdFact fact, int index) {
            mReviewId = fact.getReviewId();
            mFactId = mReviewId.toString() + SEPARATOR + "f" + String.valueOf(index);
            mLabel = fact.getLabel();
            mValue = fact.getValue();
            mIsUrl = fact.isUrl();
        }

        public FactsRow(Cursor cursor) {
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            mReviewId = ReviewId.fromString(reviewId);
            mFactId = cursor.getString(cursor.getColumnIndexOrThrow(FACT_ID));
            mLabel = cursor.getString(cursor.getColumnIndexOrThrow(LABEL));
            mValue = cursor.getString(cursor.getColumnIndexOrThrow(VALUE));
            mIsUrl = cursor.getInt(cursor.getColumnIndexOrThrow(IS_URL)) == 1;
            setContentValues();
        }

        private void setContentValues() {
            mValues = new ContentValues();
            mValues.put(FACT_ID, mFactId);
            mValues.put(REVIEW_ID, mReviewId.toString());
            mValues.put(LABEL, mLabel);
            mValues.put(VALUE, mValue);
            mValues.put(IS_URL, mIsUrl);
        }

        @Override
        public String getRowId() {
            return mFactId;
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }
    }

    public static class LocationsRow implements TableRow {
        public static String LOCATION_ID = ReviewerDbContract.TableLocations
                .COLUMN_NAME_LOCATION_ID;
        public static String REVIEW_ID   = ReviewerDbContract.TableLocations.COLUMN_NAME_REVIEW_ID;
        public static String LAT         = ReviewerDbContract.TableLocations.COLUMN_NAME_LATITUDE;
        public static String LNG         = ReviewerDbContract.TableLocations.COLUMN_NAME_LONGITUDE;
        public static String NAME        = ReviewerDbContract.TableLocations.COLUMN_NAME_NAME;

        private ContentValues mValues;
        private String        mLocationId;
        private ReviewId      mReviewId;
        private LatLng        mLatLng;
        private String        mName;

        public LocationsRow(MdLocationList.MdLocation location, int index) {
            mReviewId = location.getReviewId();
            mLocationId = mReviewId.toString() + SEPARATOR + "l" + String.valueOf(index);
            mLatLng = location.getLatLng();
            mName = location.getName();
        }

        public LocationsRow(Cursor cursor) {
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(LAT));
            double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(LNG));
            mReviewId = ReviewId.fromString(reviewId);
            mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_ID));
            mLatLng = new LatLng(lat, lng);
            mName = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
            setContentValues();
        }

        private void setContentValues() {
            mValues = new ContentValues();
            mValues.put(LOCATION_ID, mLocationId);
            mValues.put(REVIEW_ID, mReviewId.toString());
            mValues.put(LAT, mLatLng.latitude);
            mValues.put(LNG, mLatLng.longitude);
            mValues.put(NAME, mName);
        }

        @Override
        public String getRowId() {
            return mLocationId;
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }
    }

    public static class ImagesRow implements TableRow {
        public static String IMAGE_ID  = ReviewerDbContract.TableImages.COLUMN_NAME_IMAGE_ID;
        public static String REVIEW_ID = ReviewerDbContract.TableImages.COLUMN_NAME_REVIEW_ID;
        public static String BITMAP    = ReviewerDbContract.TableImages.COLUMN_NAME_BITMAP;
        public static String CAPTION   = ReviewerDbContract.TableImages.COLUMN_NAME_CAPTION;
        public static String IS_COVER  = ReviewerDbContract.TableImages.COLUMN_NAME_IS_COVER;

        private ContentValues mValues;
        private String        mImageId;
        private ReviewId      mReviewId;
        private Bitmap        mBitmap;
        private String        mCaption;
        private boolean       mIsCover;

        public ImagesRow(MdImageList.MdImage image, int index) {
            mReviewId = image.getReviewId();
            mImageId = mReviewId.toString() + SEPARATOR + "l" + String.valueOf(index);
            mBitmap = image.getBitmap();
            mCaption = image.getCaption();
            mIsCover = image.isCover();
            setContentValues(null);
        }

        public ImagesRow(Cursor cursor) {
            String reviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
            byte[] bitmap = cursor.getBlob(cursor.getColumnIndexOrThrow(BITMAP));
            mReviewId = ReviewId.fromString(reviewId);
            mImageId = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_ID));
            mBitmap = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
            mCaption = cursor.getString(cursor.getColumnIndexOrThrow(CAPTION));
            mIsCover = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COVER)) == 1;
            setContentValues(bitmap);
        }

        private void setContentValues(byte[] blob) {
            if (blob == null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                blob = bos.toByteArray();
            }

            mValues = new ContentValues();
            mValues.put(IMAGE_ID, mImageId);
            mValues.put(REVIEW_ID, mReviewId.toString());
            mValues.put(BITMAP, blob);
            if (DataValidator.validateString(mCaption)) mValues.put(CAPTION, mCaption);
        }

        @Override
        public String getRowId() {
            return mImageId;
        }

        @Override
        public ContentValues getContentValues() {
            return mValues;
        }
    }
}
