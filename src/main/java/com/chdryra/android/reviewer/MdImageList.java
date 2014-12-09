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
public class MdImageList extends MdList<MdImageList.MdImage> {

    public MdImageList(Review holdingReview) {
        super(holdingReview);
    }

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
    public static class MdImage implements MdData, DataImage {

        private final Bitmap mBitmap;
        private final String mCaption;
        private final LatLng mLatLng;
        private       Review mHoldingReview;
        private boolean mIsCover = false;

        public MdImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover,
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
        public boolean hasData() {
            return mBitmap != null;
        }

        @Override
        public Bitmap getBitmap() {
            return mBitmap;
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
        public LatLng getLatLng() {
            return mLatLng;
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
            if (mHoldingReview != null ? !mHoldingReview.equals(mdImage.mHoldingReview) : mdImage
                    .mHoldingReview != null) {
                return false;
            }
            if (mLatLng != null ? !mLatLng.equals(mdImage.mLatLng) : mdImage.mLatLng != null) {
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
