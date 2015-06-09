/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorTreeFlattener;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorTreeFlattenerTest extends TestCase {
    @SmallTest
    public void testVisit() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        VisitorTreeFlattener visitor = new VisitorTreeFlattener();
        node.acceptVisitor(visitor);

        ReviewIdableList<ReviewNode> nodes = visitor.getNodes();
        ReviewIdableList<ReviewNode> flattened = flatten(node);
        assertEquals(flattened.size(), nodes.size());
        for (ReviewNode item : flattened) {
            assertTrue(nodes.containsId(item.getId()));
        }
    }

    private ReviewIdableList<ReviewNode> flatten(ReviewNode node) {
        ReviewIdableList<ReviewNode> nodes = new ReviewIdableList<>();
        nodes.add(node);

        for (ReviewNode child : node.getChildren()) {
            nodes.add(flatten(child));
        }

        return nodes;
    }
}
