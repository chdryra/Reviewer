/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Location {
    private LatitudeLongitude mLatLng;
    private String mName;

    public Location() {
    }

    public Location(DataLocation location) {
        mLatLng = new LatitudeLongitude(location.getLatLng());
        mName = location.getName();
    }

    LatitudeLongitude getLatLng() {
        return mLatLng;
    }

    public String getName() {
        return mName;
    }

    public boolean isValid() {
        return mLatLng.isValid() && mName != null && mName.length() > 0;
    }
}
