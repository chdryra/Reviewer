/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
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
        assertEquals(GvLocation.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newLocation(null));
        ParcelableTester.testParcelable(GvDataMocker.newLocation(RandomReviewId
                .nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newLocationList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newLocationList(10, true));
    }

    @SmallTest
    public void testGvLocation() {
        GvLocation location1 = GvDataMocker.newLocation(null);
        GvLocation location2 = GvDataMocker.newLocation(null);

        LatLng latLng1 = location1.getLatLng();
        String name1 = location1.getName();
        LatLng latLng2 = location2.getLatLng();
        String name2 = location2.getName();

        GvLocation gvLocation = new GvLocation(latLng1, name1);
        GvLocation gvLocationEquals = new GvLocation(latLng1,
                name1);
        GvLocation gvLocationEquals2 = new GvLocation(gvLocation);
        GvLocation gvLocationNotEquals1 = new GvLocation
                (latLng1, name2);
        GvLocation gvLocationNotEquals2 = new GvLocation
                (latLng2, name1);
        GvLocation gvLocationNotEquals3 = new GvLocation
                (latLng2, name2);
        GvLocation gvLocationNotEquals4 = new GvLocation
                (RandomReviewId.nextGvReviewId(), latLng1, name1);
        GvLocation gvLocationNull = new GvLocation();
        GvLocation gvLocationEmpty1 = new GvLocation(latLng1, "");
        GvLocation gvLocationEmpty2 = new GvLocation(null, name1);
        GvLocation gvLocationEmpty3 = new GvLocation(null, "");

        assertNotNull(gvLocation.getViewHolder());
        assertTrue(gvLocation.isValidForDisplay());

        assertEquals(latLng1, gvLocation.getLatLng());
        assertEquals(name1, gvLocation.getName());

        assertTrue(gvLocation.equals(gvLocationEquals));
        assertTrue(gvLocation.equals(gvLocationEquals2));
        assertFalse(gvLocation.equals(gvLocationNotEquals1));
        assertFalse(gvLocation.equals(gvLocationNotEquals2));
        assertFalse(gvLocation.equals(gvLocationNotEquals3));
        assertFalse(gvLocation.equals(gvLocationNotEquals4));

        assertFalse(gvLocationNull.isValidForDisplay());
        assertFalse(gvLocationEmpty1.isValidForDisplay());
        assertFalse(gvLocationEmpty2.isValidForDisplay());
        assertFalse(gvLocationEmpty3.isValidForDisplay());
    }

    @SmallTest
    public void testGvLocationGetShortenedName() {
        String delimiters = MdLocation.LOCATION_DELIMITER;
        String firstBit = RandomString.nextWord();
        String name = firstBit;
        Random rand = new Random();
        for (int i = 0; i < 5; ++i) {
            int d = rand.nextInt(delimiters.length());
            char delimiter = delimiters.toCharArray()[d];
            name = name + delimiter + RandomString.nextWord();
        }

        LatLng latLng = RandomLatLng.nextLatLng();
        GvLocation location = new GvLocation(latLng, name);
        String shortened = location.getShortenedName();
        assertEquals(firstBit, shortened);
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newLocationList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvLocationList list = new GvLocationList();
        GvLocationList list2 = new GvLocationList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addAll(mList);
        list2.addAll(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvLocationList();
    }
}
