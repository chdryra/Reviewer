/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Location {
    private LatitudeLongitude latLng;
    private String name;

    public Location() {
    }

    public Location(DataLocation location) {
        latLng = new LatitudeLongitude(location.getLatLng());
        name = location.getName();
    }

    public LatitudeLongitude getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
