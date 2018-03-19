/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.LocationServices.LocatedPlace;
import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.LocationServices.LocationProvider;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullLocatedPlace implements LocatedPlace {
    private static final LocationId NULL_ID = new LocationId(new LocationProvider("Null"), null);

    @Override
    public LatLng getLatLng() {
        return new LatLng(0, 0);
    }

    @Override
    public String getDescription() {
        return "Null";
    }

    @Override
    public LocationId getId() {
        return NULL_ID;
    }
}
