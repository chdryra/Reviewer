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

import com.chdryra.android.reviewer.Controller.ReviewDataExpander;
import com.chdryra.android.reviewer.Controller.ReviewNodeAdapter;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataExpanderTest extends AndroidTestCase {
    @SmallTest
    public void testExpandItem() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());
        ReviewNodeAdapter.DataAdapter parent = new ReviewNodeAdapter.DataAdapter(getContext(),
                node);

        ReviewDataExpander expander = new ReviewDataExpander(getContext(), parent);
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        assertFalse(expander.isExpandable(comment));
        assertNull(expander.expandItem(comment));

        GvDataList data = parent.getGridData();
        assertTrue(data.size() > 0);
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            assertNotNull(expander.expandItem(datum));
        }
    }
}