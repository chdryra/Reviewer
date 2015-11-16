/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdImageList extends MdDataList<MdImageList.MdImage> {

    //Constructors
    public MdImageList(MdReviewId reviewId) {
        super(reviewId);
    }

    //public methods
    public MdImageList getCovers() {
        MdImageList covers = new MdImageList(getMdReviewId());
        for (MdImage image : this) {
            if (image.isCover()) covers.add(image);
        }

        return covers;
    }

//Classes

    /**
     * Review Data: image
     * <p>
     * Consists of bitmap and optionally caption, LatLng. Also knows whether a cover image.
     * </p>
     */
    public static class MdImage implements MdData, DataImage {

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
            if(reviewId != null) {
                Assert.assertEquals(reviewId.toString(), date.getReviewId());
            } else {
                Assert.assertNull(date.getReviewId());
            }
        }

        //Overridden
        @Override
        public String getReviewId() {
            return mReviewId.toString();
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
}
