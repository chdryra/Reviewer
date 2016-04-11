/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LatLngMidpoint;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.mygenerallibrary.LocationUtils.LatLngDistance;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorLocationsTest extends AggregatedDistinctItemsTest<DataLocation>{
    @NonNull
    @Override
    protected DataAggregator<DataLocation> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newLocationsAggregator(params.getSimilarLocation());
    }

    @Override
    @NonNull
    protected DataLocation newSimilarDatum(ReviewId reviewId, DataLocation template) {
        LatLng latLng = template.getLatLng();
        double lat = latLng.latitude + (getRAND().nextDouble() * 2 - 1) * 1e-5;
        double lng = latLng.longitude + (getRAND().nextDouble() * 2 - 1) * 1e-5;
        return new DatumLocation(reviewId, new LatLng(lat, lng), template.getName());
    }

    @Override
    @NonNull
    protected DataLocation randomDatum() {
        return new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    @Override
    protected DataLocation getExampleCanonical(ReviewId id, ArrayList<DataLocation> data) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for(DataLocation location : data) {
            latLngs.add(location.getLatLng());
        }
        LatLngMidpoint midpoint = new LatLngMidpoint();
        LatLng mid = midpoint.getGeoMidpoint(latLngs);
        return new DatumLocation(id, mid, data.get(0).getName());
    }

    @Override
    protected void checkCanonicalItemsSize(DataLocation canonical, int size) {
        DataLocation keyEquivalent = findKey(canonical);
        Assert.assertNotNull(keyEquivalent);
        super.checkCanonicalItemsSize(keyEquivalent, size);
    }
    
    @Override
    protected void checkCanonicalInMap(DataLocation canonical) {
        DataLocation keyEquivalent = findKey(canonical);
        assertThat(keyEquivalent, not(nullValue()));
    }

    //because key dependent on calculated float value that might differ by eps
    @Nullable
    private DataLocation findKey(DataLocation canonical) {
        LatLng latlng = canonical.getLatLng();
        DataLocation keyEquivalent = null;
        float[] res = new float[1];
        for(DataLocation key : mCanonicalsMap.keySet()) {
            LatLng keyll = key.getLatLng();

            LatLngDistance.distanceBetween(latlng.latitude, latlng.longitude, keyll.latitude, keyll.longitude, res);

            if(res[0] < 1e-5) keyEquivalent = key;
        }
        return keyEquivalent;
    }
}
