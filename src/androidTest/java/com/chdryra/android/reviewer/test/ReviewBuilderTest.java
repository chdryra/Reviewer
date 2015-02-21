/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.GvBuildReviewList;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewViewBuilder;
import com.chdryra.android.reviewer.TagsManager;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int NUM = 3;
    private ReviewViewBuilder mBuilder;

    public ReviewBuilderTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testSetGetSubject() {
        assertEquals(0, mBuilder.getSubject().length());
        String subject = RandomString.nextWord();
        mBuilder.setSubject(subject);
        assertNotNull(mBuilder.getSubject());
        assertEquals(subject, mBuilder.getSubject());
    }

    @SmallTest
    public void testSetGetRating() {
        assertEquals(0f, mBuilder.getRating());
        float rating = RandomRating.nextRating();
        mBuilder.setRating(rating);
        assertEquals(rating, mBuilder.getRating());
    }

    @SmallTest
    public void testGetAverageRating() {
        GvChildrenList children = GvDataMocker.newChildList(NUM);

        assertEquals(0f, mBuilder.getAverageRating());
        assertEquals(0f, mBuilder.getRating());
        mBuilder.setData(children);
        assertEquals(children.getAverageRating(), mBuilder.getAverageRating());
        assertEquals(0f, mBuilder.getRating());
    }

    @SmallTest
    public void testGetGridData() {
        GvBuildReviewList list = (GvBuildReviewList) mBuilder.getGridData();
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @SmallTest
    public void testGetAuthor() {
        assertEquals(Administrator.get(getActivity()).getAuthor(), mBuilder.getAuthor());
    }

    @SmallTest
    public void testGetPublishDate() {
        assertNull(mBuilder.getPublishDate());
    }

    @SmallTest
    public void testGetImages() {
        assertEquals(0, mBuilder.getImages().size());
        GvImageList images = GvDataMocker.newImageList(NUM);
        mBuilder.setData(images);
        assertEquals(images.size(), mBuilder.getImages().size());
        assertEquals(images, mBuilder.getImages());
    }

    @SmallTest
    public void testSetGetData() {
        assertEquals(0, mBuilder.getData(GvDataList.GvType.COMMENTS).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.FACTS).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.IMAGES).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.LOCATIONS).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.URLS).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.CHILDREN).size());
        assertEquals(0, mBuilder.getData(GvDataList.GvType.TAGS).size());
        assertNull(mBuilder.getData(GvDataList.GvType.SHARE));
        assertNull(mBuilder.getData(GvDataList.GvType.BUILD_REVIEW));
        assertNull(mBuilder.getData(GvDataList.GvType.FEED));

        GvDataList comments = GvDataMocker.getData(GvDataList.GvType.COMMENTS, NUM);
        GvDataList facts = GvDataMocker.getData(GvDataList.GvType.FACTS, NUM);
        GvDataList images = GvDataMocker.getData(GvDataList.GvType.IMAGES, NUM);
        GvDataList locations = GvDataMocker.getData(GvDataList.GvType.LOCATIONS, NUM);
        GvDataList urls = GvDataMocker.getData(GvDataList.GvType.URLS, NUM);
        GvDataList children = GvDataMocker.getData(GvDataList.GvType.CHILDREN, NUM);
        GvDataList tags = GvDataMocker.getData(GvDataList.GvType.TAGS, NUM);

        mBuilder.setData(comments);
        mBuilder.setData(facts);
        mBuilder.setData(images);
        mBuilder.setData(locations);
        mBuilder.setData(urls);
        mBuilder.setData(children);
        mBuilder.setData(tags);

        assertEquals(comments, mBuilder.getData(GvDataList.GvType.COMMENTS));
        assertEquals(facts, mBuilder.getData(GvDataList.GvType.FACTS));
        assertEquals(images, mBuilder.getData(GvDataList.GvType.IMAGES));
        assertEquals(locations, mBuilder.getData(GvDataList.GvType.LOCATIONS));
        assertEquals(urls, mBuilder.getData(GvDataList.GvType.URLS));
        assertEquals(children, mBuilder.getData(GvDataList.GvType.CHILDREN));
        assertEquals(tags, mBuilder.getData(GvDataList.GvType.TAGS));
        assertNull(mBuilder.getData(GvDataList.GvType.SHARE));
        assertNull(mBuilder.getData(GvDataList.GvType.BUILD_REVIEW));
        assertNull(mBuilder.getData(GvDataList.GvType.FEED));
    }

    @SmallTest
    public void testGetDataAdapter() {
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.COMMENTS));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.FACTS));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.IMAGES));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.LOCATIONS));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.URLS));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.CHILDREN));
        assertNotNull(mBuilder.getDataBuilder(GvDataList.GvType.TAGS));
    }

    @SmallTest
    public void testPublish() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        GvDataList comments = GvDataMocker.getData(GvDataList.GvType.COMMENTS, NUM);
        GvDataList facts = GvDataMocker.getData(GvDataList.GvType.FACTS, NUM);
        GvDataList images = GvDataMocker.getData(GvDataList.GvType.IMAGES, NUM);
        GvDataList locations = GvDataMocker.getData(GvDataList.GvType.LOCATIONS, NUM);
        GvDataList urls = GvDataMocker.getData(GvDataList.GvType.URLS, NUM);
        GvDataList children = GvDataMocker.getData(GvDataList.GvType.CHILDREN, NUM);
        GvDataList tags = GvDataMocker.getData(GvDataList.GvType.TAGS, NUM);

        mBuilder.setSubject(subject);
        mBuilder.setRating(rating);
        mBuilder.setData(comments);
        mBuilder.setData(facts);
        mBuilder.setData(images);
        mBuilder.setData(locations);
        mBuilder.setData(urls);
        mBuilder.setData(children);
        mBuilder.setData(tags);

        Author author = mBuilder.getAuthor();
        Date date = RandomDate.nextDate();

        Review published = mBuilder.publish(date);

        assertEquals(subject, published.getSubject().get());
        assertEquals(rating, published.getRating().get());
        assertEquals(date, published.getPublishDate());
        assertEquals(author, published.getAuthor());

        MdGvEquality.check(published.getComments(), (GvCommentList) comments);
        MdGvEquality.check(published.getFacts(), (GvFactList) facts);
        MdGvEquality.check(published.getImages(), (GvImageList) images);
        MdGvEquality.check(published.getLocations(), (GvLocationList) locations);
        MdGvEquality.check(published.getUrls(), (GvUrlList) urls);

        TagsManager.ReviewTagCollection tagsPublished = TagsManager.getTags(published);
        assertEquals(tags.size(), tagsPublished.size());
        for (int j = 0; j < tags.size(); ++j) {
            GvTagList.GvTag tag = (GvTagList.GvTag) tags.getItem(j);
            assertEquals(tag.get(), tagsPublished.getItem(j).get());
        }

        RCollectionReview<ReviewNode> childNodes = published.getReviewNode().getChildren();
        assertEquals(children.size(), childNodes.size());
        for (int i = 0; i < children.size(); ++i) {
            ReviewNode childNode = childNodes.getItem(i);
            GvChildrenList.GvChildReview child = (GvChildrenList.GvChildReview) children.getItem(i);
            assertEquals(child.getSubject(), childNode.getSubject().get());
            assertEquals(child.getRating(), childNode.getRating().get());
            assertEquals(published, childNode.getParent());
            TagsManager.ReviewTagCollection tagsChild = TagsManager.getTags(childNode);
            assertEquals(tags.size(), tagsChild.size());
            for (int j = 0; j < tags.size(); ++j) {
                GvTagList.GvTag tag = (GvTagList.GvTag) tags.getItem(j);
                assertEquals(tag.get(), tagsChild.getItem(j).get());
            }
        }
    }

    @SmallTest
    public void testGetSetRatingAverage() {
        assertFalse(mBuilder.isRatingAverage());
        mBuilder.setRatingIsAverage(true);
        assertTrue(mBuilder.isRatingAverage());
    }

    @Override
    protected void setUp() throws Exception {
        getInstrumentation().setInTouchMode(false);

        Intent i = new Intent();
        ActivityReviewView.packParameters(GvDataList.GvType.BUILD_REVIEW, false, i);

        setActivityIntent(i);

        mBuilder = new ReviewViewBuilder(getActivity());
    }
}
