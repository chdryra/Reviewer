/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTest extends AndroidTestCase {
    ReviewerDb mTestDatabase;

    @SmallTest
    public void testAddReviewToDb() {
        ReviewNode node = ReviewMocker.newReviewNode();
        mTestDatabase.addReviewToDb(node);
    }

    @Override
    protected void setUp() throws Exception {
        Context context = getContext();
        mTestDatabase = ReviewerDb.getTestDatabase(getContext());
        context.deleteDatabase(mTestDatabase.getDatabaseName());
    }
}
