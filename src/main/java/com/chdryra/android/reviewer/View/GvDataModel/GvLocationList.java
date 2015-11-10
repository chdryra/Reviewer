/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.google.android.gms.maps.model.LatLng;

import java.util.StringTokenizer;

public class GvLocationList extends GvDataList<GvLocationList.GvLocation> {
    public static final Parcelable.Creator<GvLocationList> CREATOR = new Parcelable
            .Creator<GvLocationList>() {
        //Overridden
        public GvLocationList createFromParcel(Parcel in) {
            return new GvLocationList(in);
        }

        public GvLocationList[] newArray(int size) {
            return new GvLocationList[size];
        }
    };

    //Constructors
    public GvLocationList() {
        super(GvLocation.TYPE, null);
    }

    public GvLocationList(Parcel in) {
        super(in);
    }

    public GvLocationList(GvReviewId id) {
        super(GvLocation.TYPE, id);
    }

    public GvLocationList(GvLocationList data) {
        super(data);
    }

//Classes

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdLocationList.MdLocation}
     * {@link ViewHolder}: {@link VhLocation}
     */
    public static class GvLocation extends GvDataBasic<GvLocation> implements DataLocation {
        public static final GvDataType<GvLocation> TYPE = new GvDataType<>(GvLocation.class,
                "location");
        public static final Parcelable.Creator<GvLocation> CREATOR = new Parcelable
                .Creator<GvLocation>() {
            //Overridden
            public GvLocation createFromParcel(Parcel in) {
                return new GvLocation(in);
            }

            public GvLocation[] newArray(int size) {
                return new GvLocation[size];
            }
        };
        private final LatLng mLatLng;
        private final String mName;

        //Constructors
        public GvLocation() {
            this(null, null, null);
        }

        public GvLocation(LatLng latLng, String name) {
            this(null, latLng, name);
        }

        public GvLocation(GvReviewId id, LatLng latLng, String name) {
            super(GvLocation.TYPE, id);
            mLatLng = latLng;
            mName = name;
        }

        public GvLocation(GvLocation location) {
            this(location.getReviewId(), location.getLatLng(), location.getName());
        }

        GvLocation(Parcel in) {
            super(in);
            mLatLng = in.readParcelable(LatLng.class.getClassLoader());
            mName = in.readString();
        }

        //public methods
        public String getShortenedName() {
            String shortened = mName;
            if (mName != null) {
                StringTokenizer tokens = new StringTokenizer(mName,
                        MdLocationList.MdLocation.LOCATION_DELIMITER);
                shortened = tokens.nextToken();
            }

            return shortened != null ? shortened.trim() : mName;
        }

        //Overridden
        @Override
        public ViewHolder getViewHolder() {
            return new VhLocation(false);
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
            if (!super.equals(o)) return false;

            GvLocation that = (GvLocation) o;

            if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null)
                return false;
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
    }
}
