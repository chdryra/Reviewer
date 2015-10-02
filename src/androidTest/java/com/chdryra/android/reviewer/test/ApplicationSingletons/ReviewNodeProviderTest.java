/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 May, 2015
 */

package com.chdryra.android.reviewer.test.ApplicationSingletons;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewNodeProvider;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.Screens.AuthorFeedScreen;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;

/**
 * Created by: Rizwan Choudrey
 * On: 11/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeProviderTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    public ReviewNodeProviderTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testGetFeedNode() {
        assertNotNull(ReviewNodeProvider.getReviewNode(getActivity()));
    }

    @SmallTest
    @UiThreadTest
    public void testRemoveFromFeed() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewId nodeId = node.getRoot().getId();
        ReviewNodeProvider.addToFeed(getActivity(), node);

        ReviewNode feedNode = ReviewNodeProvider.getReviewNode(getActivity());
        int numReviews = feedNode.getChildren().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        IdableList<Review> fromDb = db.loadReviewsFromDb();
        int numInDb = fromDb.size();
        assertTrue(numReviews > 0);
        assertEquals(numReviews, numInDb);
        assertTrue(fromDb.containsId(nodeId));

        IdableList<ReviewNode> reviews = feedNode.getChildren();
        ReviewNode mostRecent = reviews.getItem(reviews.size() - 1);
        assertEquals(nodeId, mostRecent.getId());

        ReviewNodeProvider.removeFromFeed(getActivity(), node.getId().toString());

        if (reviews.size() > 0) {
            mostRecent = reviews.getItem(reviews.size() - 1);
            assertFalse(nodeId.equals(mostRecent.getId()));
        }

        fromDb = db.loadReviewsFromDb();
        assertEquals(numReviews - 1, reviews.size());
        assertEquals(numInDb - 1, fromDb.size());
        assertFalse(fromDb.containsId(nodeId));
    }

    @SmallTest
    @UiThreadTest
    public void testAddToFeed() {
        ReviewNode feedNode = ReviewNodeProvider.getReviewNode(getActivity());
        assertNotNull(feedNode);

        IdableList<ReviewNode> reviews = feedNode.getChildren();
        int numReviews = reviews.size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        IdableList<Review> fromDb = db.loadReviewsFromDb();
        assertEquals(numReviews, fromDb.size());

        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewId nodeId = node.getRoot().getId();

        assertFalse(fromDb.containsId(nodeId));
        if (numReviews > 0) {
            for (int i = 0; i < numReviews; ++i) {
                assertFalse(nodeId.equals(reviews.getItem(i).getId()));
            }
        }

        ReviewNodeProvider.addToFeed(getActivity(), node);

        assertEquals(numReviews + 1, reviews.size());
        fromDb = db.loadReviewsFromDb();
        assertEquals(numReviews + 1, fromDb.size());
        assertTrue(fromDb.containsId(node.getRoot().getId()));

        ReviewNode mostRecent = reviews.getItem(reviews.size() - 1);
        assertEquals(nodeId, mostRecent.getId());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        Intent i = new Intent();
        admin.packView(AuthorFeedScreen.newScreen(getInstrumentation().getTargetContext()), i);
        setActivityIntent(i);
        assertNotNull(getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        ReviewNodeProvider.deleteTestDatabase(getActivity());
        TestDatabase.recreateDatabase(getInstrumentation());
    }
}
