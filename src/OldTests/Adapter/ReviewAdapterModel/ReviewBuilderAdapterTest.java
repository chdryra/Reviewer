/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.startouch.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.WrapperGridData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.MdGvEquality;
import com.chdryra.android.startouch.test.TestUtils.RandomAuthor;
import com.chdryra.android.startouch.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.CallBackSignaler;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderAdapterTest extends AndroidTestCase {
    private static final int NUM = 3;

    private DatumAuthor mAuthor;
    private TagsManager mTagsManager;
    private ReviewBuilder mBuilder;
    private ReviewBuilderAdapter mAdapter;
    private CallBackSignaler mSignaler;

    @SmallTest
    public void testGetSetRatingAverage() {
        mAdapter.setRatingIsAverage(false);
        assertFalse(mBuilder.isRatingAverage());
        mAdapter.setRatingIsAverage(true);
        assertTrue(mBuilder.isRatingAverage());
    }

    @SmallTest
    public void testGetImageChooser() {
        //Empty because need activity
    }

    @SmallTest
    public void testGetDataBuilder() {
        assertNotNull(mAdapter.getDataBuilderAdapter(GvComment.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvFact.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvImage.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvLocation.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvUrl.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvCriterion.TYPE));
        assertNotNull(mAdapter.getDataBuilderAdapter(GvTag.TYPE));
    }

    @SmallTest
    public void testHasTags() {
        assertFalse(mAdapter.hasTags());
        setBuilderData(GvDataMocker.getData(GvTag.TYPE, NUM));
        assertTrue(mAdapter.hasTags());
    }

    @SmallTest
    public void testPublish() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        GvDataList comments = GvDataMocker.getData(GvComment.TYPE, NUM);
        GvDataList facts = GvDataMocker.getData(GvFact.TYPE, NUM);
        GvDataList images = GvDataMocker.getData(GvImage.TYPE, NUM);
        GvDataList locations = GvDataMocker.getData(GvLocation.TYPE, NUM);
        GvDataList urls = GvDataMocker.getData(GvUrl.TYPE, NUM);
        GvDataList children = GvDataMocker.getData(GvCriterion.TYPE, NUM);
        GvDataList tags = GvDataMocker.getData(GvTag.TYPE, NUM);

        mAdapter.setSubject(subject);
        mAdapter.setRating(rating);
        setBuilderData(comments);
        setBuilderData(facts);
        setBuilderData(images);
        setBuilderData(locations);
        setBuilderData(urls);
        setBuilderData(children);
        setBuilderData(tags);

        Review published = mAdapter.buildReview();
        assertEquals(mAuthor, published.getAuthor());
        assertEquals(subject, published.getSubject().getSubject());
        assertEquals(rating, published.getRating().getRating());

        MdGvEquality.check(published.getComments(), (GvCommentList) comments);
        MdGvEquality.check(published.getFacts(), (GvFactList) facts);
        MdGvEquality.check(published.getImages(), (GvImageList) images);
        MdGvEquality.check(published.getLocations(), (GvLocationList) locations);

        ItemTagCollection tagsPublished = mTagsManager.getTags(published.getMdReviewId());
        assertEquals(tags.size(), tagsPublished.size());
        for (int j = 0; j < tags.size(); ++j) {
            GvTag tag = (GvTag) tags.getItem(j);
            assertEquals(tag.getString(), tagsPublished.getItemTag(j).getTag());
        }

        MdCriterionList criteria = published.getCriteria();
        assertEquals(children.size(), criteria.size());
        for (int i = 0; i < children.size(); ++i) {
            MdCriterion criterion = criteria.getItem(i);
            assertEquals(published.getMdReviewId(), criterion.getReviewId());
            Review childReview = criterion.getReview();

            GvCriterion child = (GvCriterion) children
                    .getItem(i);
            assertEquals(child.getSubject(), childReview.getSubject().getSubject());
            assertEquals(child.getRating(), childReview.getRating().getRating());
            ItemTagCollection tagsChild = mTagsManager.getTags(childReview.getMdReviewId());
            assertEquals(tags.size(), tagsChild.size());
            for (int j = 0; j < tags.size(); ++j) {
                GvTag tag = (GvTag) tags.getItem(j);
                assertEquals(tag.getString(), tagsChild.getItemTag(j).getTag());
            }
        }
    }

    @SmallTest
    public void testSetGetSubject() {
        assertEquals(0, mAdapter.getSubject().length());
        assertEquals(0, mBuilder.getSubject().length());
        assertFalse(mAdapter.hasTags());

        //Set up observation of tags
        DataBuilderAdapter<GvTag> tagBuilder
                = mAdapter.getDataBuilderAdapter(GvTag.TYPE);
        CallBackSignaler tagSignaler = new CallBackSignaler(5000);
        tagBuilder.registerDataObserver(new GridObserver(tagSignaler));

        //Make sure observers signaled
        String subject = RandomString.nextWord();
        mSignaler.reset();
        mAdapter.setSubject(subject);
        mSignaler.waitForSignal();
        tagSignaler.waitForSignal();
        assertFalse(mSignaler.timedOut());
        assertFalse(tagSignaler.timedOut());

        //Subject and tags properly set
        assertEquals(subject, mAdapter.getSubject());
        assertEquals(subject, mBuilder.getSubject());
        assertTrue(mAdapter.hasTags());
        GvTagList tags = (GvTagList) mAdapter.getDataBuilderAdapter(GvTag.TYPE).getGridData();
        assertEquals(1, tags.size());
        assertEquals(subject, tags.getItem(0).getString());

        //Change subject should readjust tags
        String subject2 = RandomString.nextSentence();
        tagSignaler.reset();
        mAdapter.setSubject(subject);
        tagSignaler.waitForSignal();
        assertFalse(tagSignaler.timedOut());
        tags = (GvTagList) mAdapter.getDataBuilderAdapter(GvTag.TYPE).getGridData();
        assertEquals(1, tags.size());
        assertEquals(TextUtils.toCamelCase(subject2), tags.getItem(0).getString());
    }

    @SmallTest
    public void testSetGetRating() {
        assertEquals(0f, mAdapter.getRating());
        assertEquals(0f, mBuilder.getRating());
        float rating = RandomRating.nextRating();
        mAdapter.setRating(rating);
        assertEquals(rating, mAdapter.getRating());
        assertEquals(rating, mBuilder.getRating());
    }

    @SmallTest
    public void testGetGridData() {
        WrapperGridData list = (WrapperGridData) mAdapter.getGridData();
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @SmallTest
    public void testGetCovers() {
        assertEquals(0, mAdapter.getCovers().size());
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);
        assertEquals(1, mAdapter.getCovers().size());
        assertEquals(images.getItem(0), mAdapter.getCovers().getItem(0));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = RandomAuthor.nextAuthor();
        mTagsManager = new TagsManager();
        mBuilder = new ReviewBuilder(getContext(), mAuthor, mTagsManager);
        mAdapter = new ReviewBuilderAdapter(mBuilder);
        mSignaler = new CallBackSignaler(5000);
        mAdapter.registerDataObserver(new GridObserver(mSignaler));
    }

    private <T extends GvData> void setBuilderData(GvDataList<T> data) {
        DataBuilderAdapter<T> builder =
                mAdapter.getDataBuilderAdapter(data.getGvDataType());
        for (int i = 0; i < data.size(); ++i) {
            T datum = data.getItem(i);
            assertTrue(builder.add(datum));
        }
        mSignaler.reset();
        builder.publishData();
        mSignaler.waitForSignal();
        assertFalse(mSignaler.timedOut());
        mSignaler.reset();
    }

    private static class GridObserver implements DataObservable.DataObserver {
        private CallBackSignaler mCallBack;

        private GridObserver(CallBackSignaler signaler) {
            mCallBack = signaler;
        }

        @Override
        public void onGridDataChanged() {
            mCallBack.signal();
        }
    }
}
