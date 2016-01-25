/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Implementation.ByteArray;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowImage;

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

    private boolean mValidIsCover = true;

    //Constructors
    public RowImageImpl(DataImage image, int index) {
        mReviewId = image.getReviewId().toString();
        mImageId = mReviewId + SEPARATOR + "i" + String.valueOf(index);
        mIsCover = image.isCover();
        mCaption = image.getCaption();
        mDate = image.getDate() != null ? image.getDate().getTime() : -1;
        Bitmap bitmap = image.getBitmap();
        if(bitmap != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            mBitmap = bos.toByteArray();
        } else {
            mBitmap = null;
        }
    }

    //Via reflection
    public RowImageImpl() {
    }

    public RowImageImpl(RowValues values) {
        mImageId = values.getValue(IMAGE_ID.getName(), IMAGE_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());

        ByteArray array = values.getValue(BITMAP.getName(), BITMAP.getType());
        mBitmap = array != null ? array.getData() : null;

        Boolean cover = values.getValue(IS_COVER.getName(), IS_COVER.getType());
        if(cover == null) mValidIsCover = false;
        mIsCover = mValidIsCover && cover;

        mCaption = values.getValue(CAPTION.getName(), CAPTION.getType());

        Long time = values.getValue(IMAGE_DATE.getName(), IMAGE_DATE.getType());
        mDate = time != null ? time : 0l;
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
        byte[] data = mBitmap != null? mBitmap : new byte[]{};
        return BitmapFactory.decodeByteArray(data, 0, data.length);
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
        return mBitmap != null && mDate > 0 && mValidIsCover && validator.validate(this) &&
                validator.validateString(mImageId) && validator.validateString(mReviewId);
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
        } else if(position == 5) {
            return new RowEntryImpl<>(IMAGE_DATE, mDate);
        } else {
            throw noElement();
        }
    }
}
