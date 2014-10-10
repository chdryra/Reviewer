/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

/**
 * GVReviewDataList: GVImage
 * ViewHolder: VHImageView
 * <p/>
 * <p>
 * Includes methods for adding captions and getting images designated as "covers" which can be
 * used as a background image for a review.
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewImages
 * @see com.chdryra.android.reviewer.VHImageView
 */
class GVImageList extends GVReviewDataList<GVImageList.GVImage> {

    GVImageList() {
        super(GVType.IMAGES);
    }

    void add(Bitmap bitmap, LatLng latLng) {
        add(new GVImage(bitmap, latLng, null));
    }

    void add(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
        add(new GVImage(bitmap, latLng, caption, isCover));
    }

    void updateCaption(Bitmap bitmap, LatLng latLng, String oldCaption, String newCaption) {
        GVImage image = getItem(indexOf(new GVImage(bitmap, latLng, oldCaption)));
        image.setCaption(newCaption);
    }

    GVImage getRandomCover() {
        GVImageList covers = getCovers();
        if (covers.size() == 0) {
            return null;
        }

        Random r = new Random();

        return covers.getItem(r.nextInt(covers.size()));
    }

    GVImageList getCovers() {
        GVImageList covers = new GVImageList();
        for (GVImage image : this) {
            if (image.isCover()) {
                covers.add(image);
            }
        }

        return covers;
    }

    /**
     * GVData version of: RDImage
     * ViewHolder: VHImageView
     * <p/>
     * <p>
     * Methods for getting the bitmap, caption, LatLng and whether a cover.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.RDImage
     * @see com.chdryra.android.reviewer.VHImageView
     */
    class GVImage implements GVData {
        private final Bitmap mBitmap;
        private final LatLng mLatLng;
        private       String mCaption;
        private boolean mIsCover = false;

        GVImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        GVImage(Bitmap bitmap, LatLng latLng, String caption) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
        }

        Bitmap getBitmap() {
            return mBitmap;
        }

        String getCaption() {
            return mCaption;
        }

        void setCaption(String caption) {
            mCaption = caption;
        }

        LatLng getLatLng() {
            return mLatLng;
        }

        void setIsCover(boolean isCover) {
            mIsCover = isCover;
        }

        boolean isCover() {
            return mIsCover;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHImageView();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            GVImage other = (GVImage) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (mBitmap == null) {
                if (other.mBitmap != null) {
                    return false;
                }
            } else if (!mBitmap.equals(other.mBitmap)) {
                return false;
            }
            if (mCaption == null) {
                if (other.mCaption != null) {
                    return false;
                }
            } else if (!mCaption.equals(other.mCaption)) {
                return false;
            }
            if (mLatLng == null) {
                if (other.mLatLng != null) {
                    return false;
                }
            } else if (!mLatLng.equals(other.mLatLng)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result
                    + ((mBitmap == null) ? 0 : mBitmap.hashCode());
            result = prime * result
                    + ((mCaption == null) ? 0 : mCaption.hashCode());
            result = prime * result
                    + ((mLatLng == null) ? 0 : mLatLng.hashCode());
            return result;
        }

        private GVImageList getOuterType() {
            return GVImageList.this;
        }
    }
}
