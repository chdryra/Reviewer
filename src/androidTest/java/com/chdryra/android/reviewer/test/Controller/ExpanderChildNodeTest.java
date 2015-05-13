/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ExpanderChildNode;
import com.chdryra.android.reviewer.Controller.GridDataExpander;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.Controller.WrapperChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildNodeTest extends AndroidTestCase {
    private WrapperChildList mWrapper;
    private GridDataExpander mExpander;

    @SmallTest
    public void testExpandItem() {
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        assertFalse(mExpander.isExpandable(comment));
        assertNull(mExpander.expandItem(comment));

        GvReviewList children = (GvReviewList) mWrapper.getGridData();
        for (GvReviewList.GvReviewOverview child : children) {
            ReviewViewAdapter adapter = mExpander.expandItem(child);
            assertNotNull(adapter);
            assertEquals(child.getSubject(), adapter.getSubject());
            assertEquals(child.getRating(), adapter.getRating());
            assertNotNull(adapter.getGridData());
        }
    }

    @Override
    protected void setUp() throws Exception {
        mWrapper = new WrapperChildList(ReviewMocker.newReviewNode(false));
        mExpander = getExpander(mWrapper);
    }

    protected GridDataExpander getExpander(WrapperChildList wrapper) {
        return new ExpanderChildNode(getContext(), wrapper);
    }
}
