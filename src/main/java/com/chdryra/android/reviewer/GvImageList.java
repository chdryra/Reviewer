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
public class GvImageList extends GvDataList<GvImageList.GvImage> {
    public static final GvType TYPE = GvType.IMAGES;

    public GvImageList() {
        super(TYPE);
    }

    void add(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
        add(new GvImage(bitmap, latLng, caption, isCover));
    }

    boolean contains(Bitmap bitmap) {
        for (GvImage image : this) {
            if (image.getBitmap().sameAs(bitmap)) return true;
        }

        return false;
    }

    GvImage getRandomCover() {
        GvImageList covers = getCovers();
        if (covers.size() == 0) {
            return null;
        }

        Random r = new Random();

        return covers.getItem(r.nextInt(covers.size()));
    }

    GvImageList getCovers() {
        GvImageList covers = new GvImageList();
        for (GvImage image : this) {
            if (image.isCover()) {
                covers.add(image);
            }
        }

        return covers;
    }

    /**
     * {@link GvDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdImageList.MdImage}
     * {@link ViewHolder}: {@link VHImage}
     */
    public static class GvImage implements GvDataList.GvData, DataImage {
        public static final Parcelable.Creator<GvImage> CREATOR = new Parcelable
                .Creator<GvImage>() {
            public GvImage createFromParcel(Parcel in) {
                return new GvImage(in);
            }

            public GvImage[] newArray(int size) {
                return new GvImage[size];
            }
        };
        private final Bitmap mBitmap;
        private final LatLng mLatLng;
        private       String mCaption;
        private boolean mIsCover = false;

        GvImage(Bitmap bitmap, LatLng latLng) {
            mBitmap = bitmap;
            mLatLng = latLng;
        }

        public GvImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover) {
            mBitmap = bitmap;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        GvImage(Parcel in) {
            mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
            mCaption = in.readString();
            mLatLng = in.readParcelable(LatLng.class.getClassLoader());
            mIsCover = in.readByte() != 0;
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHImage();
        }

        @Override
        public boolean isValidForDisplay() {
            return mBitmap != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvImage)) return false;

            GvImage gvImage = (GvImage) o;

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

        public void setCaption(String caption) {
            mCaption = caption;
        }

        public void setIsCover(boolean isCover) {
            mIsCover = isCover;
        }
    }
}
