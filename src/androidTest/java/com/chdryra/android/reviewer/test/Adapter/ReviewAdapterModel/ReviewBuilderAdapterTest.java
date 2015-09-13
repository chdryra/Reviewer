/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Screens.BuildScreen;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderAdapterTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int          NUM   = 3;
    private static final ArrayList<GvDataType<? extends GvData>> TYPES = GvDataMocker.TYPES;
    private ReviewBuilderAdapter mBuilder;

    public ReviewBuilderAdapterTest() {
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
        GvChildReviewList children = GvDataMocker.newChildList(NUM, false);

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
            ReviewBuilderAdapter.DataBuilderAdapter builder = mBuilder.getDataBuilder(dataType);
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
            ReviewBuilderAdapter.DataBuilderAdapter builder = mBuilder.getDataBuilder(dataType);
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
            ReviewBuilderAdapter.DataBuilderAdapter builder = mBuilder.getDataBuilder(dataType);
            if (dataType == GvChildReviewList.GvChildReview.TYPE) {
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

        ReviewBuilderAdapter.DataBuilderAdapter imageBuilder =
                mBuilder.getDataBuilder(GvImageList.GvImage.TYPE);
        imageBuilder.deleteAll();
        for (GvImageList.GvImage image : newImages) {
            imageBuilder.add(image);
        }

        assertEquals(images, mBuilder.getCovers());
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mBuilder.getDataBuilder(dataType);
            if (dataType == GvImageList.GvImage.TYPE) {
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
        assertNotNull(mBuilder.getDataBuilder(GvCommentList.GvComment.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvFactList.GvFact.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvImageList.GvImage.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvLocationList.GvLocation.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvUrlList.GvUrl.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvChildReviewList.GvChildReview.TYPE));
        assertNotNull(mBuilder.getDataBuilder(GvTagList.GvTag.TYPE));
    }

    @SmallTest
    public void testPublish() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        GvDataList comments = GvDataMocker.getData(GvCommentList.GvComment.TYPE, NUM);
        GvDataList facts = GvDataMocker.getData(GvFactList.GvFact.TYPE, NUM);
        GvDataList images = GvDataMocker.getData(GvImageList.GvImage.TYPE, NUM);
        GvDataList locations = GvDataMocker.getData(GvLocationList.GvLocation.TYPE, NUM);
        GvDataList urls = GvDataMocker.getData(GvUrlList.GvUrl.TYPE, NUM);
        GvDataList children = GvDataMocker.getData(GvChildReviewList.GvChildReview.TYPE, NUM);
        GvDataList tags = GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);

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
        assertEquals(date, published.getPublishDate().getDate());

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
            GvChildReviewList.GvChildReview child = (GvChildReviewList.GvChildReview) children
                    .getItem(i);
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
        ReviewBuilderAdapter.DataBuilderAdapter builder = mBuilder.getDataBuilder(data
                .getGvDataType());
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            assertTrue(builder.add(datum));
        }
        builder.setData();
    }
}
