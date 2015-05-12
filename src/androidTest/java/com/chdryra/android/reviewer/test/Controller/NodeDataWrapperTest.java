/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.MdGvConverter;
import com.chdryra.android.reviewer.Controller.NodeDataWrapper;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataWrapperTest extends TestCase {
    @SmallTest
    public void testGetGridData() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());

        NodeDataWrapper wrapper = new NodeDataWrapper(node);
        GvDataList data = wrapper.getGridData();

        assertNotNull(data);
        assertEquals(6, data.size());
        assertEquals(tags, data.getItem(0));
        assertEquals(MdGvConverter.convertChildren(node), data.getItem(1));
        assertEquals(MdGvConverter.convert(node.getImages()), data.getItem(2));
        assertEquals(MdGvConverter.convert(node.getComments()), data.getItem(3));
        assertEquals(MdGvConverter.convert(node.getLocations()), data.getItem(4));
        assertEquals(MdGvConverter.convert(node.getFacts()), data.getItem(5));
    }
}
