/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.content.ContentValues;
import android.database.MatrixCursor;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.RowReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewNodeTest extends TestCase {
    private ReviewNode mNode;

    @SmallTest
    public void testReviewConstructor() {
        testRow(new RowReviewNode(mNode));
    }

    @SmallTest
    public void testToCursorConstructor() {
        String[] cols = new String[]{RowReviewNode.NODE_ID, RowReviewNode.REVIEW_ID,
                RowReviewNode.PARENT_ID, RowReviewNode.IS_AVERAGE};

        MatrixCursor cursor = new MatrixCursor(cols);
        String nodeId = mNode.getId().toString();
        String reviewId = mNode.getReview().getId().toString();
        String parentId = mNode.getParent().getId().toString();
        cursor.addRow(new Object[]{nodeId, reviewId, parentId, mNode.isRatingAverageOfChildren()
                ? 1 : 0});
        cursor.moveToFirst();
        testRow(new RowReviewNode(cursor));
    }

    @Override
    protected void setUp() throws Exception {
        mNode = ReviewMocker.newReviewNode(false);
    }

    private void testRow(RowReviewNode row) {
        String nodeId = mNode.getId().toString();
        String reviewId = mNode.getReview().getId().toString();
        String parentId = mNode.getParent().getId().toString();

        ContentValues values = row.getContentValues();
        assertEquals(nodeId, values.getAsString(RowReviewNode.NODE_ID));
        assertEquals(reviewId, values.getAsString(RowReviewNode.REVIEW_ID));
        assertEquals(parentId, values.getAsString(RowReviewNode.PARENT_ID));
        assertTrue(mNode.isRatingAverageOfChildren() == values.getAsBoolean(RowReviewNode
                .IS_AVERAGE));
    }
}
