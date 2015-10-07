package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataSorting.LocationComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;
import com.chdryra.android.testutils.RandomLatLng;
import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationComparatorsTest extends ComparatorCollectionTest<GvLocationList.GvLocation> {
    //Constructors
    public LocationComparatorsTest() {
        super(LocationComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvLocationList.GvLocation> comparator = mComparators.getDefault();

        LatLng latlng1 = RandomLatLng.nextLatLng();
        LatLng latlng2 = RandomLatLng.nextLatLng();
        String nameA = "A";
        String namea = "a";
        String nameB = "B";
        String nameC = "C";

        GvLocationList.GvLocation loc1 = new GvLocationList.GvLocation(latlng1, nameA);
        GvLocationList.GvLocation loc12 = new GvLocationList.GvLocation(latlng1, nameA);
        GvLocationList.GvLocation loc13 = new GvLocationList.GvLocation(latlng2, namea);
        GvLocationList.GvLocation loc14 = new GvLocationList.GvLocation(latlng2, nameA);
        GvLocationList.GvLocation loc2 = new GvLocationList.GvLocation(latlng1, nameB);
        GvLocationList.GvLocation loc3 = new GvLocationList.GvLocation(latlng1, nameC);

        ComparatorTester<GvLocationList.GvLocation> tester = new ComparatorTester<>(comparator);
        tester.testEquals(loc1, loc1);
        tester.testEquals(loc1, loc12);
        tester.testEquals(loc1, loc13);
        tester.testEquals(loc1, loc14);
        tester.testFirstSecond(loc1, loc2);
        tester.testFirstSecond(loc2, loc3);
        tester.testFirstSecond(loc1, loc3);
    }
}
