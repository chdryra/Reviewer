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

public class GVImageList extends GVReviewDataList<GVImageList.GVImage> {

    public GVImageList() {
        super(GVType.IMAGES);
    }

    public void add(Bitmap bitmap, LatLng latLng) {
        add(new GVImage(bitmap, latLng, null));
    }

    public void add(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
        add(new GVImage(bitmap, latLng, caption, isCover));
    }

    public void updateCaption(Bitmap bitmap, LatLng latLng, String oldCaption, String newCaption) {
        GVImage image = getItem(indexOf(new GVImage(bitmap, latLng, oldCaption)));
        image.setCaption(newCaption);
    }

    public GVImage getRandomCover() {
        GVImageList covers = getCovers();
        if (covers.size() == 0) {
            return null;
        }

        Random r = new Random();

        return covers.getItem(r.nextInt(covers.size()));
    }

    public GVImageList getCovers() {
        GVImageList covers = new GVImageList();
        for (GVImage image : this) {
            if (image.isCover()) {
                covers.add(image);
            }
        }

        return covers;
    }

    class GVImage implements GVData {
        private final Bitmap mBitmap;
        private final LatLng mLatLng;
        private       String mCaption;
        private boolean mIsCover = false;

        public GVImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        public GVImage(Bitmap bitmap, LatLng latLng, String caption) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

        public String getCaption() {
            return mCaption;
        }

        public void setCaption(String caption) {
            mCaption = caption;
        }

        public LatLng getLatLng() {
            return mLatLng;
        }

        public void setIsCover(boolean isCover) {
            mIsCover = isCover;
        }

        public boolean isCover() {
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
