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

import com.chdryra.android.reviewer.Database.RowReview;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewTest extends TestCase {
    private Review mReview;
    private MdCriterionList.MdCriterion mCriterion;

    @SmallTest
    public void testReviewConstructor() {
        testRow(mReview, null, new RowReview(mReview));
        testRow(mCriterion.getReview(), mCriterion.getReviewId().toString(), new RowReview
                (mCriterion));
    }

    @SmallTest
    public void testToCursorConstructor() {
        testToCursorConstructor(mReview, null);
        testToCursorConstructor(mCriterion.getReview(), mCriterion.getReviewId().toString());
    }

    private void testToCursorConstructor(Review review, String parentId) {
        String[] cols = new String[]{RowReview.REVIEW_ID, RowReview.PARENT_ID, RowReview.AUTHOR_ID,
                RowReview.PUBLISH_DATE, RowReview.SUBJECT, RowReview.RATING, RowReview.IS_AVERAGE};

        MatrixCursor cursor = new MatrixCursor(cols);
        String reviewId = review.getMdReviewId().toString();
        String authorId = review.getAuthor().getUserId().toString();
        cursor.addRow(new Object[]{reviewId, parentId, authorId, review.getPublishDate().getTime(),
                review.getSubject().getSubject(), review.getRating().getRating(), review
                .isRatingAverageOfCriteria() ? 1 : 0});
        cursor.moveToFirst();
        testRow(review, parentId, new RowReview(cursor));
    }

    private void testRow(Review review, String parentId, RowReview row) {
        String authorId = review.getAuthor().getUserId().toString();
        ContentValues values = row.getContentValues();
        assertEquals(review.getMdReviewId().toString(), values.getAsString(RowReview.REVIEW_ID));
        assertEquals(parentId, values.getAsString(RowReview.PARENT_ID));
        assertEquals(authorId, values.getAsString(RowReview.AUTHOR_ID));
        assertEquals(review.getPublishDate().getTime(), (long) values.getAsLong(RowReview
                .PUBLISH_DATE));
        assertEquals(review.getSubject().getSubject(), values.getAsString(RowReview.SUBJECT));
        assertEquals(review.getRating().getRating(), values.getAsFloat(RowReview.RATING));
        assertEquals(review.isRatingAverageOfCriteria(),
                values.getAsBoolean(RowReview.IS_AVERAGE).booleanValue());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mReview = ReviewMocker.newReview();
        MdCriterionList criteria = mReview.getCriteria();
        mCriterion = criteria.getItem(0);
    }
}
