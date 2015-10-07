package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataSorting.FactComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactComparatorsTest extends ComparatorCollectionTest<GvFactList.GvFact> {

    //Constructors
    public FactComparatorsTest() {
        super(FactComparators.getComparators());
    }

    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvFactList.GvFact> comparator = mComparators.getDefault();

        GvFactList.GvFact AA = new GvFactList.GvFact("A", "A");
        GvFactList.GvFact AA2 = new GvFactList.GvFact("A", "A");
        GvFactList.GvFact Aa = new GvFactList.GvFact("A", "a");
        GvFactList.GvFact aA = new GvFactList.GvFact("a", "A");
        GvFactList.GvFact aa = new GvFactList.GvFact("a", "a");
        GvFactList.GvFact AZ = new GvFactList.GvFact("A", "Z");
        GvFactList.GvFact BC = new GvFactList.GvFact("B", "C");
        GvFactList.GvFact CA = new GvFactList.GvFact("C", "A");

        ComparatorTester<GvFactList.GvFact> tester = new ComparatorTester<>(comparator);
        tester.testEquals(AA, AA);
        tester.testEquals(AA, AA2);
        tester.testEquals(AA, Aa);
        tester.testEquals(AA, aA);
        tester.testEquals(AA, aa);
        tester.testFirstSecond(AA, AZ);
        tester.testFirstSecond(AZ, BC);
        tester.testFirstSecond(BC, CA);
        tester.testFirstSecond(AA, CA);
    }
}
