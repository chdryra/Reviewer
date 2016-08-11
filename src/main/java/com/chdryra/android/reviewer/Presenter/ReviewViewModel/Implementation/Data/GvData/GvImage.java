/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhImage;
import com.google.android.gms.maps.model.LatLng;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdImageList.MdImage}
 * {@link ViewHolder}: {@link VhImage}
 */
public class GvImage extends GvDataParcelableBasic<GvImage> implements DataImage {
    public static final GvDataType<GvImage> TYPE = new GvDataType<>(GvImage.class, "image");
    public static final Creator<GvImage> CREATOR = new Creator<GvImage>() {
        @Override
        public GvImage createFromParcel(Parcel in) {
            return new GvImage(in);
        }

        @Override
        public GvImage[] newArray(int size) {
            return new GvImage[size];
        }
    };

    private final Bitmap mBitmap;
    private final GvDate mDate;
    private final LatLng mLatLng;
    private String mCaption;
    private boolean mIsCover = false;

    //Constructors
    public GvImage() {
        super(GvImage.TYPE);
        mBitmap = null;
        mDate = new GvDate();
        mLatLng = null;
    }

    public GvImage(Bitmap bitmap, GvDate date, LatLng latLng, String caption, boolean isCover) {
        super(GvImage.TYPE);
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mLatLng = latLng;
        mIsCover = isCover;
    }

    public GvImage(@Nullable GvReviewId id, Bitmap bitmap, GvDate date, String caption, boolean isCover) {
        super(GvImage.TYPE, id);
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mLatLng = null;
        mIsCover = isCover;
    }

    public GvImage(GvImage image) {
        this(image.getGvReviewId(), image.getBitmap(), image.getDate(), image.getCaption(),
                image.isCover());
    }

    public GvImage(Parcel in) {
        super(in);
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        mCaption = in.readString();
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        mIsCover = in.readByte() != 0;
        mDate = in.readParcelable(GvDate.class.getClassLoader());
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
        parcel.writeParcelable(mDate, i);
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
    public GvDate getDate() {
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

    public static class Reference extends GvDataRef<Reference, DataImage, VhImage> {
        public static final GvDataType<GvImage.Reference> TYPE
                = new GvDataType<>(GvImage.Reference.class, GvImage.TYPE);

        public Reference(ReviewItemReference<DataImage> reference,
                         DataConverter<DataImage, GvImage, ?> converter) {
            super(TYPE, reference, converter, VhImage.class, new PlaceHolderFactory<DataImage>() {
                @Override
                public DataImage newPlaceHolder(String placeHolder) {
                    return new GvImage();
                }
            });
        }
    }
}
