/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.LatitudeLongitude;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.LocId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Location;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.LocationServices.LocationProvider;
import com.firebase.client.DataSnapshot;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterLocation implements ReviewItemConverter<DataLocation> {
    @Override
    public DataLocation convert(ReviewId id, DataSnapshot snapshot) {
        Location value = snapshot.getValue(Location.class);
        if(value != null) {
            LatitudeLongitude latLng = value.getLatLng();
            LocId locId = value.getLocationId();
            return new DatumLocation(id, new LatLng(latLng.getLatitude(), latLng.getLongitude()),
                            value.getName(), value.getAddress(),
                    new LocationId(new LocationProvider(locId.getProvider()), locId.getLocationId()));
        } else {
            return null;
        }
    }
}
