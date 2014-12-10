/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReviewNode;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvSubjectRatingList;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewNodeExpandable;
import com.chdryra.android.reviewer.test.TestUtils.ControllerTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewNodeTest extends AndroidTestCase {
    private ReviewNodeExpandable         mNode;
    private ControllerReviewNode         mController;
    private ControllerTester<ReviewNode> mTester;

    public void testHasChildren() {
        assertFalse(mController.hasData(GvDataList.GvType.CHILDREN));

        mNode.addChild(ReviewMocker.newReviewNode());

        assertTrue(mController.hasData(GvDataList.GvType.CHILDREN));
    }

    public void testHasDataOther() {
        mTester.testHasData();
    }

    public void testGetChildren() {
        assertEquals(0, mController.getData(GvDataList.GvType.CHILDREN).size());

        ReviewNode node = ReviewMocker.newReviewNode();
        mNode.addChild(node);

        GvSubjectRatingList list = (GvSubjectRatingList) mController.getData(GvDataList
                .GvType.CHILDREN);

        assertEquals(1, list.size());
        assertEquals(node.getSubject().get(), list.getItem(0).getSubject());
        assertEquals(node.getRating().get(), list.getItem(0).getRating());
    }

    public void testGetDataOther() {
        mTester.testGetData();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mNode = ReviewMocker.newReviewNodeExpandable();
        mController = new ControllerReviewNode(mNode);
        mTester = new ControllerTester<>(mController, mNode);
    }
}
