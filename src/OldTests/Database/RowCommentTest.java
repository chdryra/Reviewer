/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.startouch.test.Database;

import android.content.ContentValues;
import android.database.MatrixCursor;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.startouch.test.TestUtils.MdDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCommentTest extends TestCase {
    private static final int INDEX = 314;
    private MdComment mComment;

    @SmallTest
    public void testDataConstructor() {
        testRow(new RowComment(mComment, INDEX));
    }

    @SmallTest
    public void testCursorConstructor() {
        String[] cols = new String[]{RowComment.COMMENT_ID, RowComment.REVIEW_ID, RowComment
                .COMMENT, RowComment.IS_HEADLINE};

        MatrixCursor cursor = new MatrixCursor(cols);
        ReviewId reviewId = mComment.getReviewId().toString();
        String datumId = getDatumId();
        cursor.addRow(new Object[]{datumId, reviewId, mComment.getComment(), mComment.isHeadline
                () ? 1 : 0});
        cursor.moveToFirst();
        testRow(new RowComment(cursor));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        MdDataMocker mocker = new MdDataMocker();
        mComment = mocker.newComment();
    }

    //private methods
    private String getDatumId() {
        return mComment.getReviewId().toString() + FactoryReviewerDbTableRow.SEPARATOR + "c" +
                String.valueOf
                (INDEX);
    }

    private void testRow(RowComment row) {
        ContentValues values = row.getContentValues();
        assertEquals(getDatumId(), values.getAsString(RowComment.COMMENT_ID));
        assertEquals(mComment.getReviewId().toString(), values.getAsString(RowComment.REVIEW_ID));
        assertEquals(mComment.getComment(), values.getAsString(RowComment.COMMENT));
        assertTrue(mComment.isHeadline() == values.getAsBoolean(RowComment.IS_HEADLINE));
        assertEquals(mComment, row.toMdData());
    }
}
