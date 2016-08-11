/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.StringTokenizer;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdLocationList.MdLocation}
 * {@link ViewHolder}: {@link VhLocation}
 */
public class GvLocation extends GvDataParcelableBasic<GvLocation> implements DataLocation {
    private static final String DELIMITERS = ",|";
    public static final GvDataType<GvLocation> TYPE = new GvDataType<>(GvLocation.class,
            "location");
    public static final Creator<GvLocation> CREATOR = new Creator<GvLocation>() {
        @Override
        public GvLocation createFromParcel(Parcel in) {
            return new GvLocation(in);
        }

        @Override
        public GvLocation[] newArray(int size) {
            return new GvLocation[size];
        }
    };
    private final LatLng mLatLng;
    private final String mName;

    public GvLocation() {
        this(null, null, null);
    }

    public GvLocation(LatLng latLng, String name) {
        this(null, latLng, name);
    }

    public GvLocation(@Nullable GvReviewId id, LatLng latLng, String name) {
        super(GvLocation.TYPE, id);
        mLatLng = latLng;
        mName = name;
    }

    public GvLocation(GvLocation location) {
        this(location.getGvReviewId(), location.getLatLng(), location.getName());
    }

    GvLocation(Parcel in) {
        super(in);
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        mName = in.readString();
    }

    @Nullable
    public String getShortenedName() {
        String shortened = mName;
        if (mName != null) {
            StringTokenizer tokens = new StringTokenizer(mName, DELIMITERS);
            shortened = tokens.nextToken();
        }

        return shortened != null ? shortened.trim() : mName;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhLocation();
    }

    @Override
    public boolean isValidForDisplay() {
        return mLatLng != null && mName != null && mName.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String getStringSummary() {
        return "@" + getShortenedName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(mLatLng, i);
        parcel.writeString(mName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvLocation)) return false;

        GvLocation that = (GvLocation) o;

        if (!super.equals(that)) return false;


        if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null) return false;
        return !(mName != null ? !mName.equals(that.mName) : that.mName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getName() {
        return mName;
    }

    public static class Reference extends GvDataRef<Reference, DataLocation, VhLocation> {
        public static final GvDataType<GvLocation.Reference> TYPE
                = new GvDataType<>(GvLocation.Reference.class, GvLocation.TYPE);

        public Reference(ReviewItemReference<DataLocation> reference,
                         DataConverter<DataLocation, GvLocation, ?> converter) {
            super(TYPE, reference, converter, VhLocation.class, new PlaceHolderFactory<DataLocation>() {
                @Override
                public DataLocation newPlaceHolder(String placeHolder) {
                    return new GvLocation(new LatLng(0,0), placeHolder);
                }
            });
        }
    }
}
