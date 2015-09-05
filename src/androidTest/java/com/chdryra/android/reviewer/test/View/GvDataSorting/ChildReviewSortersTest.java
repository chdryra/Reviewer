package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataSorting.ChildReviewSorters;

import junit.framework.TestCase;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewSortersTest extends TestCase {
    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvChildReviewList.GvChildReview> comparator = ChildReviewSorters
                .getComparators().getDefault();

        GvChildReviewList.GvChildReview reviewA1 = new GvChildReviewList.GvChildReview("A", 1f);
        GvChildReviewList.GvChildReview reviewA2 = new GvChildReviewList.GvChildReview("A", 2f);
        GvChildReviewList.GvChildReview reviewA3 = new GvChildReviewList.GvChildReview("a", 2f);
        GvChildReviewList.GvChildReview reviewB1 = new GvChildReviewList.GvChildReview("B", 1f);
        GvChildReviewList.GvChildReview reviewB2 = new GvChildReviewList.GvChildReview("b", 5f);
        GvChildReviewList.GvChildReview reviewC1 = new GvChildReviewList.GvChildReview("C", 2f);
        GvChildReviewList.GvChildReview reviewC2 = new GvChildReviewList.GvChildReview("c", 1f);

        assertEquals(0, comparator.compare(reviewA1, reviewA1));
        assertEquals(0, comparator.compare(reviewA2, reviewA2));
        assertEquals(0, comparator.compare(reviewA3, reviewA3));
        assertEquals(0, comparator.compare(reviewB1, reviewB1));
        assertEquals(0, comparator.compare(reviewB2, reviewB2));
        assertEquals(0, comparator.compare(reviewC1, reviewC1));
        assertEquals(0, comparator.compare(reviewC2, reviewC2));

        assertEquals(0, comparator.compare(reviewA2, reviewA3));

        assertTrue(comparator.compare(reviewA2, reviewA1) < 0);
        assertEquals(comparator.compare(reviewA2, reviewA1), -comparator.compare(reviewA1,
                reviewA2));
        assertTrue(comparator.compare(reviewA1, reviewB1) < 0);
        assertEquals(comparator.compare(reviewA1, reviewB1), -comparator.compare(reviewB1,
                reviewA1));
        assertTrue(comparator.compare(reviewB2, reviewB1) < 0);
        assertTrue(comparator.compare(reviewB2, reviewC1) < 0);
        assertTrue(comparator.compare(reviewC1, reviewC2) < 0);
    }
}
