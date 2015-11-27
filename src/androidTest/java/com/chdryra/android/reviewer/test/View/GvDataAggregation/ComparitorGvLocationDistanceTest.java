/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.location.Location;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvLocationDistance;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceFloat;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationDistanceTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvLocation loc1 = GvDataMocker.newLocation(null);
        GvLocation loc2 = GvDataMocker.newLocation(null);
        LatLng ll1 = loc1.getLatLng();
        LatLng ll2 = loc2.getLatLng();
        double maxLatitude = Math.max(ll1.latitude, ll2.latitude);
        double minLatitude = Math.min(ll1.latitude, ll2.latitude);
        double maxLongitude = Math.max(ll1.longitude, ll2.longitude);
        double minLongitude = Math.min(ll1.longitude, ll2.longitude);
        double midLatitude = minLatitude + (maxLatitude - minLatitude) / 2.0;
        double midLongitude = minLongitude + (maxLongitude - minLongitude) / 2.0;
        LatLng ll3 = new LatLng(midLatitude, midLongitude);

        //Check distances make sense
        float[] res = new float[1];
        Location.distanceBetween(ll1.latitude, ll1.longitude, ll2.latitude, ll2.longitude, res);
        DifferenceFloat maxDistance = new DifferenceFloat(res[0]);
        DifferenceFloat zeroDistance = new DifferenceFloat(0f);
        Location.distanceBetween(ll1.latitude, ll1.longitude, ll3.latitude, ll3.longitude, res);
        DifferenceFloat midDistance = new DifferenceFloat(res[0]);
        assertTrue(midDistance.lessThanOrEqualTo(maxDistance));
        assertTrue(zeroDistance.lessThanOrEqualTo(midDistance));

        ComparitorGvLocationDistance comparitor = new ComparitorGvLocationDistance();

        //Check same location = zero distance
        GvLocation lhs = new GvLocation(loc1);
        GvLocation rhs = new GvLocation(loc1);
        DifferenceFloat difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(zeroDistance));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(zeroDistance));

        //Check different location at correct distance
        rhs = new GvLocation(loc2);
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertFalse(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertFalse(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));

        //Check name doesn't affect result
        rhs = new GvLocation(ll2, loc1.getName());
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertFalse(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertFalse(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));

        //Check midpoint at correct distance
        rhs = new GvLocation(ll3, loc1.getName());
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertTrue(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(zeroDistance));
        assertTrue(difference.lessThanOrEqualTo(midDistance));
        assertTrue(difference.lessThanOrEqualTo(maxDistance));
    }
}
