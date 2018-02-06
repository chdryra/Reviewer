/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.startouch.test.Model.TreeMethods;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Model.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation
        .VisitorRatingAverager;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorRatingAveragerTest extends TestCase {
    @SmallTest
    public void testGetRating() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        VisitorRatingAverager visitor = new VisitorRatingAverager();
        node.acceptVisitor(visitor);

        float nodeAverage = 0;
        MdIdableCollection<ReviewNode> children = node.getChildren();
        assertTrue(children.size() > 0);
        for (ReviewNode child : children) {
            nodeAverage += child.getRating().getRating();
        }

        nodeAverage /= children.size();
        assertEquals(nodeAverage, visitor.getRating());
    }
}
