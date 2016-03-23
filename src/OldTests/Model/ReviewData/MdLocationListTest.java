/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdLocationListTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testMdLocationHasData() {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();

        MdLocation location = new MdLocation(ID, null, null);
        assertFalse(location.hasData());
        location = new MdLocation(ID, null, "");
        assertFalse(location.hasData());
        location = new MdLocation(ID, null, "");
        assertFalse(location.hasData());
        location = new MdLocation(ID, latLng, "");
        assertFalse(location.hasData());
        location = new MdLocation(ID, latLng, name);
        assertTrue(location.hasData());
    }

    @SmallTest
    public void testMdLocationGetters() {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();
        MdLocation location = new MdLocation(ID, latLng, name);
        assertEquals(latLng, location.getLatLng());
        assertEquals(name, location.getName());
        assertEquals(ID, location.getReviewId());
    }

    @SmallTest
    public void testMdLocationEqualsHash() {
        LatLng latLng1 = RandomLatLng.nextLatLng();
        String name1 = RandomString.nextWord();
        LatLng latLng2 = RandomLatLng.nextLatLng();
        String name2 = RandomString.nextWord();
        MdReviewId id2 = RandomReviewId.nextId();

        MdLocation location1 = new MdLocation(ID, latLng1, name1);

        MdLocation location2;
        location2 = new MdLocation(ID, latLng1, name2);
        MdDataUtils.testEqualsHash(location1, location2, false);
        location2 = new MdLocation(ID, latLng2, name1);
        MdDataUtils.testEqualsHash(location1, location2, false);
        location2 = new MdLocation(id2, latLng1, name1);
        MdDataUtils.testEqualsHash(location1, location2, false);
        location2 = new MdLocation(ID, latLng1, name1);
        MdDataUtils.testEqualsHash(location1, location2, true);
    }
}
