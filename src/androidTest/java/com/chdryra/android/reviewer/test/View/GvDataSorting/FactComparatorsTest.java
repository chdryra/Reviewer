package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.FactComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactComparatorsTest extends ComparatorCollectionTest<GvFact> {

    //Constructors
    public FactComparatorsTest() {
        super(FactComparators.getComparators());
    }

    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvFact> comparator = mComparators.getDefault();

        GvFact AA = new GvFact("A", "A");
        GvFact AA2 = new GvFact("A", "A");
        GvFact Aa = new GvFact("A", "a");
        GvFact aA = new GvFact("a", "A");
        GvFact aa = new GvFact("a", "a");
        GvFact AZ = new GvFact("A", "Z");
        GvFact BC = new GvFact("B", "C");
        GvFact CA = new GvFact("C", "A");

        ComparatorTester<GvFact> tester = new ComparatorTester<>(comparator);
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
