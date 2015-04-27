/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestReviewsTest extends InstrumentationTestCase {

    @SmallTest
    public void testGetReviews() {
        ArrayList<ReviewNode> reviews = TestReviews.getReviews(getInstrumentation());
        assertEquals(2, reviews.size());
        testReview1(reviews.get(0));
    }

    private void testReview1(ReviewNode node) {
        assertEquals("Tayyabs", node.getSubject().get());
        assertTrue(node.isRatingAverageOfChildren());
        assertEquals(3.5f, node.getRating().get());
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(node.getId());
        assertEquals(3, tags.size());
    }
}
