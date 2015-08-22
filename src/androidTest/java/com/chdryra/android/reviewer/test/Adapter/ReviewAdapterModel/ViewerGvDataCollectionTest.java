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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ExpanderGridCell;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerGvDataCollection;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataCollectionTest extends AndroidTestCase {
    @SmallTest
    public void testGetGridData() {
        GvDataList<GvCommentList.GvComment> data = (GvCommentList) GvDataMocker.getData
                (GvCommentList.TYPE, 10);

        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewViewAdapter<GvReviewOverviewList.GvReviewOverview> parent =
                FactoryReviewViewAdapter.newChildOverviewAdapter(getContext(), node);
        ExpanderGridCell expander = new ExpanderGridCell(getContext(), parent);
        ViewerGvDataCollection<GvCommentList.GvComment> wrapper = new ViewerGvDataCollection<>(expander, data);

        assertEquals(data, wrapper.getGridData());
    }
}
