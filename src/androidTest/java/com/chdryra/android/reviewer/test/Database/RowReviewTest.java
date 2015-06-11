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

    @SmallTest
    public void testReviewConstructor() {
        testRow(new RowReview(mReview));
    }

    @SmallTest
    public void testToCursorConstructor() {
        String[] cols = new String[]{RowReview.REVIEW_ID, RowReview.AUTHOR_ID, RowReview
                .PUBLISH_DATE, RowReview.SUBJECT, RowReview.RATING};

        MatrixCursor cursor = new MatrixCursor(cols);
        String reviewId = mReview.getId().toString();
        String authorId = mReview.getAuthor().getUserId().toString();
        cursor.addRow(new Object[]{reviewId, authorId, mReview.getPublishDate().getTime(),
                mReview.getSubject().get(), mReview.getRating().get()});
        cursor.moveToFirst();
        testRow(new RowReview(cursor));
    }

    @Override
    protected void setUp() throws Exception {
        mReview = ReviewMocker.newReview();
    }

    private void testRow(RowReview row) {
        String authorId = mReview.getAuthor().getUserId().toString();
        ContentValues values = row.getContentValues();
        assertEquals(mReview.getId().toString(), values.getAsString(RowReview.REVIEW_ID));
        assertEquals(authorId, values.getAsString(RowReview.AUTHOR_ID));
        assertEquals(mReview.getPublishDate().getTime(), (long) values.getAsLong(RowReview
                .PUBLISH_DATE));
        assertEquals(mReview.getSubject().get(), values.getAsString(RowReview.SUBJECT));
        assertEquals(mReview.getRating().get(), values.getAsFloat(RowReview.RATING));
    }
}
