/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.Controller;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewBuilder;
import com.chdryra.android.reviewer.Controller.ReviewFeedAdapter;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.View.ActivityReviewView;
import com.chdryra.android.reviewer.View.FeedScreen;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.View.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.ImageChooser;
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
        Activity a = getActivity();
        ImageChooser im = Administrator.getImageChooser(a);
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
    public void testGetFeedAdapter() {
        assertNotNull(mAdmin.getFeedAdapter());
    }

    @SmallTest
    public void testPublishReviewBuilder() {
        testPublish();
    }

    @SmallTest
    public void testDeleteReview() {
        testPublish();

        ReviewViewAdapter reviews = mAdmin.getFeedAdapter();
        int numReviews = reviews.getGridData().size();
        assertTrue(numReviews > 0);
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        int numInDb = db.getReviewTreesFromDb().size();
        ReviewFeedAdapter feed = (ReviewFeedAdapter) reviews;
        GvReviewList list = (GvReviewList) feed.getGridData();
        GvReviewList.GvReviewOverview r = list.getItem(list.size() - 1);
        mAdmin.deleteReview(r.getId());
        assertEquals(numReviews - 1, reviews.getGridData().size());
        assertEquals(numInDb - 1, db.getReviewTreesFromDb().size());
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
        Activity a = getActivity();
        assertNotNull(a);
        ImageChooser ic = Administrator.getImageChooser(a);
    }

    private void testPublish() {
        ReviewViewAdapter reviews = mAdmin.getFeedAdapter();
        assertNotNull(reviews);
        int numReviews = reviews.getGridData().size();
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
    }
}
