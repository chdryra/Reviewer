/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesGoogle;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Implementation.LocationProvider;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GooglePlace implements LocatedPlace {
    private static final String SEPARATOR = ":";
    private static final LocationProvider USER = new LocationProvider("User");

    private final LatLng mLatLng;
    private final String mDescription;
    private final LocationId mId;

    public GooglePlace(LatLng latLng) {
        this(latLng, "");
    }

    public GooglePlace(LatLng latLng, String description, String id) {
        mLatLng = latLng;
        mDescription = description;
        mId = new LocationId(LocationServicesGoogle.GOOGLE, id);
    }

    public GooglePlace(LatLng latLng, String description) {
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
