package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImageImpl implements RowImage {
    private static final String SEPARATOR = ":";

    private String mImageId;
    private String mReviewId;
    private byte[] mBitmap;
    private long mDate;
    private String mCaption;
    private boolean mIsCover;

    //Constructors
    public RowImageImpl(DataImage image, int index) {
        mReviewId = image.getReviewId();
        mImageId = mReviewId + SEPARATOR + "i" + String.valueOf(index);
        mCaption = image.getCaption();
        mIsCover = image.isCover();
        mDate = image.getDate().getTime();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        mBitmap = bos.toByteArray();
    }

    //Via reflection
    public RowImageImpl() {
    }

    public RowImageImpl(Cursor cursor) {
        mImageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mBitmap = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_BITMAP));
        mDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DATE));
        mCaption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPTION));
        mIsCover = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COVER)) == 1;
    }


    //Overridden

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(mBitmap, 0, mBitmap.length);
    }

    @Override
    public DataDate getDate() {
        return new DatumDateReview(mReviewId, mDate);
    }

    @Override
    public String getCaption() {
        return mCaption;
    }

    @Override
    public boolean isCover() {
        return mIsCover;
    }

    @Override
    public String getRowId() {
        return mImageId;
    }

    @Override
    public String getRowIdColumnName() {
        return COLUMN_IMAGE_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_ID, mImageId);
        values.put(COLUMN_REVIEW_ID, mReviewId);
        values.put(COLUMN_BITMAP, mBitmap);
        values.put(COLUMN_IMAGE_DATE, mDate);
        values.put(COLUMN_CAPTION, mCaption);
        values.put(COLUMN_IS_COVER, mIsCover);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
