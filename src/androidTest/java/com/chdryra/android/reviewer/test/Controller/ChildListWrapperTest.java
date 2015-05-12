/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ChildListWrapper;
import com.chdryra.android.reviewer.Controller.MdGvConverter;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildListWrapperTest extends TestCase {

    @SmallTest
    public void testGetGridData() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        ChildListWrapper wrapper = new ChildListWrapper(node);
        GvDataList data = wrapper.getGridData();
        assertNotNull(data);
        ReviewIdableList<ReviewNode> children = node.getChildren();
        assertEquals(children.size(), data.size());
        GvReviewList list = (GvReviewList) data;
        for (int i = 0; i < children.size(); ++i) {
            ReviewNode child = children.getItem(i);
            GvReviewList.GvReviewOverview item = list.getItem(i);
            assertEquals(child.getSubject().get(), item.getSubject());
            assertEquals(child.getRating().get(), item.getRating());
            assertEquals(child.getAuthor(), item.getAuthor());
            assertEquals(child.getPublishDate(), item.getPublishDate());
            MdLocationList locs = child.getLocations();
            String location = locs.getItem(0).getName();
            if (locs.size() > 1) {
                String loc = locs.size() == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locs.size() - 1) + loc;
            }
            assertEquals(location, item.getLocationString());
            GvCommentList headlines = MdGvConverter.convert(child.getComments()).getHeadlines();
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() : null;
            assertEquals(headline, item.getHeadline());
            MdImageList covers = child.getImages().getCovers();
            if (covers.size() > 0) {
                assertTrue(covers.getItem(0).getBitmap().sameAs(item.getCoverImage()));
            }
        }
    }
}
