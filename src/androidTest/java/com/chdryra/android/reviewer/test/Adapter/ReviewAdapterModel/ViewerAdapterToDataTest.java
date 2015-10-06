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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerAdapterToData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAdapterToDataTest extends AndroidTestCase {
    @SmallTest
    public void testExpandItem() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList tags = GvDataMocker.newTagList(3, false);
        TagsManager.tag(getContext(), node.getId(), tags.toStringArray());
        ReviewViewAdapter<? extends GvData> parent = FactoryReviewViewAdapter.newTreeDataAdapter
                (getContext(), node);

        ViewerAdapterToData expander = new ViewerAdapterToData(parent);
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        assertFalse(expander.isExpandable(comment));
        assertNull(expander.expandGridCell(comment));
        GvDataList data = parent.getGridData();
        assertTrue(data.size() > 0);
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            if (datum.getGvDataType() != GvCriterionList.GvCriterion.TYPE) {
                assertNotNull(expander.expandGridCell(datum));
            } else {
                assertNull(expander.expandGridCell(datum));
            }
        }
    }
}
