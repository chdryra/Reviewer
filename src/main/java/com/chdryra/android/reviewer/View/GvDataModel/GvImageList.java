/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Random;

/**
 * Includes methods for adding captions and getting images designated as "covers" which can be
 * used as a background image for a review.
 */
public class GvImageList extends GvDataList<GvImageList.GvImage> {
    public static final Parcelable.Creator<GvImageList> CREATOR = new Parcelable
            .Creator<GvImageList>() {
        //Overridden
        public GvImageList createFromParcel(Parcel in) {
            return new GvImageList(in);
        }

        public GvImageList[] newArray(int size) {
            return new GvImageList[size];
        }
    };

    //Constructors
    public GvImageList() {
        super(GvImage.TYPE, null);
    }

    public GvImageList(Parcel in) {
        super(in);
    }

    public GvImageList(GvReviewId id) {
        super(GvImage.TYPE, id);
    }

    public GvImageList(GvImageList data) {
        super(data);
    }

    //public methods
    public GvImage getRandomCover() {
        GvImageList covers = getCovers();
        if (covers.size() == 0) return new GvImage();

        Random r = new Random();
        return covers.getItem(r.nextInt(covers.size()));
    }

    public GvImageList getCovers() {
        GvImageList covers = new GvImageList(getReviewId());
        for (GvImage image : this) {
            if (image.isCover()) covers.add(image);
        }

        return covers;
    }

    public boolean contains(Bitmap bitmap) {
        for (GvImage image : this) {
            if (image.getBitmap().sameAs(bitmap)) return true;
        }

        return false;
    }

//Classes

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdImageList.MdImage}
     * {@link ViewHolder}: {@link VhImage}
     */
    public static class GvImage extends GvDataBasic<GvImage> implements DataImage {
        public static final GvDataType<GvImage> TYPE = new GvDataType<>(GvImage.class, "image");
        public static final Parcelable.Creator<GvImage> CREATOR = new Parcelable
                .Creator<GvImage>() {
            //Overridden
            public GvImage createFromParcel(Parcel in) {
                return new GvImage(in);
            }

            public GvImage[] newArray(int size) {
                return new GvImage[size];
            }
        };

        private final Bitmap mBitmap;
        private final Date mDate;
        private final LatLng mLatLng;
        private String mCaption;
        private boolean mIsCover = false;

        //Constructors
        public GvImage() {
            super(GvImage.TYPE);
            mBitmap = null;
            mDate = null;
            mLatLng = null;
        }

        public GvImage(Bitmap bitmap, Date date, LatLng latLng, String caption, boolean isCover) {
            super(GvImage.TYPE);
            mBitmap = bitmap;
            mDate = date;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        public GvImage(GvReviewId id, Bitmap bitmap, Date date, String caption, boolean isCover) {
            super(GvImage.TYPE, id);
            mBitmap = bitmap;
            mDate = date;
            mCaption = caption;
            mLatLng = null;
            mIsCover = isCover;
        }

        public GvImage(GvImage image) {
            this(image.getReviewId(), image.getBitmap(), image.getDate(), image.getCaption(),
                    image.isCover());
        }

        GvImage(Parcel in) {
            super(in);
            mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
            mCaption = in.readString();
            mLatLng = in.readParcelable(LatLng.class.getClassLoader());
            mIsCover = in.readByte() != 0;
            mDate = (Date) in.readSerializable();
        }

        //public methods
        public LatLng getLatLng() {
            return mLatLng;
        }

        public void setIsCover(boolean isCover) {
            mIsCover = isCover;
        }

        //Overridden
        @Override
        public ViewHolder getViewHolder() {
            return new VhImage();
        }

        @Override
        public boolean isValidForDisplay() {
            return mBitmap != null;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return dataValidator.validate(this);
        }

        @Override
        public String getStringSummary() {
            return mCaption != null ? TYPE.getDatumName() + ": " + mCaption : TYPE.getDatumName();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(mBitmap, i);
            parcel.writeString(mCaption);
            parcel.writeParcelable(mLatLng, i);
            parcel.writeByte((byte) (isCover() ? 1 : 0));
            parcel.writeSerializable(mDate);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvImage)) return false;
            if (!super.equals(o)) return false;

            GvImage gvImage = (GvImage) o;

            if (mIsCover != gvImage.mIsCover) return false;
            if (mBitmap != null ? !mBitmap.sameAs(gvImage.mBitmap) : gvImage.mBitmap != null)
                return false;
            if (mDate != null ? !mDate.equals(gvImage.mDate) : gvImage.mDate != null) return false;
            if (mLatLng != null ? !mLatLng.equals(gvImage.mLatLng) : gvImage.mLatLng != null)
                return false;
            return !(mCaption != null ? !mCaption.equals(gvImage.mCaption) : gvImage.mCaption !=
                    null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mBitmap != null ? mBitmap.hashCode() : 0);
            result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
            result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
            result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
            result = 31 * result + (mIsCover ? 1 : 0);
            return result;
        }

        @Override
        public Bitmap getBitmap() {
            return mBitmap;
        }

        @Override
        public Date getDate() {
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
    }
}
