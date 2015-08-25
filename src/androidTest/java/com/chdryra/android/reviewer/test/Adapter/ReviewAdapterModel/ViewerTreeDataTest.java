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
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
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
        GvTagList tags = GvDataMocker.newTagList(3, false);
        TagsManager.tag(node.getId(), tags.toStringArray());

        ViewerTreeData wrapper = new ViewerTreeData(node);
        GvList collection = wrapper.getGridData();
        assertNotNull(collection);
        assertEquals(10, collection.size());

        assertEquals(node.getChildren().size(), ((GvDataCollection) collection.getItem(0)).size());
        assertEquals(GvReviewOverviewList.TYPE, collection.getItem(0).getGvDataType());
        assertEquals(node.getChildren().size(), ((GvDataCollection) collection.getItem(1)).size());
        assertEquals(GvAuthorList.GvAuthor.TYPE, collection.getItem(1).getGvDataType()
                .getElementType());
        assertEquals(node.getChildren().size(), ((GvDataCollection) collection.getItem(2)).size());
        assertEquals(GvSubjectList.GvSubject.TYPE, collection.getItem(2).getGvDataType()
                .getElementType());
        assertEquals(node.getChildren().size(), ((GvDataCollection) collection.getItem(3)).size());
        assertEquals(GvDateList.GvDate.TYPE, collection.getItem(3).getGvDataType().getElementType
                ());
        assertEquals(tags.size(), ((GvDataCollection) collection.getItem(4)).size());
        assertEquals(GvTagList.GvTag.TYPE, collection.getItem(4).getGvDataType().getElementType());
        assertEquals(0, ((GvDataCollection) collection.getItem(5)).size());
        assertEquals(GvChildReviewList.GvChildReview.TYPE, collection.getItem(5).getGvDataType()
                .getElementType());
        assertEquals(node.getImages().size(), ((GvDataCollection) collection.getItem(6)).size());
        assertEquals(GvImageList.GvImage.TYPE, collection.getItem(6).getGvDataType()
                .getElementType());
        assertEquals(node.getComments().size(), ((GvDataCollection) collection.getItem(7)).size());
        assertEquals(GvCommentList.GvComment.TYPE, collection.getItem(7).getGvDataType()
                .getElementType());
        assertEquals(node.getLocations().size(), ((GvDataCollection) collection.getItem(8)).size());
        assertEquals(GvLocationList.GvLocation.TYPE, collection.getItem(8).getGvDataType()
                .getElementType());
        assertEquals(node.getFacts().size(), ((GvDataCollection) collection.getItem(9)).size());
        assertEquals(GvFactList.GvFact.TYPE, collection.getItem(9).getGvDataType().getElementType());
    }

    @SmallTest
    public void testGetGridDataReview() {
        ReviewNode unwrapped = ReviewMocker.newReviewNode(false);
        ReviewNode node = FactoryReview.createReviewNode(unwrapped);
        GvTagList tags = GvDataMocker.newTagList(3, false);
        TagsManager.tag(node.getId(), tags.toStringArray());

        ViewerTreeData wrapper = new ViewerTreeData(node);
        GvList collection = wrapper.getGridData();
        assertNotNull(collection);
        assertEquals(6, collection.size());

        assertEquals(tags.size(), ((GvDataCollection) collection.getItem(0)).size());
        assertEquals(GvTagList.GvTag.TYPE, collection.getItem(0).getGvDataType().getElementType());
        assertEquals(unwrapped.getChildren().size(), ((GvDataCollection) collection.getItem(1))
                .size());
        assertEquals(GvChildReviewList.GvChildReview.TYPE, collection.getItem(1).getGvDataType()
                .getElementType());
        assertEquals(node.getImages().size(), ((GvDataCollection) collection.getItem(2)).size());
        assertEquals(GvImageList.GvImage.TYPE, collection.getItem(2).getGvDataType()
                .getElementType());
        assertEquals(node.getComments().size(), ((GvDataCollection) collection.getItem(3)).size());
        assertEquals(GvCommentList.GvComment.TYPE, collection.getItem(3).getGvDataType()
                .getElementType());
        assertEquals(node.getLocations().size(), ((GvDataCollection) collection.getItem(4)).size());
        assertEquals(GvLocationList.GvLocation.TYPE, collection.getItem(4).getGvDataType()
                .getElementType());
        assertEquals(node.getFacts().size(), ((GvDataCollection) collection.getItem(5)).size());
        assertEquals(GvFactList.GvFact.TYPE, collection.getItem(5).getGvDataType().getElementType());
    }
}
