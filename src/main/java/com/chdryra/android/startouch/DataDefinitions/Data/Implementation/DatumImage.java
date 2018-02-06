/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumImage implements DataImage {
    private Bitmap mBitmap;
    private DateTime mDate;
    private String mCaption;
    private ReviewId mReviewId;
    private boolean mIsCover = false;
    private LatLng mLatLng;

    public DatumImage() {
    }

    public DatumImage(ReviewId reviewId) {
        mReviewId = reviewId;
        mBitmap = null;
        mDate = null;
        mCaption = "";
        mIsCover = false;
    }

    public DatumImage(ReviewId reviewId,
                      Bitmap bitmap,
                      DateTime date,
                      String caption,
                      @Nullable LatLng latLng,
                      boolean isCover) {
        mReviewId = reviewId;
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mLatLng = latLng;
        mIsCover = isCover;
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public DateTime getDate() {
        return mDate;
    }

    @Override
    public String getCaption() {
        return mCaption;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
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
        if (mCaption != null ? !mCaption.equals(that.mCaption) : that.mCaption != null)
            return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return mLatLng != null ? mLatLng.equals(that.mLatLng) : that.mLatLng == null;

    }

    @Override
    public int hashCode() {
        int result = mBitmap != null ? mBitmap.hashCode() : 0;
        result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
        result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mIsCover ? 1 : 0);
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
