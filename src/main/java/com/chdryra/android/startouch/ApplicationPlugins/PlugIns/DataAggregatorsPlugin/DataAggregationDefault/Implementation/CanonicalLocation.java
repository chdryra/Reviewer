/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.LocationUtils.LatLngMidpoint;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.mygenerallibrary.Aggregation.ItemGetter;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocation extends CanonicalStringMaker<DataLocation> {
    @Override
    public DataLocation getCanonical(IdableList<? extends DataLocation> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullLocation(id);

        String name = getModeString(data);
        LatLng latLng = getMidLatLng(data);
        return new DatumLocation(id, latLng, name, "aggregate", LocationId.withProviderName(name, latLng));
    }

    private LatLng getMidLatLng(IdableList<? extends DataLocation> locations) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (DataLocation location : locations) {
            latLngs.add(location.getLatLng());
        }

        LatLngMidpoint midpoint = new LatLngMidpoint();
        return midpoint.getGeoMidpoint(latLngs);
    }

    @NonNull
    @Override
    protected ItemGetter<DataLocation, String> getStringGetter() {
        return new ItemGetter<DataLocation, String>() {
            @Override
            public String getItem(DataLocation datum) {
                return datum.getName();
            }
        };
    }
}
