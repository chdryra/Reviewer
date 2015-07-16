/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ExpanderChildNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.GridCellExpander;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerChildList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildNodeTest extends AndroidTestCase {
    protected ReviewNode mNode;
    private ViewerChildList mWrapper;
    private GridCellExpander<GvReviewOverviewList.GvReviewOverview> mExpander;

    @SmallTest
    public void testExpandItem() {
        GvReviewOverviewList children = mWrapper.getGridData();
        for (GvReviewOverviewList.GvReviewOverview child : children) {
            ReviewViewAdapter adapter = mExpander.expandItem(child);
            assertNotNull(adapter);
            assertEquals(child.getSubject(), adapter.getSubject());
            assertEquals(child.getRating(), adapter.getRating());
            assertNotNull(adapter.getGridData());
        }
    }

    @Override
    protected void setUp() throws Exception {
        mNode = ReviewMocker.newReviewNode(false);
        mExpander = getExpander(mWrapper);
        mWrapper = new ViewerChildList(mNode, mExpander);
    }

    protected GridCellExpander<GvReviewOverviewList.GvReviewOverview> getExpander(ViewerChildList
                                                                                          wrapper) {
        return new ExpanderChildNode(getContext(), mNode);
    }
}
