/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.startouch.test.Model.TreeMethods;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Model.ReviewsModel.Implementation
        .ReviewTreeComparer;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTreeMutable;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeComparerTest extends TestCase {

    @SmallTest
    public void testCompareNodes() {
        ReviewNode node1 = ReviewMocker.newReviewNode(true);

        ReviewTreeMutable node2 = new ReviewTreeMutable(node1.getReview(), true, node1.getMdReviewId());
        assertFalse(ReviewTreeComparer.compareNodes(node1, node2));
        node2.setParent((ReviewTreeMutable) node1.getParent());
        assertFalse(ReviewTreeComparer.compareNodes(node1, node2));
        assertTrue(ReviewTreeComparer.compareNodes(node1, node1));
        assertTrue(ReviewTreeComparer.compareNodes(node2, node2));
    }
}
