package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetails;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationId;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetailsProvider;

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
