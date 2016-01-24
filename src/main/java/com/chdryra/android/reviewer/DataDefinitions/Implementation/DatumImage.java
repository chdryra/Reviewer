/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumImage implements DataImage {
    private final Bitmap mBitmap;
    private final DataDate mDate;
    private final String mCaption;
    private final ReviewId mReviewId;
    private boolean mIsCover = false;

    public DatumImage(ReviewId reviewId) {
        mReviewId = reviewId;
        mBitmap = null;
        mDate = null;
        mCaption = "";
        mIsCover = false;
    }

    public DatumImage(ReviewId reviewId, Bitmap bitmap, @Nullable DataDate date,
                      @Nullable String caption, boolean isCover) {
        mReviewId = reviewId;
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mIsCover = isCover;
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public DataDate getDate() {
        return mDate;
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
    public boolean hasData(DataValidator va) {
        return va.validate(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumImage)) return false;

        DatumImage that = (DatumImage) o;

        if (mIsCover != that.mIsCover) return false;
        if (mBitmap != null ? !mBitmap.equals(that.mBitmap) : that.mBitmap != null) return false;
        if (mDate != null ? !mDate.equals(that.mDate) : that.mDate != null) return false;
        if (!mCaption.equals(that.mCaption)) return false;
        return mReviewId.equals(that.mReviewId);

    }

    @Override
    public int hashCode() {
        int result = mBitmap != null ? mBitmap.hashCode() : 0;
        result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
        result = 31 * result + mCaption.hashCode();
        result = 31 * result + mReviewId.hashCode();
        result = 31 * result + (mIsCover ? 1 : 0);
        return result;
    }
}
