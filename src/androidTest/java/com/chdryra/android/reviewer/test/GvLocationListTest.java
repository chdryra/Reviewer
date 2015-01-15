/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.LatLngMocker;
import com.chdryra.android.testutils.RandomStringGenerator;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvLocationListTest extends TestCase {
    private GvLocationList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvLocationList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testGvLocation() {
        GvLocationList.GvLocation location1 = GvDataMocker.newLocation();
        GvLocationList.GvLocation location2 = GvDataMocker.newLocation();

        LatLng latLng1 = location1.getLatLng();
        String name1 = location1.getName();
        LatLng latLng2 = location2.getLatLng();
        String name2 = location2.getName();

        GvLocationList.GvLocation gvLocation = new GvLocationList.GvLocation(latLng1, name1);
        GvLocationList.GvLocation gvLocationEquals = new GvLocationList.GvLocation(latLng1,
                name1);
        GvLocationList.GvLocation gvLocationNotEquals1 = new GvLocationList.GvLocation
                (latLng1, name2);
        GvLocationList.GvLocation gvLocationNotEquals2 = new GvLocationList.GvLocation
                (latLng2, name1);
        GvLocationList.GvLocation gvLocationNotEquals3 = new GvLocationList.GvLocation
                (latLng2, name2);
        GvLocationList.GvLocation gvLocationNull = new GvLocationList.GvLocation();
        GvLocationList.GvLocation gvLocationEmpty1 = new GvLocationList.GvLocation(latLng1, "");
        GvLocationList.GvLocation gvLocationEmpty2 = new GvLocationList.GvLocation(null, name1);
        GvLocationList.GvLocation gvLocationEmpty3 = new GvLocationList.GvLocation(null, "");

        assertNotNull(gvLocation.newViewHolder());
        assertTrue(gvLocation.isValidForDisplay());

        assertEquals(latLng1, gvLocation.getLatLng());
        assertEquals(name1, gvLocation.getName());

        assertTrue(gvLocation.equals(gvLocationEquals));
        assertFalse(gvLocation.equals(gvLocationNotEquals1));
        assertFalse(gvLocation.equals(gvLocationNotEquals2));
        assertFalse(gvLocation.equals(gvLocationNotEquals3));

        assertFalse(gvLocationNull.isValidForDisplay());
        assertFalse(gvLocationEmpty1.isValidForDisplay());
        assertFalse(gvLocationEmpty2.isValidForDisplay());
        assertFalse(gvLocationEmpty3.isValidForDisplay());
    }

    @SmallTest
    public void testGvLocationGetShortenedName() {
        String delimiters = MdLocationList.MdLocation.LOCATION_DELIMITER;
        String firstBit = RandomStringGenerator.nextWord();
        String name = firstBit;
        Random rand = new Random();
        for (int i = 0; i < 5; ++i) {
            int d = rand.nextInt(delimiters.length());
            char delimiter = delimiters.toCharArray()[d];
            name = name + delimiter + RandomStringGenerator.nextWord();
        }

        LatLng latLng = LatLngMocker.newLatLng();
        GvLocationList.GvLocation location = new GvLocationList.GvLocation(latLng, name);
        String shortened = location.getShortenedName();
        assertEquals(firstBit, shortened);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvLocationList();
    }
}
