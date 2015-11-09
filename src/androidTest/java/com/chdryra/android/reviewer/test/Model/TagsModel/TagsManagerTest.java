/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.TagsModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.ReviewTag;
import com.chdryra.android.reviewer.Model.TagsModel.ReviewTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsManagerTest extends TestCase {
    private static final int NUM = 4;
    private TagsManager mTagsManager;

    @Override
    protected void setUp() throws Exception {
        mTagsManager = new TagsManager();
    }

    @SmallTest
    public void testTagAndGetTag() {
        GvTagList tags = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);
        Review review = ReviewMocker.newReview();

        ReviewTagCollection tagCollection = mTagsManager.getTags(review.getId());
        assertEquals(0, tagCollection.size());

        mTagsManager.tagReview(review.getId(), tags.toStringArray());

        tagCollection = mTagsManager.getTags(review.getId());
        assertEquals(tags.size(), tagCollection.size());
        for (int i = 0; i < tags.size(); ++i) {
            assertEquals(tags.getItem(i).getString(), tagCollection.getItem(i).getTag());
        }

        Review untagged = ReviewMocker.newReview();
        ReviewTagCollection empty = mTagsManager.getTags(untagged.getId());
        assertEquals(0, empty.size());
    }

    @SmallTest
    public void testTagsReview() {
        GvTagList tags1 = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);
        GvTagList tags2 = new GvTagList();
        int numShared = NUM / 2;
        for (int i = 0; i < numShared; ++i) {
            tags2.add(tags1.getItem(i));
        }

        GvTagList tagsUnshared = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE,
                NUM - numShared);
        for (int i = 0; i < tagsUnshared.size(); ++i) {
            tags2.add(tagsUnshared.getItem(i));
        }

        assertEquals(tags1.size(), tags2.size());

        Review review1 = ReviewMocker.newReview();
        Review review2 = ReviewMocker.newReview();

        mTagsManager.tagReview(review1.getId(), tags1.toStringArray());
        mTagsManager.tagReview(review2.getId(), tags2.toStringArray());

        ReviewTagCollection tagCollection1 = mTagsManager.getTags(review1.getId());
        ReviewTagCollection tagCollection2 = mTagsManager.getTags(review2.getId());
        assertEquals(tags1.size(), tagCollection1.size());
        assertEquals(tags2.size(), tagCollection2.size());

        for (int i = 0; i < tags1.size(); ++i) {
            ReviewTag tag1 = tagCollection1.getItem(i);
            ReviewTag tag2 = tagCollection2.getItem(i);

            assertTrue(tag1.tagsReview(review1.getId()));
            assertTrue(tag2.tagsReview(review2.getId()));

            if (i < numShared) {
                assertTrue(tag1.equals(tag2));
                assertTrue(tag1.tagsReview(review2.getId()));
                assertTrue(tag2.tagsReview(review1.getId()));
            } else {
                assertFalse(tag1.equals(tag2));
                assertFalse(tag1.tagsReview(review2.getId()));
                assertFalse(tag2.tagsReview(review1.getId()));
            }
        }
    }

    @SmallTest
    public void testGetTags() {
        GvTagList tags1 = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);
        GvTagList tags2 = new GvTagList();
        int numShared = NUM / 2;
        for (int i = 0; i < numShared; ++i) {
            tags2.add(tags1.getItem(i));
        }

        GvTagList tagsUnshared = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE,
                NUM - numShared);
        for (int i = 0; i < tagsUnshared.size(); ++i) {
            tags2.add(tagsUnshared.getItem(i));
        }

        Review review1 = ReviewMocker.newReview();
        Review review2 = ReviewMocker.newReview();

        mTagsManager.tagReview(review1.getId(), tags1.toStringArray());
        mTagsManager.tagReview(review2.getId(), tags2.toStringArray());

        ReviewTagCollection allTags = mTagsManager.getTags();
        assertEquals(tags1.size() + tagsUnshared.size(), allTags.size());
        GvTagList allocated = new GvTagList();
        for (int i = 0; i < NUM; ++i) {
            ReviewTag tag = allTags.getItem(i);
            GvTagList.GvTag gvTag = new GvTagList.GvTag(tag.getTag());
            assertTrue(tags1.contains(gvTag) || tagsUnshared.contains(gvTag));
            assertFalse(allocated.contains(gvTag));
            allocated.add(gvTag);
        }
    }

    @SmallTest
    public void testUntagReview() {
        GvTagList tags = (GvTagList) GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);
        Review review = ReviewMocker.newReview();

        ReviewTagCollection tagCollection = mTagsManager.getTags(review.getId());
        assertEquals(0, tagCollection.size());

        mTagsManager.tagReview(review.getId(), tags.toStringArray());

        tagCollection = mTagsManager.getTags(review.getId());
        assertEquals(tags.size(), tagCollection.size());
        for (int i = 0; i < tags.size(); ++i) {
            assertEquals(tags.getItem(i).getString(), tagCollection.getItem(i).getTag());
        }

        assertEquals(tags.size(), mTagsManager.getTags().size());
        for (ReviewTag tag : tagCollection) {
            assertTrue(tag.tagsReview(review.getId()));
            mTagsManager.untagReview(review.getId(), tag);
            assertFalse(tag.tagsReview(review.getId()));
        }

        assertEquals(0, mTagsManager.getTags().size());
    }
}
