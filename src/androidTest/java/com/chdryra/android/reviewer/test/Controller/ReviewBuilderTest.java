/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewBuilder;
import com.chdryra.android.reviewer.Model.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.ActivityReviewView;
import com.chdryra.android.reviewer.View.BuildScreen;
import com.chdryra.android.reviewer.View.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;
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
    private static final int          NUM   = 3;
    private static final GvDataType[] TYPES = GvDataMocker.DATATYPES;
    private ReviewBuilder mBuilder;

    public ReviewBuilderTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testSetGetSubject() {
        assertEquals(0, mBuilder.getSubject().length());
        String subject = RandomString.nextWord();
        mBuilder.setSubject(subject);
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
        GvChildList children = GvDataMocker.newChildList(NUM, false);

        assertEquals(0f, mBuilder.getAverageRating());
        assertEquals(0f, mBuilder.getRating());
        setBuilderData(children);
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
    public void testGetImages() {
        assertEquals(0, mBuilder.getCovers().size());
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);
        assertEquals(images.size(), mBuilder.getCovers().size());
        assertEquals(images, mBuilder.getCovers());
    }

    @SmallTest
    public void testDataBuilderSetGetSubject() {
        assertEquals("", mBuilder.getSubject());
        String subject = RandomString.nextWord();
        for (GvDataType dataType : TYPES) {
            ReviewBuilder.DataBuilder builder = mBuilder.getDataBuilder(dataType);
            assertEquals("", builder.getSubject());
            mBuilder.setSubject(subject);
            assertEquals(subject, builder.getSubject());
            mBuilder.setSubject("");
            assertEquals("", builder.getSubject());
            builder.setSubject(subject);
            assertEquals(subject, builder.getSubject());
            assertEquals(subject, mBuilder.getSubject());
            mBuilder.setSubject("");
        }
    }

    @SmallTest
    public void testDataBuilderSetGetRating() {
        assertEquals(0f, mBuilder.getRating());
        float rating = RandomRating.nextRating();
        for (GvDataType dataType : TYPES) {
            ReviewBuilder.DataBuilder builder = mBuilder.getDataBuilder(dataType);
            assertEquals(0f, builder.getRating());
            mBuilder.setRating(rating);
            assertEquals(rating, builder.getRating());
            mBuilder.setRating(0f);
            assertEquals(0f, builder.getRating());
            builder.setRating(rating);
            assertEquals(rating, builder.getRating());
            assertEquals(rating, mBuilder.getRating());
            mBuilder.setRating(0f);
        }
    }

    @SmallTest
    public void testDataBuilderGetParentBuilder() {
        for (GvDataType dataType : TYPES) {
            assertEquals(mBuilder, mBuilder.getDataBuilder(dataType).getParentBuilder());
        }
    }

    @SmallTest
    public void testDataBuilderGetAverageRating() {
        while (mBuilder.getAverageRating() == mBuilder.getRating()) {
            setBuilderData(GvDataMocker.newChildList(NUM, false));
        }

        for (GvDataType dataType : TYPES) {
            ReviewBuilder.DataBuilder builder = mBuilder.getDataBuilder(dataType);
            if (dataType == GvChildList.TYPE) {
                assertEquals(mBuilder.getAverageRating(), builder.getAverageRating());
            } else {
                assertEquals(mBuilder.getRating(), builder.getAverageRating());
            }
        }
    }

    @SmallTest
    public void testDataBuilderGetImages() {
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        GvImageList newImages = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);

        ReviewBuilder.DataBuilder imageBuilder = mBuilder.getDataBuilder(GvImageList.TYPE);
        imageBuilder.deleteAll();
        for (GvImageList.GvImage image : newImages) {
            imageBuilder.add(image);
        }

        assertEquals(images, mBuilder.getCovers());
        for (GvDataType dataType : TYPES) {
            ReviewBuilder.DataBuilder builder = mBuilder.getDataBuilder(dataType);
            if (dataType == GvImageList.TYPE) {
                assertEquals(newImages, builder.getCovers());
            } else {
                assertEquals(images, builder.getCovers());
            }
        }
    }

    @SmallTest
    public void testDataBuilderGetSetData() {
        for (GvDataType dataType : TYPES) {
            assertEquals(0, getBuilderData(dataType).size());
        }

        for (GvDataType dataType : TYPES) {
            GvDataList data = GvDataMocker.getData(dataType, NUM);
            setBuilderData(data);
            assertEquals(data, getBuilderData(dataType));
        }
    }

    @SmallTest
    public void testGetDataBuilder() {
        assertNotNull(mBuilder.getDataBuilder(GvCommentList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvFactList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvImageList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvLocationList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvUrlList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvChildList.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvTagList.TYPE));
    }

    @SmallTest
    public void testPublish() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        GvDataList comments = GvDataMocker.getData(GvCommentList.TYPE, NUM);
        GvDataList facts = GvDataMocker.getData(GvFactList.TYPE, NUM);
        GvDataList images = GvDataMocker.getData(GvImageList.TYPE, NUM);
        GvDataList locations = GvDataMocker.getData(GvLocationList.TYPE, NUM);
        GvDataList urls = GvDataMocker.getData(GvUrlList.TYPE, NUM);
        GvDataList children = GvDataMocker.getData(GvChildList.TYPE, NUM);
        GvDataList tags = GvDataMocker.getData(GvTagList.TYPE, NUM);

        mBuilder.setSubject(subject);
        mBuilder.setRating(rating);
        setBuilderData(comments);
        setBuilderData(facts);
        setBuilderData(images);
        setBuilderData(locations);
        setBuilderData(urls);
        setBuilderData(children);
        setBuilderData(tags);

        Date date = RandomDate.nextDate();
        ReviewNode published = mBuilder.publish(PublishDate.then(date.getTime()));

        assertEquals(subject, published.getSubject().get());
        assertEquals(rating, published.getRating().get());
        assertEquals(date, published.getPublishDate());

        MdGvEquality.check(published.getComments(), (GvCommentList) comments);
        MdGvEquality.check(published.getFacts(), (GvFactList) facts);
        MdGvEquality.check(published.getImages(), (GvImageList) images);
        MdGvEquality.check(published.getLocations(), (GvLocationList) locations);

        TagsManager.ReviewTagCollection tagsPublished = TagsManager.getTags(published.getId());
        assertEquals(tags.size(), tagsPublished.size());
        for (int j = 0; j < tags.size(); ++j) {
            GvTagList.GvTag tag = (GvTagList.GvTag) tags.getItem(j);
            assertEquals(tag.get(), tagsPublished.getItem(j).get());
        }

        ReviewIdableList<ReviewNode> childNodes = published.getChildren();
        assertEquals(children.size(), childNodes.size());
        for (int i = 0; i < children.size(); ++i) {
            ReviewNode childNode = childNodes.getItem(i);
            GvChildList.GvChildReview child = (GvChildList.GvChildReview) children.getItem(i);
            assertEquals(child.getSubject(), childNode.getSubject().get());
            assertEquals(child.getRating(), childNode.getRating().get());
            assertEquals(published, childNode.getParent());
            TagsManager.ReviewTagCollection tagsChild = TagsManager.getTags(childNode.getId());
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
        Context context = getInstrumentation().getTargetContext();
        Administrator admin = Administrator.get(context);
        admin.newReviewBuilder();
        admin.packView(BuildScreen.newScreen(context), i);
        setActivityIntent(i);

        mBuilder = admin.getReviewBuilder();
    }

    private GvDataList getBuilderData(GvDataType dataType) {
        return mBuilder.getDataBuilder(dataType).getGridData();
    }

    private void setBuilderData(GvDataList data) {
        ReviewBuilder.DataBuilder builder = mBuilder.getDataBuilder(data.getGvDataType());
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            builder.add(datum);
        }
        builder.setData();
    }
}
