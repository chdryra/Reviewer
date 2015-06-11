/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.Controller.ApplicationSingletons;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Controller.ApplicationSingletons.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    private Administrator mAdmin;

    public AdministratorTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testGetImageChooser() {
        assertNull(Administrator.getImageChooser(getActivity()));
        mAdmin.newReviewBuilder();
        assertNotNull(Administrator.getImageChooser(getActivity()));
    }

    @SmallTest
    public void testGetAuthor() {
        assertNotNull(mAdmin.getAuthor());
    }

    @SmallTest
    public void testGetReviewBuilder() {
        ReviewBuilder builder = mAdmin.newReviewBuilder();
        assertNotNull(builder);
        assertEquals(builder, mAdmin.getReviewBuilder());
    }

    @SmallTest
    public void testNewReviewBuilder() {
        assertNotNull(mAdmin.newReviewBuilder());
    }

    @SmallTest
    @UiThreadTest
    public void testPublishReviewBuilder() {
        ReviewViewAdapter reviews = ReviewFeed.getFeedAdapter(getActivity());
        assertNotNull(reviews);
        int numReviews = reviews.getGridData().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        assertEquals(numReviews, db.getReviewTreesFromDb().size());

        ReviewBuilder builder = mAdmin.newReviewBuilder();
        assertNotNull(builder);
        builder.setSubject(RandomString.nextWord());
        GvTagList tags = GvDataMocker.newTagList(3);
        ReviewBuilder.DataBuilder tagBuilder = builder.getDataBuilder(GvTagList.TYPE);
        for (GvTagList.GvTag tag : tags) {
            tagBuilder.add(tag);
        }
        tagBuilder.setData();
        mAdmin.publishReviewBuilder();
        assertEquals(numReviews + 1, reviews.getGridData().size());
        assertNull(mAdmin.getReviewBuilder());

        ReviewIdableList<ReviewNode> fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews + 1, fromDb.size());
        AdapterReviewNode feed = (AdapterReviewNode) reviews;
        GvReviewList list = (GvReviewList) feed.getGridData();
        GvReviewList.GvReviewOverview mostRecent = list.getItem(list.size() - 1);
        assertTrue(fromDb.containsId(ReviewId.fromString(mostRecent.getId())));
    }


    @SmallTest
    public void testGetSocialPlatformList() {
        GvSocialPlatformList list = mAdmin.getSocialPlatformList();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        assertNotNull(mAdmin);
        Intent i = new Intent();
        mAdmin.packView(FeedScreen.newScreen(getInstrumentation().getTargetContext()), i);
        setActivityIntent(i);
        assertNotNull(getActivity());
    }
}
