/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.GVTagList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewTest extends AndroidTestCase {
    private Review           mReview;
    private ControllerReview mController;

    public void testGetReviewNode() {
        assertNotNull(mController.getReviewNode());
    }

    public void testGetId() {
        assertEquals(mReview.getId().toString(), mController.getId());
    }

    public void testGetSubject() {
        assertEquals(mReview.getSubject().get(), mController.getSubject());
    }

    public void testGetRating() {
        assertEquals(mReview.getRating().get(), mController.getRating());
    }

    public void testGetAuthor() {
        assertEquals(mReview.getAuthor().getName(), mController.getAuthor());
    }

    public void testGetPublishDate() {
        assertEquals(mReview.getPublishDate(), mController.getPublishDate());
    }

    public void testIsPublished() {
        assertEquals(mReview.isPublished(), mController.isPublished());
    }

    public void testAddTags() {
        GVTagList getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GVTagList tags = new GVTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertEquals(tags.size(), getTags.size());
        for (int i = 0; i < tagArray.length; ++i) {
            assertEquals(tagArray[i], getTags.getItem(i).get());
        }
    }

    public void testRemoveTags() {
        GVTagList getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GVTagList tags = new GVTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertEquals(tags.size(), getTags.size());

        mController.removeTags();
        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);
    }

    public void testHasData() {
        ReviewEditable editable = ReviewMocker.newReviewEditable();
        ControllerReview<Review> controller = new ControllerReview<Review>(editable);
        for (GVReviewDataList.GVType dataType : GVReviewDataList.GVType.values()) {
            assertFalse(controller.hasData(dataType));
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mReview = ReviewMocker.newReview();
        mController = new ControllerReview<Review>(mReview);
    }
}
