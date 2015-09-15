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
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.ReviewTreeComparer;

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
        IdableList<ReviewNode> testNodes = TestReviews.getReviews(getInstrumentation());
        IdableList<ReviewNode> nodes = db.getReviewTreesFromDb();
        assertEquals(testNodes.size(), nodes.size());
        for (int i = 0; i < nodes.size(); ++i) {
            assertTrue(ReviewTreeComparer.compareTrees(testNodes.getItem(i), nodes.getItem(i)));
        }
    }
}
