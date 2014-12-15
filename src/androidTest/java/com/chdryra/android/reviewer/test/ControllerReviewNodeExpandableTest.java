/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ControllerReviewNodeExpandable;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.ReviewNodeExpandable;
import com.chdryra.android.reviewer.test.TestUtils.ChildrenMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewNodeExpandableTest extends TestCase {
    private final static int NUMDATA = 50;
    ControllerReviewNodeExpandable mController;
    private ReviewNodeExpandable mNode;

    @SmallTest
    public void testSetChildren() {
        ChildrenMocker.checkEquality(mNode.getChildren(),
                (GvChildrenList) mController.getData(GvDataList.GvType.CHILDREN));

        GvChildrenList setData = ChildrenMocker.getMockGvChildren();
        mController.setChildren(setData);

        ChildrenMocker.checkEquality(mNode.getChildren(), setData);
        ChildrenMocker.checkEquality(setData,
                (GvChildrenList) mController.getData(GvDataList.GvType.CHILDREN));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mNode = ReviewMocker.newReviewNodeExpandable();
        mController = new ControllerReviewNodeExpandable(mNode);
    }
}
