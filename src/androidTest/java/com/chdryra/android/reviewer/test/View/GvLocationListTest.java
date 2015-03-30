/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvLocationListTest extends TestCase {
    private static final int NUM = 50;
    private GvLocationList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvLocationList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        GvDataParcelableTester.testParcelable(GvDataMocker.newLocation(false));
        GvDataParcelableTester.testParcelable(GvDataMocker.newLocation(true));
        GvDataParcelableTester.testParcelable(GvDataMocker.newLocationList(10, false));
        GvDataParcelableTester.testParcelable(GvDataMocker.newLocationList(10, true));
    }

    @SmallTest
    public void testGvLocation() {
        GvLocationList.GvLocation location1 = GvDataMocker.newLocation(false);
        GvLocationList.GvLocation location2 = GvDataMocker.newLocation(false);

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
        String firstBit = RandomString.nextWord();
        String name = firstBit;
        Random rand = new Random();
        for (int i = 0; i < 5; ++i) {
            int d = rand.nextInt(delimiters.length());
            char delimiter = delimiters.toCharArray()[d];
            name = name + delimiter + RandomString.nextWord();
        }

        LatLng latLng = RandomLatLng.nextLatLng();
        GvLocationList.GvLocation location = new GvLocationList.GvLocation(latLng, name);
        String shortened = location.getShortenedName();
        assertEquals(firstBit, shortened);
    }

    @SmallTest
    public void testEquals() {
        mList.add(GvDataMocker.newLocationList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvLocationList list = new GvLocationList();
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        list.add(mList);
        assertFalse(mList.equals(list));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvLocationList();
    }
}
