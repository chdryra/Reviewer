/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.ControllerReviewNode;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdDataList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.ReviewNode;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerTester<T extends Review> {
    private static final int                 NUMDATA          = 50;
    private final static GvDataList.GvType[] sRelevantGvTypes = new GvDataList
            .GvType[]{GvDataList.GvType.FACTS,
            GvDataList.GvType.URLS,
            GvDataList.GvType.LOCATIONS, GvDataList.GvType.COMMENTS,
            GvDataList.GvType.IMAGES, GvDataList.GvType.TAGS};
    private ControllerReview<T> mController;
    private T                   mReview;
    private MdDataMocker<T>     mMdDataMocker;

    public ControllerTester(ControllerReview<T> controller, T review) {
        mController = controller;
        mReview = review;
        mMdDataMocker = new MdDataMocker<>(mReview);
    }

    public void testBasicControllerMethods() {
        testGetReviewNode();
        testGetId();
        testGetSubject();
        testGetRating();
        testGetAuthor();
        testGetPublishDate();
        testIsPublished();
        testAddAndGetTags();
        testRemoveTags();
        testHasData();
        testGetData();
    }

    public void testGetReviewNode() {
        Assert.assertNotNull(mController.getReviewNode());
    }

    public void testGetId() {
        Assert.assertEquals(mReview.getId().toString(), mController.getId());
    }

    public void testGetSubject() {
        Assert.assertEquals(mReview.getSubject().get(), mController.getSubject());
    }

    public void testGetRating() {
        Assert.assertEquals(mReview.getRating().get(), mController.getRating());
    }

    public void testGetAuthor() {
        Assert.assertEquals(mReview.getAuthor().getName(), mController.getAuthor());
    }

    public void testGetPublishDate() {
        Assert.assertEquals(mReview.getPublishDate(), mController.getPublishDate());
    }

    public void testIsPublished() {
        Assert.assertEquals(mReview.isPublished(), mController.isPublished());
    }

    public void testAddAndGetTags() {
        GvTagList getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        Assert.assertEquals(0, getTags.size());

        String[] tagArray = setAndGetTags();

        getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        Assert.assertEquals(tagArray.length, getTags.size());
        for (int i = 0; i < tagArray.length; ++i) {
            Assert.assertEquals(tagArray[i], getTags.getItem(i).get());
        }
    }

    public void testRemoveTags() {
        String[] tagArray = setAndGetTags();
        GvTagList getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        Assert.assertEquals(tagArray.length, getTags.size());

        mController.removeTags();

        getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        Assert.assertEquals(0, getTags.size());
    }

    public void testGetData() {
        ReviewEditable editable = ReviewMocker.newReviewEditable();
        ControllerReview<Review> controller = new ControllerReview<Review>(editable);

        GvDataList.GvType dataType;

        //Comments
        dataType = GvDataList.GvType.COMMENTS;
        Assert.assertEquals(0, controller.getData(dataType).size());
        MdGvEquality.check((MdCommentList) setAndGetMdData(dataType, editable),
                (GvCommentList) controller.getData(dataType));

        //Facts
        dataType = GvDataList.GvType.FACTS;
        Assert.assertEquals(0, controller.getData(dataType).size());
        MdGvEquality.check((MdFactList) setAndGetMdData(dataType, editable),
                (GvFactList) controller.getData(dataType));


        //Images
        dataType = GvDataList.GvType.IMAGES;
        Assert.assertEquals(0, controller.getData(dataType).size());
        MdGvEquality.check((MdImageList) setAndGetMdData(dataType, editable),
                (GvImageList) controller.getData(dataType));


        //Locations
        dataType = GvDataList.GvType.LOCATIONS;
        Assert.assertEquals(0, controller.getData(dataType).size());
        MdGvEquality.check((MdLocationList) setAndGetMdData(dataType, editable),
                (GvLocationList) controller.getData(dataType));

        //Urls
        dataType = GvDataList.GvType.URLS;
        Assert.assertEquals(0, controller.getData(dataType).size());
        MdGvEquality.check((MdUrlList) setAndGetMdData(dataType, editable),
                (GvUrlList) controller.getData(dataType));

        //Tags
        //see testAddAndGetTags()
    }

    public void testHasData() {
        for (GvDataList.GvType dataType : sRelevantGvTypes) {
            testHasData(dataType);
        }
    }

    private String[] setAndGetTags() {
        String[] tagArray = getRandomTags();

        GvTagList tags = new GvTagList();
        for (String tag : tagArray) {
            tags.add(tag);
        }

        mController.addTags(tags);

        return tagArray;
    }

    private String[] getRandomTags() {
        String[] tags = new String[NUMDATA];
        for (int i = 0; i < NUMDATA; ++i) {
            tags[i] = RandomStringGenerator.nextWord();
        }

        return tags;
    }

    private void testHasData(GvDataList.GvType dataType) {
        ReviewNode node = ReviewMocker.newReviewNode();
        ReviewEditable editable = (ReviewEditable) node.getReview();
        ControllerReviewNode controller = new ControllerReviewNode(node);

        testOnlyHasData(controller, null);

        if (dataType != GvDataList.GvType.TAGS) {
            setAndGetMdData(dataType, editable);
        } else {
            GvTagList tags = new GvTagList();
            for (int i = 0; i < NUMDATA; ++i) {
                tags.add(RandomStringGenerator.nextWord());
            }

            controller.addTags(tags);
        }

        testOnlyHasData(controller, dataType);

        if (dataType != GvDataList.GvType.TAGS) {
            ReviewGetSetDelete.deleteData(dataType, editable);
        } else {
            controller.removeTags();
        }

        testOnlyHasData(controller, null);
    }

    private MdDataList setAndGetMdData(GvDataList.GvType dataType, ReviewEditable editable) {
        MdDataList data = mMdDataMocker.getData(dataType, NUMDATA);
        Assert.assertTrue(data.size() > 0);

        ReviewGetSetDelete.setData(dataType, editable, data);

        return data;
    }

    private void testOnlyHasData(ControllerReview controller, GvDataList.GvType dataType) {
        for (GvDataList.GvType gv : sRelevantGvTypes) {
            if (gv == dataType) {
                Assert.assertTrue(controller.hasData(gv));
            } else {
                Assert.assertFalse(controller.hasData(gv));
            }
        }
    }
}
