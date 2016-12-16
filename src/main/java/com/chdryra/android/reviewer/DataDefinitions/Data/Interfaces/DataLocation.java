/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataLocation extends HasReviewId, Validatable{
    String TYPE_NAME = "location";

    LatLng getLatLng();

    String getName();

    String getAddress();

    String getShortenedName();

    LocationId getLocationId();
}
