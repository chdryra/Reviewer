/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.TagsModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;
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
        GvTagList tags = (GvTagList) GvDataMocker.getData(GvTag.TYPE, NUM);
        Review review = ReviewMocker.newReview();

        ItemTagCollection tagCollection = mTagsManager.getTags(review.getMdReviewId());
        assertEquals(0, tagCollection.size());

        mTagsManager.tagItem(review.getMdReviewId(), tags.toStringArray());

        tagCollection = mTagsManager.getTags(review.getMdReviewId());
        assertEquals(tags.size(), tagCollection.size());
        for (int i = 0; i < tags.size(); ++i) {
            assertEquals(tags.getItem(i).getString(), tagCollection.getItemTag(i).getTag());
        }

        Review untagged = ReviewMocker.newReview();
        ItemTagCollection empty = mTagsManager.getTags(untagged.getMdReviewId());
        assertEquals(0, empty.size());
    }

    @SmallTest
    public void testTagsReview() {
        GvTagList tags1 = (GvTagList) GvDataMocker.getData(GvTag.TYPE, NUM);
        GvTagList tags2 = new GvTagList();
        int numShared = NUM / 2;
        for (int i = 0; i < numShared; ++i) {
            tags2.add(tags1.getItem(i));
        }

        GvTagList tagsUnshared = (GvTagList) GvDataMocker.getData(GvTag.TYPE,
                NUM - numShared);
        for (int i = 0; i < tagsUnshared.size(); ++i) {
            tags2.add(tagsUnshared.getItem(i));
        }

        assertEquals(tags1.size(), tags2.size());

        Review review1 = ReviewMocker.newReview();
        Review review2 = ReviewMocker.newReview();

        mTagsManager.tagItem(review1.getMdReviewId(), tags1.toStringArray());
        mTagsManager.tagItem(review2.getMdReviewId(), tags2.toStringArray());

        ItemTagCollection tagCollection1 = mTagsManager.getTags(review1.getMdReviewId());
        ItemTagCollection tagCollection2 = mTagsManager.getTags(review2.getMdReviewId());
        assertEquals(tags1.size(), tagCollection1.size());
        assertEquals(tags2.size(), tagCollection2.size());

        for (int i = 0; i < tags1.size(); ++i) {
            ItemTag tag1 = tagCollection1.getItemTag(i);
            ItemTag tag2 = tagCollection2.getItemTag(i);

            assertTrue(tag1.tagsItem(review1.getMdReviewId()));
            assertTrue(tag2.tagsItem(review2.getMdReviewId()));

            if (i < numShared) {
                assertTrue(tag1.equals(tag2));
                assertTrue(tag1.tagsItem(review2.getMdReviewId()));
                assertTrue(tag2.tagsItem(review1.getMdReviewId()));
            } else {
                assertFalse(tag1.equals(tag2));
                assertFalse(tag1.tagsItem(review2.getMdReviewId()));
                assertFalse(tag2.tagsItem(review1.getMdReviewId()));
            }
        }
    }

    @SmallTest
    public void testGetTags() {
        GvTagList tags1 = (GvTagList) GvDataMocker.getData(GvTag.TYPE, NUM);
        GvTagList tags2 = new GvTagList();
        int numShared = NUM / 2;
        for (int i = 0; i < numShared; ++i) {
            tags2.add(tags1.getItem(i));
        }

        GvTagList tagsUnshared = (GvTagList) GvDataMocker.getData(GvTag.TYPE,
                NUM - numShared);
        for (int i = 0; i < tagsUnshared.size(); ++i) {
            tags2.add(tagsUnshared.getItem(i));
        }

        Review review1 = ReviewMocker.newReview();
        Review review2 = ReviewMocker.newReview();

        mTagsManager.tagItem(review1.getMdReviewId(), tags1.toStringArray());
        mTagsManager.tagItem(review2.getMdReviewId(), tags2.toStringArray());

        ItemTagCollection allTags = mTagsManager.getTags();
        assertEquals(tags1.size() + tagsUnshared.size(), allTags.size());
        GvTagList allocated = new GvTagList();
        for (int i = 0; i < NUM; ++i) {
            ItemTag tag = allTags.getItemTag(i);
            GvTag gvTag = new GvTag(tag.getTag());
            assertTrue(tags1.contains(gvTag) || tagsUnshared.contains(gvTag));
            assertFalse(allocated.contains(gvTag));
            allocated.add(gvTag);
        }
    }

    @SmallTest
    public void testUntagReview() {
        GvTagList tags = (GvTagList) GvDataMocker.getData(GvTag.TYPE, NUM);
        Review review = ReviewMocker.newReview();

        ItemTagCollection tagCollection = mTagsManager.getTags(review.getMdReviewId());
        assertEquals(0, tagCollection.size());

        mTagsManager.tagItem(review.getMdReviewId(), tags.toStringArray());

        tagCollection = mTagsManager.getTags(review.getMdReviewId());
        assertEquals(tags.size(), tagCollection.size());
        for (int i = 0; i < tags.size(); ++i) {
            assertEquals(tags.getItem(i).getString(), tagCollection.getItemTag(i).getTag());
        }

        assertEquals(tags.size(), mTagsManager.getTags().size());
        for (ItemTag tag : tagCollection) {
            assertTrue(tag.tagsItem(review.getMdReviewId()));
            mTagsManager.untagItem(review.getMdReviewId(), tag);
            assertFalse(tag.tagsItem(review.getMdReviewId()));
        }

        assertEquals(0, mTagsManager.getTags().size());
    }
}
