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

import java.util.Comparator;
import java.util.Date;
import java.util.Random;

/**
 * Includes methods for adding captions and getting images designated as "covers" which can be
 * used as a background image for a review.
 */
public class GvImageList extends GvDataList<GvImageList.GvImage> {
    public static final GvDataType TYPE = new GvDataType("image");
    public static final Class<GvImage> DATA_CLASS = GvImage.class;

    public GvImageList() {
        super(null, GvImage.class, TYPE);
    }

    public GvImageList(GvReviewId id) {
        super(id, GvImage.class, TYPE);
    }

    public GvImageList(GvImageList data) {
        super(data);
    }

    public boolean contains(Bitmap bitmap) {
        for (GvImage image : this) {
            if (image.getBitmap().sameAs(bitmap)) return true;
        }

        return false;
    }

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

    @Override
    protected Comparator<GvImage> getDefaultComparator() {
        return new Comparator<GvImage>() {
            @Override
            public int compare(GvImage lhs, GvImage rhs) {
                int comp = 0;
                if (contains(lhs) && contains(rhs)) {
                    if (lhs.isCover() && !rhs.isCover()) {
                        comp = -1;
                    } else if (!lhs.isCover() && rhs.isCover()) {
                        comp = 1;
                    } else {
                        if (lhs.getDate().after(rhs.getDate())) {
                            comp = -1;
                        } else if (lhs.getDate().before(rhs.getDate())) {
                            comp = 1;
                        }
                    }
                }

                return comp;
            }
        };
    }

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdImageList.MdImage}
     * {@link ViewHolder}: {@link VhImage}
     */
    public static class GvImage extends GvDataBasic implements DataImage {
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
        private final Date   mDate;
        private final LatLng mLatLng;
        private       String mCaption;
        private boolean mIsCover = false;

        public GvImage() {
            super(TYPE);
            mBitmap = null;
            mDate = null;
            mLatLng = null;
        }

        public GvImage(Bitmap bitmap, Date date, LatLng latLng, String caption, boolean isCover) {
            super(TYPE);
            mBitmap = bitmap;
            mDate = date;
            mCaption = caption;
            mLatLng = latLng;
            mIsCover = isCover;
        }

        public GvImage(GvReviewId id, Bitmap bitmap, Date date, String caption, boolean isCover) {
            super(TYPE, id);
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

        @Override
        public ViewHolder getViewHolder() {
            return new VhImage();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getStringSummary() {
            return mCaption != null ? TYPE.getDatumName() + ": " + mCaption : TYPE.getDatumName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvImage)) return false;

            GvImage gvImage = (GvImage) o;

            if (mBitmap != null ? !mBitmap.sameAs(gvImage.mBitmap) : gvImage.mBitmap != null) {
                return false;
            }
            if (mCaption != null ? !mCaption.equals(gvImage.mCaption) : gvImage.mCaption != null) {
                return false;
            }
            if (mLatLng != null ? !mLatLng.equals(gvImage.mLatLng) : gvImage.mLatLng != null) {
                return false;
            }
            if (mDate != null ? !mDate.equals(gvImage.mDate) : gvImage.mDate != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mBitmap != null ? mBitmap.hashCode() : 0;
            result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
            result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
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
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(mBitmap, i);
            parcel.writeString(mCaption);
            parcel.writeParcelable(mLatLng, i);
            parcel.writeByte((byte) (isCover() ? 1 : 0));
            parcel.writeSerializable(mDate);
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

        public LatLng getLatLng() {
            return mLatLng;
        }

        public void setIsCover(boolean isCover) {
            mIsCover = isCover;
        }
    }
}