/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.ControllerReviewTreeEditable;
import com.chdryra.android.reviewer.ImageChooser;
import com.chdryra.android.reviewer.PublisherReviewTree;
import com.chdryra.android.reviewer.ReviewBuilder;
import com.chdryra.android.reviewer.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderTest extends ActivityInstrumentationTestCase2<ActivityFeed> {
    private ReviewBuilder mBuilder;

    public ReviewBuilderTest() {
        super(ActivityFeed.class);
    }

    @SmallTest
    public void testGetReview() {
        ControllerReviewTreeEditable review = mBuilder.getReview();
        assertNotNull(review);
        assertFalse(review.isPublished());
    }

    @SmallTest
    public void testPublish() {
        PublisherReviewTree publisher = new PublisherReviewTree(Author.NULL_AUTHOR);
        ReviewNode node = mBuilder.publish(publisher);
        assertNotNull(node);
        assertTrue(node.isPublished());
        assertEquals(Author.NULL_AUTHOR, node.getAuthor());
    }

    @SmallTest
    public void testGetImageChooser() {
        ImageChooser chooser = mBuilder.getImageChooser(getActivity());
        assertNotNull(chooser);
        assertNotNull(chooser.getChooserIntents());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mBuilder = new ReviewBuilder(getInstrumentation().getTargetContext()
                .getApplicationContext());
    }
}
