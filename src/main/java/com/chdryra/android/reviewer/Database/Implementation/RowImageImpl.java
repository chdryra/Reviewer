package com.chdryra.android.reviewer.Database.Implementation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValues;
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
        mReviewId = image.getReviewId().toString();
        mImageId = mReviewId + SEPARATOR + "i" + String.valueOf(index);
        mCaption = image.getCaption();
        mIsCover = image.isCover();
        mDate = image.getDate() != null ? image.getDate().getTime() : -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        mBitmap = bos.toByteArray();
    }

    //Via reflection
    public RowImageImpl() {
    }

    public RowImageImpl(RowValues values) {
        mImageId = values.getString(COLUMN_IMAGE_ID);
        mReviewId = values.getString(COLUMN_REVIEW_ID);
        Byte[] byteArray = values.getByteArray(COLUMN_BITMAP);
        for(int i = 0; i < byteArray.length; ++i) {
            mBitmap[i] = byteArray[i];
        }
        mDate = values.getLong(COLUMN_IMAGE_DATE);
        mCaption = values.getString(COLUMN_CAPTION);
        mIsCover = values.getBoolean(COLUMN_IS_COVER);
    }


    //Overridden

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
    }

    @Override
    public byte[] getBitmapByteArray() {
        return mBitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(mBitmap, 0, mBitmap.length);
    }

    @Override
    public DataDate getDate() {
        return new DatumDateReview(getReviewId(), mDate);
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
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
