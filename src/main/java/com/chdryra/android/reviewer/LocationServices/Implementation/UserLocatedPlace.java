/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;


import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserLocatedPlace implements LocatedPlace {
    private static final String SEPARATOR = ":";
    private static final LocationProvider USER = new LocationProvider("User");

    private final LatLng mLatLng;
    private final String mDescription;
    private final LocationId mId;

    public UserLocatedPlace(LatLng latLng) {
        this(latLng, "");
    }

    public UserLocatedPlace(LatLng latLng, String description) {
        mLatLng = latLng;
        mDescription = description;
        mId = new LocationId(USER, generateId());
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public LocationId getId() {
        return mId;
    }

    private String generateId() {
        return String.valueOf(mLatLng.hashCode()) + SEPARATOR + String.valueOf(mDescription
                .hashCode());
    }

    @Override
    public int hashCode() {
        int result = mLatLng.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

}
