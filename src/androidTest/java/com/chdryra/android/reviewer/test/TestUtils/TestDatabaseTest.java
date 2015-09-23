/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestDatabaseTest extends InstrumentationTestCase {

    @SmallTest
    public void testDatabase() {
        TestDatabase.recreateDatabase(getInstrumentation());
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        assertNotNull(db);
        IdableList<Review> testReviews = TestReviews.getReviews(getInstrumentation());
        IdableList<Review> reviews = db.loadReviewsFromDb();
        assertEquals(testReviews.size(), reviews.size());
        for (int i = 0; i < reviews.size(); ++i) {
            assertEquals(testReviews.getItem(i), reviews.getItem(i));
        }
    }
}
