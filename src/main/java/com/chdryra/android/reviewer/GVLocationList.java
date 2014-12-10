/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

import java.util.StringTokenizer;

public class GVLocationList extends GVDataList<GVLocationList.GvLocation> {

    GVLocationList() {
        super(GvType.LOCATIONS);
    }

    void add(LatLng latLng, String name) {
        add(new GvLocation(latLng, name));
    }

    /**
     * {@link GVDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdLocationList.MdLocation}
     * {@link ViewHolder}: {@link VHLocation}
     */
    public static class GvLocation implements GVDataList.GvData, DataLocation {
        public static final Parcelable.Creator<GvLocation> CREATOR = new Parcelable
                .Creator<GvLocation>() {
            public GvLocation createFromParcel(Parcel in) {
                return new GvLocation(in);
            }

            public GvLocation[] newArray(int size) {
                return new GvLocation[size];
            }
        };
        private final LatLng mLatLng;
        private final String mName;

        GvLocation(LatLng latLng, String name) {
            mLatLng = latLng;
            mName = name;
        }

        GvLocation(Parcel in) {
            mLatLng = in.readParcelable(LatLng.class.getClassLoader());
            mName = in.readString();
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHLocation(false);
        }

        @Override
        public boolean isValidForDisplay() {
            return mLatLng != null && mName != null && mName.length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvLocation)) return false;

            GvLocation that = (GvLocation) o;

            return !(mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null) && !
                    (mName != null ? !mName.equals(that.mName) : that.mName != null);

        }

        @Override
        public int hashCode() {
            int result = mLatLng != null ? mLatLng.hashCode() : 0;
            result = 31 * result + (mName != null ? mName.hashCode() : 0);
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(mLatLng, i);
            parcel.writeString(mName);
        }

        @Override
        public LatLng getLatLng() {
            return mLatLng;
        }

        @Override
        public String getName() {
            return mName;
        }

        String getShortenedName() {
            if (mName != null) {
                StringTokenizer tokens = new StringTokenizer(mName,
                        MdLocationList.MdLocation.LOCATION_DELIMITER);
                String shortened = tokens.nextToken();
                return shortened != null ? shortened.trim() : null;
            } else {
                return null;
            }
        }
    }
}
