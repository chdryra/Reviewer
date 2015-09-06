package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataSorting.ChildReviewComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparatorsTest extends ComparatorCollectionTest<GvChildReviewList
        .GvChildReview> {
    public ChildReviewComparatorsTest() {
        super(ChildReviewComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvChildReviewList.GvChildReview> comparator = mComparators.getDefault();

        GvChildReviewList.GvChildReview reviewA1 = new GvChildReviewList.GvChildReview("A", 1f);
        GvChildReviewList.GvChildReview reviewA12 = new GvChildReviewList.GvChildReview("A", 1f);
        GvChildReviewList.GvChildReview reviewA2 = new GvChildReviewList.GvChildReview("A", 2f);
        GvChildReviewList.GvChildReview reviewA3 = new GvChildReviewList.GvChildReview("a", 2f);
        GvChildReviewList.GvChildReview reviewB1 = new GvChildReviewList.GvChildReview("B", 1f);
        GvChildReviewList.GvChildReview reviewB2 = new GvChildReviewList.GvChildReview("b", 5f);
        GvChildReviewList.GvChildReview reviewC1 = new GvChildReviewList.GvChildReview("C", 2f);
        GvChildReviewList.GvChildReview reviewC2 = new GvChildReviewList.GvChildReview("c", 1f);

        ComparatorTester<GvChildReviewList.GvChildReview> tester = new ComparatorTester<>
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
