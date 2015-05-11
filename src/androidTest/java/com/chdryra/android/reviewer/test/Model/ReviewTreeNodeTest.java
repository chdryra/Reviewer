/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeNodeTest extends TestCase {
    private static final int NUM = 3;
    private Review                    mReview;
    private Review                    mParent;
    private ReviewIdableList<Review> mChildren;

    @SmallTest
    public void testSetParent() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, false);
        assertNull(node.getParent());

        ReviewTreeNode parentNode = new ReviewTreeNode(mParent, false);
        assertEquals(0, parentNode.getChildren().size());

        node.setParent(parentNode);
        assertNotNull(node.getParent());
        assertEquals(parentNode, node.getParent());

        assertEquals(1, parentNode.getChildren().size());
        assertEquals(node, parentNode.getChildren().getItem(0));
    }

    @SmallTest
    public void testAddChild() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, false);
        assertEquals(0, node.getChildren().size());

        ReviewIdableList<ReviewTreeNode> children = new ReviewIdableList<>();
        int i = 0;
        for (Review child : mChildren) {
            ReviewTreeNode childNode = new ReviewTreeNode(child, false);
            children.add(childNode);

            assertNull(childNode.getParent());

            node.addChild(childNode);
            assertEquals(++i, node.getChildren().size());

            assertNotNull(childNode.getParent());
            assertEquals(node, childNode.getParent());
        }

        ReviewIdableList<ReviewNode> nodeChildren = node.getChildren();
        assertEquals(children.size(), nodeChildren.size());
        for (i = 0; i < children.size(); ++i) {
            assertEquals(children.getItem(i), nodeChildren.getItem(i));
        }
    }

    @SmallTest
    public void testReviewGetters() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, false);

        assertEquals(mReview.getId(), node.getId());
        assertEquals(mReview.getSubject(), node.getSubject());
        assertEquals(mReview.getRating(), node.getRating());
        assertEquals(mReview.getAuthor(), node.getAuthor());
        assertEquals(mReview.getPublishDate(), node.getPublishDate());

        assertEquals(mReview.getComments(), node.getComments());
        assertEquals(mReview.getFacts(), node.getFacts());
        assertEquals(mReview.getImages(), node.getImages());
        assertEquals(mReview.getLocations(), node.getLocations());
    }

    @SmallTest
    public void testGetRatingAverage() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, true);
        for (Review child : mChildren) {
            node.addChild(new ReviewTreeNode(child, false));
        }

        float rating = 0;
        for (Review child : mChildren) {
            rating += child.getRating().get() / mChildren.size();
        }
        assertEquals(rating, node.getRating().get(), 0.0001);
    }

    @SmallTest
    public void testGetIdUnique() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, false, ReviewId.generateId());
        assertFalse(mReview.getId().equals(node.getId()));
    }

    @SmallTest
    public void testRemoveChild() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, true);
        ReviewTreeNode childNode1 = new ReviewTreeNode(mChildren.getItem(0), false);
        ReviewTreeNode childNode2 = new ReviewTreeNode(mChildren.getItem(1), false);

        assertEquals(0, node.getChildren().size());
        assertNull(childNode1.getParent());
        assertNull(childNode2.getParent());

        node.addChild(childNode1);
        node.addChild(childNode2);

        assertEquals(2, node.getChildren().size());
        assertEquals(childNode1, node.getChildren().getItem(0));
        assertEquals(childNode2, node.getChildren().getItem(1));
        assertNotNull(childNode1.getParent());
        assertNotNull(childNode2.getParent());
        assertEquals(node, childNode1.getParent());
        assertEquals(node, childNode2.getParent());

        node.removeChild(childNode1.getId());
        assertEquals(1, node.getChildren().size());
        assertEquals(childNode2, node.getChildren().getItem(0));
        assertNull(childNode1.getParent());
        assertNotNull(childNode2.getParent());
        assertEquals(node, childNode2.getParent());
    }

    @SmallTest
    public void testCreateTree() {
        ReviewTreeNode node = new ReviewTreeNode(mReview, false);
        node.setParent(new ReviewTreeNode(mParent, false));
        for (Review child : mChildren) {
            node.addChild(new ReviewTreeNode(child, false));
        }

        ReviewNode tree = node.createTree();

        assertNotNull(tree);
        assertEquals(node.getId(), tree.getId());
        assertEquals(node.getParent(), tree.getParent());
        assertEquals(node.getChildren(), tree.getChildren());
    }

    @Override
    protected void setUp() throws Exception {
        mReview = ReviewMocker.newReview();
        mParent = ReviewMocker.newReview();
        mChildren = new ReviewIdableList<>();
        for (int i = 0; i < NUM; ++i) {
            mChildren.add(ReviewMocker.newReview());
        }
    }
}
