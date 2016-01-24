/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsProviderGp implements LocationDetailsProvider {
    @Override
    public LocationDetails fetchDetails(LocationId id) {
        GpPlaceDetailsResult result =  PlacesApi.fetchDetails(id.getId());
        return new LocationDetailsImpl(result.getGeometry().getLatLng(), result.getName().getString());
    }

}
