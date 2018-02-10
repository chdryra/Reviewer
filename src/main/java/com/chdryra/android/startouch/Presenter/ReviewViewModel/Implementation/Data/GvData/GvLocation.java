/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.LocationServices.LocationProvider;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdLocationList.MdLocation}
 * {@link ViewHolder}: {@link VhLocation}
 */
public class GvLocation extends GvDataParcelableBasic<GvLocation> implements DataLocation {
    public static final GvDataType<GvLocation> TYPE
            = new GvDataType<>(GvLocation.class, TYPE_NAME);

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
    private final String mAddress;
    private final LocationId mLocationId;

    public GvLocation() {
        this(null, new LatLng(0,0), "", "", LocationId.nullId());
    }

    public GvLocation(LatLng latLng, String name, String address, LocationId locationId) {
        this(null, latLng, name, address, locationId);
    }

    public GvLocation(@Nullable GvReviewId id, LatLng latLng, String name, String address,
                      LocationId locationId) {
        super(GvLocation.TYPE, id);
        mLatLng = latLng;
        mName = name;
        mAddress = address;
        mLocationId = locationId;
    }

    public GvLocation(GvLocation location) {
        this(location.getGvReviewId(), location.getLatLng(), location.getName(), location.getAddress(), location.getLocationId());
    }

    public GvLocation(Parcel in) {
        super(in);
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        mName = in.readString();
        mAddress = in.readString();
        mLocationId = new LocationId(new LocationProvider(in.readString()), in.readString());
    }

    @Override
    public String getShortenedName() {
        return DataFormatter.getShortenedName(mName);
    }

    @Override
    public String getAddress() {
        return mAddress;
    }

    @Override
    public LocationId getLocationId() {
        return mLocationId;
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
    public String toString() {
        return StringParser.parse(this);
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
        parcel.writeString(mAddress);
        parcel.writeString(mLocationId.getProvider().getProviderName());
        parcel.writeString(mLocationId.getId());
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
                    return new GvLocation(new LatLng(0,0), placeHolder, "address", LocationId.nullId());
                }
            });
        }
    }
}
