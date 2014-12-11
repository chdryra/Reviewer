/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ControllerReviewTreeEditable;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvSubjectRatingList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewTreeEditable;
import com.chdryra.android.reviewer.test.TestUtils.ChildrenMocker;
import com.chdryra.android.reviewer.test.TestUtils.ControllerEditableTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewTreeEditableTest extends AndroidTestCase {
    private static final int NUMDATA = 50;
    private ReviewTreeEditable           mNode;
    private ControllerReviewTreeEditable mController;
    private ControllerEditableTester     mTester;

    @SmallTest
    public void testGetReviewNode() {
        mTester.testGetReviewNode();
    }

    @SmallTest
    public void testGetDataChildren() {
        ChildrenMocker.checkEquality(mNode.getChildren(),
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));

        RCollectionReview<ReviewNode> setData = ChildrenMocker.getMockNodeChildren();
        for (ReviewNode child : setData) {
            mNode.addChild(child);
        }

        ChildrenMocker.checkEquality(setData,
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));
    }

    @SmallTest
    public void testGetDataOther() {
        mTester.testGetData();
    }

    @SmallTest
    public void testSetDataChildren() {
        ChildrenMocker.checkEquality(mNode.getChildren(),
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));

        GvSubjectRatingList setData = ChildrenMocker.getMockGvChildren();
        mController.setData(setData);

        ChildrenMocker.checkEquality(mNode.getChildren(), setData);
        ChildrenMocker.checkEquality(setData,
                (GvSubjectRatingList) mController.getData(GvDataList.GvType.CHILDREN));
    }

    @SmallTest
    public void testSetDataOther() {
        mTester.testSetData();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mNode = ReviewMocker.newReviewTreeEditable();
        mController = new ControllerReviewTreeEditable(mNode);
        mTester = new ControllerEditableTester(mController, (ReviewEditable) mNode.getReview());
    }

//    @SmallTest
//    public void testPublishAndTag() {
//        assertFalse(mNode.isPublished());
//
//        GvTagList tags = (GvTagList)GvDataMocker.getData(GvDataList.GvType.TAGS, NUMDATA);
//        assertEquals(NUMDATA, tags.size());
//        mController.addTags(tags);
//
//        Author author = new Author("Rizwan Choudrey");
//        PublisherReviewTree publisher = new PublisherReviewTree(author);
//
//        ReviewNode published = mController.publishAndTag(publisher);
//        ReviewEquality.checkData(mNode, published);
//        assertTrue(published.isPublished());
//        assertEquals(author, published.getAuthor());
//        assertNotNull(published.getPublishDate());
//        TagsManager.ReviewTagCollection tagsP = TagsManager.getTags(published);
//        assertEquals(tags.size(), tagsP.size());
//        for(int i = 0; i < tags.size(); ++i)
//            assertEquals(tags.getItem(i).get(), tagsP.getItem(i).get());
//    }
}
