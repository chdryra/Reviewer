/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewTree;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeTest extends TestCase {
    private ReviewNode mNode;
    private ReviewTree mTree;

    @SmallTest
    public void testGetters() {
        assertEquals(mNode.getReview(), mTree.getReview());
        assertEquals(mNode.getParent(), mTree.getParent());
        assertEquals(mNode.getId(), mTree.getId());
        assertEquals(mTree, mTree.getReviewNode());

        assertEquals(mNode.getId(), mTree.getId());
        assertEquals(mNode.getSubject(), mTree.getSubject());
        assertEquals(mNode.getRating(), mTree.getRating());
        assertEquals(mNode.getAuthor(), mTree.getAuthor());
        assertEquals(mNode.getPublishDate(), mTree.getPublishDate());

        assertEquals(mNode.getComments(), mTree.getComments());
        assertEquals(mNode.getFacts(), mTree.getFacts());
        assertEquals(mNode.getImages(), mTree.getImages());
        assertEquals(mNode.getLocations(), mTree.getLocations());
    }

    @Override
    protected void setUp() throws Exception {
        mNode = ReviewMocker.newReviewNode();
        mTree = new ReviewTree(mNode);
    }
}
