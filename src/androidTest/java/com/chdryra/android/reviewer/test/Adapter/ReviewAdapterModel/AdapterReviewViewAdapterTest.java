/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 May, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerAdapterToData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerGvDataCollection;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 06/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewViewAdapterTest extends AndroidTestCase {
    private AdapterReviewViewAdapter<GvData> mAdapter;
    private AdapterReviewNode<GvData> mNodeAdapter;
    private GvDataList<GvData>            mGridData;

    @SmallTest
    public void testGetSubject() {
        assertEquals(mNodeAdapter.getSubject(), mAdapter.getSubject());
    }

    @SmallTest
    public void testGetRating() {
        assertEquals(mNodeAdapter.getRating(), mAdapter.getRating());
    }

    @SmallTest
    public void testGetAverageRating() {
        assertEquals(mNodeAdapter.getAverageRating(), mAdapter.getAverageRating());
    }

    @SmallTest
    public void testGridData() {
        assertEquals(mGridData, mAdapter.getGridData());
    }

    @SmallTest
    public void testGetCovers() {
        assertEquals(mNodeAdapter.getCovers(), mAdapter.getCovers());
        //TODO test alternative return for grid data with review id.
    }

    @SmallTest
    public void testExpandable() {
        GvDataList data = mAdapter.getGridData();
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            if (i < 2) {
                assertTrue(mAdapter.isExpandable(datum));
                assertNotNull(mAdapter.expandGridCell(datum));
            } else {
                assertFalse(mAdapter.isExpandable(datum));
                assertNull(mAdapter.expandGridCell(datum));
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        ReviewNode node = ReviewMocker.newReviewNode(true);

        GvDataType<GvData> type = new GvDataType<>(GvData.class, "testData");
        mGridData = new GvDataList<>(type, null);
        mGridData.add(GvDataMocker.newCommentList(6, false));
        mGridData.add(GvDataMocker.newFactList(6, false));
        mGridData.add(GvDataMocker.newLocationList(0, false));
        mGridData.add(GvDataMocker.newImage(null));

        ViewerAdapterToData expander = new ViewerAdapterToData(getContext(), mNodeAdapter);
        ViewerGvDataCollection<GvData> wrapper = new ViewerGvDataCollection<>(expander, mGridData);
        mNodeAdapter = new AdapterReviewNode<>(node, wrapper);
        mAdapter = new AdapterReviewViewAdapter<>(mNodeAdapter, wrapper);
    }
}
