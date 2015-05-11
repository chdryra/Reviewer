/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.CoversManager;
import com.chdryra.android.reviewer.Controller.MdGvConverter;
import com.chdryra.android.reviewer.Controller.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CoversManagerTest extends AndroidTestCase {
    @SmallTest
    public void testGetCovers() {
        Context context = getContext();
        Administrator admin = Administrator.get(context);
        ReviewViewAdapter feed = ReviewFeed.getFeedAdapter(getContext());
        GvReviewList reviews = (GvReviewList) feed.getGridData();
        for (GvReviewList.GvReviewOverview review : reviews) {
            String id = review.getId();
            ReviewId rId = ReviewId.fromString(id);
            GvReviewId gvId = new GvReviewId(rId);
            ReviewNode node = ReviewFeed.getReviewNode(getContext(), rId.toString());
            GvImageList covers = MdGvConverter.convert(node.getImages().getCovers());
            assertEquals(covers, CoversManager.getCovers(context, gvId));
            assertEquals(covers, CoversManager.getCovers(context, rId));
        }
    }
}
