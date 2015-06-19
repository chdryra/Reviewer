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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerTreeData;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeDataTest extends AndroidTestCase {
    @SmallTest
    public void testGetGridDataMeta() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());

        ViewerTreeData wrapper = new ViewerTreeData(getContext(), node);
        GvDataCollection collection = wrapper.getGridData();
        assertNotNull(collection);
        assertEquals(10, collection.size());

        assertEquals(node.getChildren().size(), collection.getItem(0).size());
        assertEquals(GvReviewList.TYPE, collection.getItem(0).getGvDataType());
        assertEquals(node.getChildren().size(), collection.getItem(1).size());
        assertEquals(GvAuthorList.TYPE, collection.getItem(1).getGvDataType());
        assertEquals(node.getChildren().size(), collection.getItem(2).size());
        assertEquals(GvSubjectList.TYPE, collection.getItem(2).getGvDataType());
        assertEquals(GvDateList.TYPE, collection.getItem(3).getGvDataType());
        assertEquals(node.getChildren().size(), collection.getItem(3).size());
        assertEquals(tags.size(), collection.getItem(4).size());
        assertEquals(GvTagList.TYPE, collection.getItem(4).getGvDataType());
        assertEquals(0, collection.getItem(5).size());
        assertEquals(GvChildList.TYPE, collection.getItem(5).getGvDataType());
        assertEquals(node.getImages().size(), collection.getItem(6).size());
        assertEquals(GvImageList.TYPE, collection.getItem(6).getGvDataType());
        assertEquals(node.getComments().size(), collection.getItem(7).size());
        assertEquals(GvCommentList.TYPE, collection.getItem(7).getGvDataType());
        assertEquals(node.getLocations().size(), collection.getItem(8).size());
        assertEquals(GvLocationList.TYPE, collection.getItem(8).getGvDataType());
        assertEquals(node.getFacts().size(), collection.getItem(9).size());
        assertEquals(GvFactList.TYPE, collection.getItem(9).getGvDataType());
    }

    @SmallTest
    public void testGetGridDataReview() {
        ReviewNode unwrapped = ReviewMocker.newReviewNode(false);
        ReviewNode node = FactoryReview.createReviewNode(unwrapped);
        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());

        ViewerTreeData wrapper = new ViewerTreeData(getContext(), node);
        GvDataCollection collection = wrapper.getGridData();
        assertNotNull(collection);
        assertEquals(6, collection.size());

        assertEquals(tags.size(), collection.getItem(0).size());
        assertEquals(GvTagList.TYPE, collection.getItem(0).getGvDataType());
        assertEquals(unwrapped.getChildren().size(), collection.getItem(1).size());
        assertEquals(GvChildList.TYPE, collection.getItem(1).getGvDataType());
        assertEquals(node.getImages().size(), collection.getItem(2).size());
        assertEquals(GvImageList.TYPE, collection.getItem(2).getGvDataType());
        assertEquals(node.getComments().size(), collection.getItem(3).size());
        assertEquals(GvCommentList.TYPE, collection.getItem(3).getGvDataType());
        assertEquals(node.getLocations().size(), collection.getItem(4).size());
        assertEquals(GvLocationList.TYPE, collection.getItem(4).getGvDataType());
        assertEquals(node.getFacts().size(), collection.getItem(5).size());
        assertEquals(GvFactList.TYPE, collection.getItem(5).getGvDataType());
    }
}
