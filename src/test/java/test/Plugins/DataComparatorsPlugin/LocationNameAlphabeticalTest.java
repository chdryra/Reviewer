/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.LocationNameAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationNameAlphabeticalTest extends ComparatorTest<DataLocation> {
    public LocationNameAlphabeticalTest() {
        super(new LocationNameAlphabetical());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        DataLocation A = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), "a" + RandomString.nextWord());
        DataLocation B = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), "b" + RandomString.nextWord());
        DataLocation C = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), "C" + RandomString.nextWord());

        ComparatorTester<DataLocation> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String name1 = RandomString.nextWord();
        String name2 = name1 + RandomString.nextWord();
        DataLocation loc1 = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name1);
        DataLocation loc2 = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name2);

        ComparatorTester<DataLocation> tester = newComparatorTester();
        if(name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(loc1, loc2);
        } else if(name1.compareToIgnoreCase(name2) > 0){
            tester.testFirstSecond(loc2, loc1);
        } else {
            tester.testEquals(loc1, loc2);
        }
    }
    
    @Test
    public void comparatorEqualitySameLocationName() {
        String name = RandomString.nextWord();

        DataLocation loc1 = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name);
        DataLocation loc2 = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name);

        ComparatorTester<DataLocation> tester = newComparatorTester();
        tester.testEquals(loc1, loc1);
        tester.testEquals(loc1, loc2);
    }

    @Test
    public void comparatorEqualitySameLocationNameIgnoresCase() {
        String name = RandomString.nextWord();

        DataLocation loc = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name);
        DataLocation locLower = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name.toLowerCase());
        DataLocation locUpper = new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), name.toUpperCase());

        ComparatorTester<DataLocation> tester = newComparatorTester();
        tester.testEquals(loc, locLower);
        tester.testEquals(locLower, locUpper);
    }
    
}
