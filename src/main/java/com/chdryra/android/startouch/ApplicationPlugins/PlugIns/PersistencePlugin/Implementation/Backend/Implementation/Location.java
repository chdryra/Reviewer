/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Location {
    private LatitudeLongitude latLng;
    private String name;
    private String address;
    private LocId locationId;

    public Location() {
    }

    public Location(DataLocation location) {
        latLng = new LatitudeLongitude(location.getLatLng());
        name = location.getName();
        address = location.getAddress();
        locationId = new LocId(location.getLocationId());
    }

    public static int size() {
        return 1 + LatitudeLongitude.size() + 1 + LocId.size();
    }

    public LatitudeLongitude getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocId getLocationId() {
        return locationId;
    }
}
