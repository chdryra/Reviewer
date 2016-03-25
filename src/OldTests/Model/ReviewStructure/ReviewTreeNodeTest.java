/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewStructure;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeMutable;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeNodeTest extends TestCase {
    private static final int NUM = 3;
    private Review mReview;
    private Review mParent;
    private MdIdableCollection<Review> mChildren;

    @SmallTest
    public void testSetParent() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, false, mReview.getMdReviewId());
        assertNull(node.getParent());

        ReviewTreeMutable parentNode = new ReviewTreeMutable(mParent, false, mParent.getMdReviewId());
        assertEquals(0, parentNode.getChildren().size());

        node.setParent(parentNode);
        assertNotNull(node.getParent());
        assertEquals(parentNode, node.getParent());

        assertEquals(1, parentNode.getChildren().size());
        assertEquals(node, parentNode.getChildren().getItem(0));
    }

    @SmallTest
    public void testAddChild() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, false, mReview.getMdReviewId());
        assertEquals(0, node.getChildren().size());

        MdIdableCollection<ReviewTreeMutable> children = new MdIdableCollection<>();
        int i = 0;
        for (Review child : mChildren) {
            ReviewTreeMutable childNode = new ReviewTreeMutable(child, false, child.getMdReviewId());
            children.add(childNode);

            assertNull(childNode.getParent());

            node.addChild(childNode);
            assertEquals(++i, node.getChildren().size());

            assertNotNull(childNode.getParent());
            assertEquals(node, childNode.getParent());
        }

        MdIdableCollection<ReviewNode> nodeChildren = node.getChildren();
        assertEquals(children.size(), nodeChildren.size());
        for (i = 0; i < children.size(); ++i) {
            assertEquals(children.getItem(i), nodeChildren.getItem(i));
        }
    }

    @SmallTest
    public void testReviewGetters() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, false, mReview.getMdReviewId());

        assertEquals(mReview.getMdReviewId(), node.getMdReviewId());
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
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, true, mReview.getMdReviewId());
        for (Review child : mChildren) {
            node.addChild(new ReviewTreeMutable(child, false, child.getMdReviewId()));
        }

        float rating = 0;
        for (Review child : mChildren) {
            rating += child.getRating().getRating() / mChildren.size();
        }
        assertEquals(rating, node.getRating().getRating(), 0.0001);
    }

    @SmallTest
    public void testGetIdUnique() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, false, RandomReviewId.nextId());
        assertFalse(mReview.getMdReviewId().equals(node.getMdReviewId()));
    }

    @SmallTest
    public void testRemoveChild() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, true, mReview.getMdReviewId());
        Review child1 = mChildren.getItem(0);
        Review child2 = mChildren.getItem(1);
        ReviewTreeMutable childNode1 = new ReviewTreeMutable(child1, false, child1.getMdReviewId());
        ReviewTreeMutable childNode2 = new ReviewTreeMutable(child2, false, child2.getMdReviewId());

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

        node.removeChild(childNode1.getMdReviewId());
        assertEquals(1, node.getChildren().size());
        assertEquals(childNode2, node.getChildren().getItem(0));
        assertNull(childNode1.getParent());
        assertNotNull(childNode2.getParent());
        assertEquals(node, childNode2.getParent());
    }

    @SmallTest
    public void testCreateTree() {
        ReviewTreeMutable node = new ReviewTreeMutable(mReview, false, mReview.getMdReviewId());
        node.setParent(new ReviewTreeMutable(mParent, false, mParent.getMdReviewId()));
        for (Review child : mChildren) {
            node.addChild(new ReviewTreeMutable(child, false, child.getMdReviewId()));
        }

        ReviewNode tree = node.makeTree();

        assertNotNull(tree);
        assertEquals(node.getMdReviewId(), tree.getMdReviewId());
        assertEquals(node.getParent(), tree.getParent());
        assertEquals(node.getChildren(), tree.getChildren());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mReview = ReviewMocker.newReview();
        mParent = ReviewMocker.newReview();
        mChildren = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mChildren.add(ReviewMocker.newReview());
        }
    }
}
