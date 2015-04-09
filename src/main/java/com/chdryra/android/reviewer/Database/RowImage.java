/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.MdImageList;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImage implements ReviewerDbRow.TableRow {
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

    public RowImage() {
    }

    public RowImage(MdImageList.MdImage image, int index) {
        mReviewId = image.getReviewId().toString();
        mImageId = mReviewId + ReviewerDbRow.SEPARATOR + "i" + String.valueOf(index);
        mCaption = image.getCaption();
        mIsCover = image.isCover();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        mBitmap = bos.toByteArray();
    }

    public RowImage(Cursor cursor) {
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
    public String getRowIdColumnName() {
        return IMAGE_ID;
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
