/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ControllerReviewNode;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvSubjectRatingList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewNodeExpandable;
import com.chdryra.android.reviewer.test.TestUtils.ChildrenMocker;
import com.chdryra.android.reviewer.test.TestUtils.ControllerTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewNodeTest extends AndroidTestCase {
    private final static int NUMDATA = 50;
    private ReviewNodeExpandable         mNode;
    private ControllerReviewNode         mController;
    private ControllerTester<ReviewNode> mTester;

    @SmallTest
    public void testHasChildren() {
        assertFalse(mController.hasData(GvDataList.GvType.CHILDREN));

        mNode.addChild(ReviewMocker.newReviewNode());

        assertTrue(mController.hasData(GvDataList.GvType.CHILDREN));
    }

    @SmallTest
    public void testHasDataOther() {
        mTester.testHasData();
    }

    @SmallTest
    public void testGetDataChildren() {
        ChildrenMocker.checkEquality(mNode.getChildren(),
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));

        RCollectionReview<ReviewNode> setData = ChildrenMocker.getMockNodeChildren();
        for (ReviewNode child : setData) {
            mNode.addChild(child);
        }

        ChildrenMocker.checkEquality(setData,
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));
    }

    @SmallTest
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
