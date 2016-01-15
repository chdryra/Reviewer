package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.ByteArray;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImageImpl extends RowTableBasic implements RowImage {
    private static final String SEPARATOR = ":";

    private String mImageId;
    private String mReviewId;
    private byte[] mBitmap;
    private boolean mIsCover;
    private String mCaption;
    private long mDate;

    //Constructors
    public RowImageImpl(DataImage image, int index) {
        mReviewId = image.getReviewId().toString();
        mImageId = mReviewId + SEPARATOR + "i" + String.valueOf(index);
        mIsCover = image.isCover();
        mCaption = image.getCaption();
        mDate = image.getDate() != null ? image.getDate().getTime() : -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        mBitmap = bos.toByteArray();
    }

    //Via reflection
    public RowImageImpl() {
    }

    public RowImageImpl(RowValues values) {
        mImageId = values.getValue(IMAGE_ID.getName(), IMAGE_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mBitmap = values.getValue(BITMAP.getName(), BITMAP.getType()).getData();
        mIsCover = values.getValue(IS_COVER.getName(), IS_COVER.getType());
        mCaption = values.getValue(CAPTION.getName(), CAPTION.getType());
        mDate = values.getValue(IMAGE_DATE.getName(), IMAGE_DATE.getType());
    }

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
        return IMAGE_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    protected int size() {
        return 6;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(IMAGE_ID, mImageId);
        } else if(position == 1) {
            return new RowEntryImpl<>(REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(BITMAP, new ByteArray(mBitmap));
        } else if(position == 3) {
            return new RowEntryImpl<>(IS_COVER, mIsCover);
        } else if(position == 4) {
            return new RowEntryImpl<>(CAPTION, mCaption);
        } else {
            return new RowEntryImpl<>(IMAGE_DATE, mDate);
        }
    }
}
