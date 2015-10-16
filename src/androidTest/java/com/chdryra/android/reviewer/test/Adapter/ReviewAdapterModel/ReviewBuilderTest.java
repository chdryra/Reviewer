/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderTest extends AndroidTestCase {
    private static final int NUM = 3;
    private static final ArrayList<GvDataType<? extends GvData>> TYPES = GvDataMocker.TYPES;

    private Author mAuthor;
    private TagsManager mTagsManager;
    private ReviewBuilder mBuilder;
    private ReviewBuilderAdapter mAdapter;

    @SmallTest
    public void testGetSetRatingAverage() {
        mAdapter.setRatingIsAverage(false);
        GvCriterionList criteria;
        do {
            criteria = GvDataMocker.newChildList(NUM, false);
            setBuilderData(criteria);
        } while (criteria.getAverageRating() == mAdapter.getRating());

        assertFalse(criteria.getAverageRating() == mAdapter.getRating());
        mAdapter.setRatingIsAverage(true);
        assertTrue(criteria.getAverageRating() == mAdapter.getRating());
    }

    @SmallTest
    public void testGetImageChooser() {
        assertNotNull(mAdapter.getImageChooser(getActivity()));
    }

    @SmallTest
    public void testGetDataBuilder() {
        assertNotNull(mAdapter.getDataBuilder(GvCommentList.GvComment.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvFactList.GvFact.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvImageList.GvImage.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvLocationList.GvLocation.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvUrlList.GvUrl.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvCriterionList.GvCriterion.TYPE));
        assertNotNull(mAdapter.getDataBuilder(GvTagList.GvTag.TYPE));
    }

    @SmallTest
    public void testHasTags() {
        assertFalse(mAdapter.hasTags());
        setBuilderData(GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM));
        assertTrue(mAdapter.hasTags());
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
        GvDataList children = GvDataMocker.getData(GvCriterionList.GvCriterion.TYPE, NUM);
        GvDataList tags = GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM);

        mAdapter.setSubject(subject);
        mAdapter.setRating(rating);
        setBuilderData(comments);
        setBuilderData(facts);
        setBuilderData(images);
        setBuilderData(locations);
        setBuilderData(urls);
        setBuilderData(children);
        setBuilderData(tags);

        Review published = mAdapter.publish();

        assertEquals(subject, published.getSubject().get());
        assertEquals(rating, published.getRating().getValue());

        MdGvEquality.check(published.getComments(), (GvCommentList) comments);
        MdGvEquality.check(published.getFacts(), (GvFactList) facts);
        MdGvEquality.check(published.getImages(), (GvImageList) images);
        MdGvEquality.check(published.getLocations(), (GvLocationList) locations);

        TagsManager.ReviewTagCollection tagsPublished = TagsManager.getTags(getActivity(),
                published.getId());
        assertEquals(tags.size(), tagsPublished.size());
        for (int j = 0; j < tags.size(); ++j) {
            GvTagList.GvTag tag = (GvTagList.GvTag) tags.getItem(j);
            assertEquals(tag.get(), tagsPublished.getItem(j).get());
        }

        MdCriterionList criteria = published.getCriteria();
        assertEquals(children.size(), criteria.size());
        for (int i = 0; i < children.size(); ++i) {
            MdCriterionList.MdCriterion criterion = criteria.getItem(i);
            assertEquals(published.getId(), criterion.getReviewId());
            Review childReview = criterion.getReview();

            GvCriterionList.GvCriterion child = (GvCriterionList.GvCriterion) children
                    .getItem(i);
            assertEquals(child.getSubject(), childReview.getSubject().get());
            assertEquals(child.getRating(), childReview.getRating().getValue());
            TagsManager.ReviewTagCollection tagsChild = TagsManager.getTags(getActivity(),
                    childReview.getId());
            assertEquals(tags.size(), tagsChild.size());
            for (int j = 0; j < tags.size(); ++j) {
                GvTagList.GvTag tag = (GvTagList.GvTag) tags.getItem(j);
                assertEquals(tag.get(), tagsChild.getItem(j).get());
            }
        }
    }

    @SmallTest
    public void testSetGetSubject() {
        assertEquals(0, mAdapter.getSubject().length());
        String subject = RandomString.nextWord();
        mAdapter.setSubject(subject);
        assertEquals(subject, mAdapter.getSubject());
    }

    @SmallTest
    public void testSetGetRating() {
        assertEquals(0f, mAdapter.getRating());
        float rating = RandomRating.nextRating();
        mAdapter.setRating(rating);
        assertEquals(rating, mAdapter.getRating());
    }

    @SmallTest
    public void testGetGridData() {
        GvBuildReviewList list = (GvBuildReviewList) mAdapter.getGridData();
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @SmallTest
    public void testGetImages() {
        assertEquals(0, mAdapter.getCovers().size());
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);
        assertEquals(images.size(), mAdapter.getCovers().size());
        assertEquals(images, mAdapter.getCovers());
    }

    @SmallTest
    public void testDataBuilderSetGetSubject() {
        assertEquals("", mAdapter.getSubject());
        String subject = RandomString.nextWord();
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals("", builder.getSubject());
            mAdapter.setSubject(subject);
            assertEquals(subject, builder.getSubject());
            mAdapter.setSubject("");
            assertEquals("", builder.getSubject());
            builder.setSubject(subject);
            assertEquals(subject, builder.getSubject());
            assertEquals(subject, mAdapter.getSubject());
            mAdapter.setSubject("");
        }
    }

    @SmallTest
    public void testDataBuilderSetGetRating() {
        assertEquals(0f, mAdapter.getRating());
        float rating = RandomRating.nextRating();
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals(0f, builder.getRating());
            mAdapter.setRating(rating);
            assertEquals(rating, builder.getRating());
            mAdapter.setRating(0f);
            assertEquals(0f, builder.getRating());
            builder.setRating(rating);
            assertEquals(rating, builder.getRating());
            assertEquals(rating, mAdapter.getRating());
            mAdapter.setRating(0f);
        }
    }

    @SmallTest
    public void testDataBuilderGetParentBuilder() {
        for (GvDataType dataType : TYPES) {
            assertEquals(mAdapter, mAdapter.getDataBuilder(dataType).getParentBuilder());
        }
    }

    @SmallTest
    public void testDataBuilderGetAverageRating() {
        mAdapter.setRatingIsAverage(false);
        GvCriterionList criteria;
        do {
            criteria = GvDataMocker.newChildList(NUM, false);
            setBuilderData(criteria);
        } while (criteria.getAverageRating() == mAdapter.getRating());

        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals(criteria.getAverageRating(), builder.getAverageRating());
        }
    }

    @SmallTest
    public void testDataBuilderGetImages() {
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        GvImageList newImages = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);

        ReviewBuilderAdapter.DataBuilderAdapter imageBuilder =
                mAdapter.getDataBuilder(GvImageList.GvImage.TYPE);
        imageBuilder.deleteAll();
        for (GvImageList.GvImage image : newImages) {
            imageBuilder.add(image);
        }

        assertEquals(images, mAdapter.getCovers());
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
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

    private GvDataList getBuilderData(GvDataType dataType) {
        return mAdapter.getDataBuilder(dataType).getGridData();
    }

    private void setBuilderData(GvDataList data) {
        ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(data
                .getGvDataType());
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            assertTrue(builder.add(datum));
        }
        builder.setData();
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = RandomAuthor.nextAuthor();
        mTagsManager = new TagsManager();
        mBuilder = new ReviewBuilder(getContext(), mAuthor, mTagsManager);
        mAdapter = new ReviewBuilderAdapter(mBuilder);
    }
}
