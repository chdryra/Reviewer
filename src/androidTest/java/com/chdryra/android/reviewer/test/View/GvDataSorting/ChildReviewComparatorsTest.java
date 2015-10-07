package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataSorting.ChildReviewComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparatorsTest extends ComparatorCollectionTest<GvCriterionList
        .GvCriterion> {
    //Constructors
    public ChildReviewComparatorsTest() {
        super(ChildReviewComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvCriterionList.GvCriterion> comparator = mComparators.getDefault();

        GvCriterionList.GvCriterion reviewA1 = new GvCriterionList.GvCriterion("A", 1f);
        GvCriterionList.GvCriterion reviewA12 = new GvCriterionList.GvCriterion("A", 1f);
        GvCriterionList.GvCriterion reviewA2 = new GvCriterionList.GvCriterion("A", 2f);
        GvCriterionList.GvCriterion reviewA3 = new GvCriterionList.GvCriterion("a", 2f);
        GvCriterionList.GvCriterion reviewB1 = new GvCriterionList.GvCriterion("B", 1f);
        GvCriterionList.GvCriterion reviewB2 = new GvCriterionList.GvCriterion("b", 5f);
        GvCriterionList.GvCriterion reviewC1 = new GvCriterionList.GvCriterion("C", 2f);
        GvCriterionList.GvCriterion reviewC2 = new GvCriterionList.GvCriterion("c", 1f);

        ComparatorTester<GvCriterionList.GvCriterion> tester = new ComparatorTester<>
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
