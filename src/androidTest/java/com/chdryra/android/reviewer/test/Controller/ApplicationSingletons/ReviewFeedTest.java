/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller.ApplicationSingletons;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Controller.ApplicationSingletons.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;

/**
 * Created by: Rizwan Choudrey
 * On: 11/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFeedTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    public ReviewFeedTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testGetFeedAdapter() {
        assertNotNull(ReviewFeed.getFeedAdapter(getActivity()));
    }

    @SmallTest
    public void testAggregateAdapter() {
        assertNotNull(ReviewFeed.getAggregateAdapter(getActivity()));
    }

    @SmallTest
    @UiThreadTest
    public void testGetReviewLaunchable() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        assertNull(ReviewFeed.getReviewLaunchable(getActivity(), id));
        ReviewFeed.addToFeed(getActivity(), node);
        assertNotNull(ReviewFeed.getReviewLaunchable(getActivity(), id));
    }

    @SmallTest
    @UiThreadTest
    public void testRemoveFromFeed() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewId nodeId = node.getRoot().getId();
        ReviewFeed.addToFeed(getActivity(), node);

        ReviewViewAdapter reviews = ReviewFeed.getFeedAdapter(getActivity());
        int numReviews = reviews.getGridData().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        ReviewIdableList<ReviewNode> fromDb = db.getReviewTreesFromDb();
        int numInDb = fromDb.size();
        assertTrue(numReviews > 0);
        assertEquals(numReviews, numInDb);
        assertTrue(fromDb.containsId(nodeId));

        AdapterReviewNode feed = (AdapterReviewNode) reviews;

        GvReviewList list = (GvReviewList) feed.getGridData();
        GvReviewList.GvReviewOverview mostRecent = list.getItem(list.size() - 1);
        assertEquals(nodeId.toString(), mostRecent.getId());

        ReviewFeed.removeFromFeed(getActivity(), node.getId().toString());

        list = (GvReviewList) feed.getGridData();
        if (list.size() > 0) {
            mostRecent = list.getItem(list.size() - 1);
            assertFalse(nodeId.toString().equals(mostRecent.getId()));
        }

        fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews - 1, reviews.getGridData().size());
        assertEquals(numInDb - 1, fromDb.size());
        assertFalse(fromDb.containsId(nodeId));
    }

    @SmallTest
    @UiThreadTest
    public void testAddToFeed() {
        ReviewViewAdapter reviews = ReviewFeed.getFeedAdapter(getActivity());
        assertNotNull(reviews);
        GvReviewList list = (GvReviewList) reviews.getGridData();
        assertNotNull(list);

        int numReviews = list.size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        ReviewIdableList<ReviewNode> fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews, fromDb.size());

        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewId nodeId = node.getRoot().getId();

        assertFalse(fromDb.containsId(nodeId));
        if (numReviews > 0) {
            for (int i = 0; i < list.size(); ++i) {
                assertFalse(nodeId.toString().equals(list.getItem(i).getId()));
            }
        }

        ReviewFeed.addToFeed(getActivity(), node);

        assertEquals(numReviews + 1, reviews.getGridData().size());
        fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews + 1, fromDb.size());
        assertTrue(fromDb.containsId(node.getRoot().getId()));

        list = (GvReviewList) reviews.getGridData();
        GvReviewList.GvReviewOverview mostRecent = list.getItem(list.size() - 1);
        assertEquals(nodeId.toString(), mostRecent.getId());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        Intent i = new Intent();
        admin.packView(FeedScreen.newScreen(getInstrumentation().getTargetContext()), i);
        setActivityIntent(i);
        assertNotNull(getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        ReviewFeed.deleteTestDatabase(getActivity());
        TestDatabase.recreateDatabase(getInstrumentation());
    }
}
