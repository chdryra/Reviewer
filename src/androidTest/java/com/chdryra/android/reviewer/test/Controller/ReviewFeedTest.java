/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewChildrenAdapter;
import com.chdryra.android.reviewer.Controller.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.ActivityReviewView;
import com.chdryra.android.reviewer.View.FeedScreen;
import com.chdryra.android.reviewer.View.GvReviewList;
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
    public void testRemoveFromFeed() {
        ReviewViewAdapter reviews = ReviewFeed.getFeedAdapter(getActivity());
        int numReviews = reviews.getGridData().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        int numInDb = db.getReviewTreesFromDb().size();
        assertEquals(numReviews, numInDb);

        ReviewChildrenAdapter feed = (ReviewChildrenAdapter) reviews;
        GvReviewList list = (GvReviewList) feed.getGridData();
        GvReviewList.GvReviewOverview mostRecent = list.getItem(list.size() - 1);

        ReviewFeed.removeFromFeed(getActivity(), mostRecent.getId());

        ReviewIdableList<ReviewNode> fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews - 1, reviews.getGridData().size());
        assertEquals(numInDb - 1, fromDb.size());
        assertFalse(db.getReviewTreesFromDb().containsId(ReviewId.fromString(mostRecent.getId())));
    }

    public void testAddToFeed() {
        ReviewViewAdapter reviews = ReviewFeed.getFeedAdapter(getActivity());
        assertNotNull(reviews);
        assertNotNull(reviews.getGridData());
        int numReviews = reviews.getGridData().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        assertEquals(numReviews, db.getReviewTreesFromDb().size());

        ReviewNode node = ReviewMocker.newReviewNode(false);

        ReviewFeed.addToFeed(getActivity(), node);
        assertEquals(numReviews + 1, reviews.getGridData().size());

        ReviewIdableList<ReviewNode> fromDb = db.getReviewTreesFromDb();
        assertEquals(numReviews + 1, fromDb.size());
        ReviewChildrenAdapter feed = (ReviewChildrenAdapter) reviews;
        GvReviewList list = (GvReviewList) feed.getGridData();
        GvReviewList.GvReviewOverview mostRecent = list.getItem(list.size() - 1);
        assertTrue(fromDb.containsId(ReviewId.fromString(mostRecent.getId())));
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
    }
}
