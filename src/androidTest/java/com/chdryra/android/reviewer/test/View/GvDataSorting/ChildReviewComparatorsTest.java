package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.ChildReviewComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparatorsTest extends ComparatorCollectionTest<GvCriterion> {
    //Constructors
    public ChildReviewComparatorsTest() {
        super(ChildReviewComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvCriterion> comparator = mComparators.getDefault();

        GvCriterion reviewA1 = new GvCriterion("A", 1f);
        GvCriterion reviewA12 = new GvCriterion("A", 1f);
        GvCriterion reviewA2 = new GvCriterion("A", 2f);
        GvCriterion reviewA3 = new GvCriterion("a", 2f);
        GvCriterion reviewB1 = new GvCriterion("B", 1f);
        GvCriterion reviewB2 = new GvCriterion("b", 5f);
        GvCriterion reviewC1 = new GvCriterion("C", 2f);
        GvCriterion reviewC2 = new GvCriterion("c", 1f);

        ComparatorTester<GvCriterion> tester = new ComparatorTester<>
                (comparator);
        tester.testEquals(reviewA1, reviewA1);
        tester.testEquals(reviewA1, reviewA12);
        tester.testEquals(reviewA2, reviewA3);
        tester.testFirstSecond(reviewA2, reviewA1);
        tester.testFirstSecond(reviewA1, reviewB1);
        tester.testFirstSecond(reviewB2, reviewB1);
        tester.testFirstSecond(reviewB2, reviewC1);
        tester.testFirstSecond(reviewC1, reviewC2);
    }
}
