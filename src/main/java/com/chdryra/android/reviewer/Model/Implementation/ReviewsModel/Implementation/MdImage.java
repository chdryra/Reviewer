/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import junit.framework.Assert;

/**
 * Review Data: image
 * <p>
 * Consists of bitmap and optionally caption, LatLng. Also knows whether a cover image.
 * </p>
 */
public class MdImage implements DataImage {
    private final Bitmap mBitmap;
    private final MdDate mDate;
    private final String mCaption;
    private final MdReviewId mReviewId;
    private boolean mIsCover = false;

    //Constructors
    public MdImage(MdReviewId reviewId, Bitmap bitmap, MdDate date, String caption, boolean isCover) {
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mIsCover = isCover;
        mReviewId = reviewId;
        Assert.assertEquals(reviewId, date.getReviewId());
    }

    //Overridden
    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdImage)) return false;

        MdImage mdImage = (MdImage) o;

        if (mIsCover != mdImage.mIsCover) return false;
        if (mBitmap != null ? !mBitmap.sameAs(mdImage.mBitmap) : mdImage.mBitmap != null) {
            return false;
        }
        if (mCaption != null ? !mCaption.equals(mdImage.mCaption) : mdImage.mCaption != null) {
            return false;
        }
        if (mReviewId != null ? !mReviewId.equals(mdImage.mReviewId) : mdImage
                .mReviewId != null) {
            return false;
        }
        if (mDate != null ? !mDate.equals(mdImage.mDate) : mdImage.mDate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mBitmap != null ? mBitmap.hashCode() : 0;
        result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
        result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mIsCover ? 1 : 0);
        return result;
    }
}
