/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDImageList extends RDList<RDImageList.RDImage> {
    /**
     * Review Data: image
     * <p>
     * Consists of bitmap and optionally caption, LatLng. Also knows whether a cover image.
     * </p>
     * <p/>
     * <p>
     * {@link #hasData()}: non-null bitmap.
     * </p>
     */
    public static class RDImage implements RData {

        private final Bitmap mBitmap;
        private final String mCaption;
        private final LatLng mLatLng;
        private       Review mHoldingReview;
        private boolean mIsCover = false;

        public RDImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover,
                Review holdingReview) {
            mBitmap = bitmap;
            mLatLng = latLng;
            mCaption = caption;
            mIsCover = isCover;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public void setHoldingReview(Review review) {
            mHoldingReview = review;
        }

        @Override
        public boolean hasData() {
            return mBitmap != null;
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

        public String getCaption() {
            return mCaption;
        }

        public LatLng getLatLng() {
            return mLatLng;
        }

        public boolean isCover() {
            return mIsCover;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RDImage)) return false;

            RDImage rdImage = (RDImage) o;

            if (mIsCover != rdImage.mIsCover) return false;
            if (mBitmap != null ? !mBitmap.sameAs(rdImage.mBitmap) : rdImage.mBitmap != null) {
                return false;
            }
            if (mCaption != null ? !mCaption.equals(rdImage.mCaption) : rdImage.mCaption != null) {
                return false;
            }
            if (mHoldingReview != null ? !mHoldingReview.equals(rdImage.mHoldingReview) : rdImage
                    .mHoldingReview != null) {
                return false;
            }
            if (mLatLng != null ? !mLatLng.equals(rdImage.mLatLng) : rdImage.mLatLng != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mBitmap != null ? mBitmap.hashCode() : 0;
            result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
            result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
            result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
            result = 31 * result + (mIsCover ? 1 : 0);
            return result;
        }
    }
}
