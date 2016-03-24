/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LatitudeLongitude {
    private double mLatitude;
    private double mLongitude;

    public LatitudeLongitude() {
    }

    public LatitudeLongitude(LatLng latLng) {
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
    }

    double getLatitude() {
        return mLatitude;
    }

    double getLongitude() {
        return mLongitude;
    }

    public boolean isValid() {
        return mLatitude >= -90. && mLatitude <= 90.
                && mLongitude >= -180. && mLongitude <= 180.;
    }
}
