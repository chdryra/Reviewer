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
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerGvDataList;
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
    private AdapterReviewViewAdapter      mAdapter;
    private AdapterReviewNode mNodeAdapter;
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
        GvDataList<GvData> data = (GvDataList<GvData>) mAdapter.getGridData();
        assertEquals(mGridData, data);
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
                assertNotNull(mAdapter.expandItem(datum));
            } else {
                assertFalse(mAdapter.isExpandable(datum));
                assertNull(mAdapter.expandItem(datum));
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        ReviewNode node = ReviewMocker.newReviewNode(true);

        mGridData = new GvDataList<>(null, GvData.class, new GvDataType("testData"));
        mGridData.add(GvDataMocker.newCommentList(6, false));
        mGridData.add(GvDataMocker.newFactList(6, false));
        mGridData.add(GvDataMocker.newLocationList(0, false));
        mGridData.add(GvDataMocker.newImage(null));

        ViewerGvDataList wrapper = new ViewerGvDataList(mGridData);
        mNodeAdapter = new AdapterReviewNode(node, wrapper);
        mAdapter = new AdapterReviewViewAdapter(getContext(), mNodeAdapter, wrapper);
    }
}