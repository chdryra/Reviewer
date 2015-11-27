/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvLocationName;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferencePercentage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.testutils.RandomLatLng;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationNameTest extends TestCase {
    @SmallTest
    public void testCompare() {
        LatLng lhsLatLng = RandomLatLng.nextLatLng();
        LatLng rhsLatLng = RandomLatLng.nextLatLng();
        String lhsName = "kitten";
        String rhsName = "sitting";

        ComparitorGvLocationName comparitor = new ComparitorGvLocationName();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        GvLocation lhs = new GvLocation(lhsLatLng, lhsName);
        GvLocation rhs = new GvLocation(lhsLatLng, lhsName);
        DifferencePercentage difference = comparitor.compare(lhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvLocation(rhsLatLng, lhsName);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvLocation(rhsLatLng, rhsName);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
    }
}
