/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.Utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocatedPlace {
    private static final String SEPARATOR = ":";

    private final LatLng mLatLng;
    private final String mDescription;
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

    //public methods
    public LatLng getLatLng() {
        return mLatLng;
    }

    public String getDescription() {
        return mDescription;
    }

    public LocationId getId() {
        return mId;
    }

    private String generateId() {
        return String.valueOf(mLatLng.hashCode()) + SEPARATOR + String.valueOf(mDescription
                .hashCode());
    }

//Overridden
    @Override
    public int hashCode() {
        int result = mLatLng.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

    //Classes
    public static class LocationId {
        private LocationProvider mProvider;
        private String mId;

        //Constructors
        public LocationId(LocationProvider provider, String providerId) {
            mProvider = provider;
            mId = providerId;
        }

        //public methods
        public LocationProvider getProvider() {
            return mProvider;
        }

        public String getId() {
            return mId;
        }

        //Overridden
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
    }
}
