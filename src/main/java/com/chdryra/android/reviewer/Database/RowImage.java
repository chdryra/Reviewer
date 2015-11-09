package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImage implements MdDataRow<MdImageList.MdImage>, DataImage {
    public static final String COLUMN_IMAGE_ID = "image_id";
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_BITMAP = "bitmap";
    public static final String COLUMN_IMAGE_DATE = "image_date";
    public static final String COLUMN_CAPTION = "caption";
    public static final String COLUMN_IS_COVER = "is_cover";

    private static final String SEPARATOR = ":";

    private String mImageId;
    private String mReviewId;
    private byte[] mBitmap;
    private long mDate;
    private String mCaption;
    private boolean mIsCover;

    //Constructors
    public RowImage(MdImageList.MdImage image, int index) {
        mReviewId = image.getReviewId().toString();
        mImageId = mReviewId + SEPARATOR + "i" + String.valueOf(index);
        mCaption = image.getCaption();
        mIsCover = image.isCover();
        mDate = image.getDate().getTime();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        mBitmap = bos.toByteArray();
    }

    //Via reflection
    public RowImage() {
    }

    public RowImage(Cursor cursor) {
        mImageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mBitmap = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_BITMAP));
        mDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DATE));
        mCaption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPTION));
        mIsCover = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COVER)) == 1;
    }

    //Overridden

    @Override
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(mBitmap, 0, mBitmap.length);
    }

    @Override
    public Date getDate() {
        return new Date(mDate);
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

    @Override
    public MdImageList.MdImage toMdData() {
        ReviewId id = ReviewId.fromString(mReviewId);
        return new MdImageList.MdImage(getBitmap(), getDate(), mCaption, mIsCover, id);
    }
}
