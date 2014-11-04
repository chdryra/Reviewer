/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

/**
 * Includes methods for adding captions and getting images designated as "covers" which can be
 * used as a background image for a review.
 */
class GVImageList extends GVReviewDataList<GVImageList.GVImage> {

    GVImageList() {
        super(GVType.IMAGES);
    }

    /**
     * {@link GVReviewData} version of: {@link RDImage}
     * {@link ViewHolder}: {@link VHImage}
     */
    static class GVImage implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVImage> CREATOR = new Parcelable
                .Creator<GVImage>() {
            public GVImage createFromParcel(Parcel in) {
                return new GVImage(in);
            }

            public GVImage[] newArray(int size) {
                return new GVImage[size];
            }
        };
        private final Bitmap mBitmap;
        private final LatLng mLatLng;
        private       String mCaption;
        private boolean mIsCover = false;

        GVImage(Bitmap bitmap, LatLng latLng) {
            mBitmap = bitmap;
            mLatLng = latLng;
        }

        GVImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        GVImage(Parcel in) {
            mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
            mCaption = in.readString();
            mLatLng = in.readParcelable(LatLng.class.getClassLoader());
            mIsCover = in.readByte() != 0;
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
            return new VHImage();
        }

        @Override
        public boolean isValidForDisplay() {
            return mBitmap != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVImage)) return false;

            GVImage gvImage = (GVImage) o;

            if (mIsCover != gvImage.mIsCover) return false;
            if (mBitmap != null ? !mBitmap.equals(gvImage.mBitmap) : gvImage.mBitmap != null) {
                return false;
            }
            if (mCaption != null ? !mCaption.equals(gvImage.mCaption) : gvImage.mCaption != null) {
                return false;
            }
            if (mLatLng != null ? !mLatLng.equals(gvImage.mLatLng) : gvImage.mLatLng != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mBitmap != null ? mBitmap.hashCode() : 0;
            result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
            result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
            result = 31 * result + (mIsCover ? 1 : 0);
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(mBitmap, i);
            parcel.writeString(mCaption);
            parcel.writeParcelable(mLatLng, i);
            parcel.writeByte((byte) (isCover() ? 1 : 0));
        }
    }

    void add(Bitmap bitmap, LatLng latLng) {
        add(new GVImage(bitmap, latLng));
    }

    void add(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
        add(new GVImage(bitmap, latLng, caption, isCover));
    }

    boolean contains(Bitmap bitmap) {
        for (GVImage image : this) {
            if (image.getBitmap().sameAs(bitmap)) return true;
        }

        return false;
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
}
