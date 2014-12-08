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

public class GVLocationList extends GVReviewDataList<GVLocationList.GVLocation> {

    GVLocationList() {
        super(GVType.LOCATIONS);
    }

    void add(LatLng latLng, String name) {
        add(new GVLocation(latLng, name));
    }

    /**
     * {@link GVReviewData} version of: {@link RDLocationList.RDLocation}
     * {@link ViewHolder}: {@link VHLocation}
     */
    public static class GVLocation implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVLocation> CREATOR = new Parcelable
                .Creator<GVLocation>() {
            public GVLocation createFromParcel(Parcel in) {
                return new GVLocation(in);
            }

            public GVLocation[] newArray(int size) {
                return new GVLocation[size];
            }
        };
        private final LatLng mLatLng;
        private final String mName;

        GVLocation(LatLng latLng, String name) {
            mLatLng = latLng;
            mName = name;
        }

        GVLocation(Parcel in) {
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
            if (!(o instanceof GVLocation)) return false;

            GVLocation that = (GVLocation) o;

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

        public LatLng getLatLng() {
            return mLatLng;
        }

        public String getName() {
            return mName;
        }

        String getShortenedName() {
            if (mName != null) {
                StringTokenizer tokens = new StringTokenizer(mName,
                        RDLocationList.RDLocation.LOCATION_DELIMITER);
                String shortened = tokens.nextToken();
                return shortened != null ? shortened.trim() : null;
            } else {
                return null;
            }
        }
    }
}
