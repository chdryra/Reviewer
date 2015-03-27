/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocatedPlace {
    private static final String SEPARATOR = "-";

    private final LatLng     mLatLng;
    private final String     mDescription;
    private final LocationId mId;

    public enum Provider {GOOGLE, USER}

    public LocatedPlace(LatLng latLng, String description, LocationId id) {
        mLatLng = latLng;
        mDescription = description;
        mId = id;
    }

    public LocatedPlace(LatLng latLng, String description) {
        mLatLng = latLng;
        mDescription = description;
        mId = new LocationId(Provider.USER, generateId());
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

    public boolean isValid() {
        return mLatLng != null && DataValidator.validateString(mDescription) && DataValidator
                .validateString(mId.getId());
    }

    private String generateId() {
        return mLatLng.hashCode() + SEPARATOR + mDescription.hashCode();
    }

    public static class LocationId {
        private Provider mProvider;
        private String   mId;

        public LocationId(Provider provider, String id) {
            mProvider = provider;
            mId = id;
        }

        public Provider getProvider() {
            return mProvider;
        }

        public String getId() {
            return mId;
        }
    }
}
