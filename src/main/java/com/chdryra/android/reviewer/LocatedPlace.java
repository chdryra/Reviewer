/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocatedPlace implements Parcelable {
    public static final Parcelable.Creator<LocatedPlace> CREATOR = new Parcelable
            .Creator<LocatedPlace>() {
        public LocatedPlace createFromParcel(Parcel in) {
            return new LocatedPlace(in);
        }

        public LocatedPlace[] newArray(int size) {
            return new LocatedPlace[size];
        }
    };

    private static final String SEPARATOR = "-";

    private final LatLng     mLatLng;
    private final String     mDescription;
    private final LocationId mId;

    public enum LocationProvider {GOOGLE, USER}

    public LocatedPlace(LatLng latLng, String description, LocationId id) {
        mLatLng = latLng;
        mDescription = description;
        mId = id;
    }

    public LocatedPlace(LatLng latLng, String description) {
        mLatLng = latLng;
        mDescription = description;
        mId = new LocationId(LocationProvider.USER, generateId());
    }

    public LocatedPlace(Parcel in) {
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        mDescription = in.readString();
        mId = in.readParcelable(LocationId.class.getClassLoader());
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public String getDescription() {
        return mDescription;
    }

    public LocationId getId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocatedPlace)) return false;

        LocatedPlace that = (LocatedPlace) o;

        if (!mDescription.equals(that.mDescription)) return false;
        if (!mId.equals(that.mId)) return false;
        if (!mLatLng.equals(that.mLatLng)) return false;

        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private String generateId() {
        return mLatLng.hashCode() + SEPARATOR + mDescription.hashCode();
    }

    @Override
    public int hashCode() {
        int result = mLatLng.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

    public static class LocationId implements Parcelable {
        public static final Parcelable.Creator<LocationId> CREATOR = new Parcelable
                .Creator<LocationId>() {
            public LocationId createFromParcel(Parcel in) {
                return new LocationId(in);
            }

            public LocationId[] newArray(int size) {
                return new LocationId[size];
            }
        };

        private LocationProvider mProvider;
        private String           mId;

        public LocationId(LocationProvider provider, String providerId) {
            mProvider = provider;
            mId = providerId;
        }

        public LocationId(Parcel in) {
            mProvider = (LocationProvider) in.readSerializable();
            mId = in.readString();
        }

        public LocationProvider getProvider() {
            return mProvider;
        }

        public String getId() {
            return mId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LocationId)) return false;

            LocationId that = (LocationId) o;

            if (!mId.equals(that.mId)) return false;
            if (mProvider != that.mProvider) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = mProvider.hashCode();
            result = 31 * result + mId.hashCode();
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable(mProvider);
            parcel.writeString(mId);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mLatLng, i);
        parcel.writeString(mDescription);
        parcel.writeParcelable(mId, i);
    }


}
