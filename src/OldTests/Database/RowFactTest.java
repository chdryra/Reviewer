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
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.test.TestUtils.MdDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactTest extends TestCase {
    private static final int INDEX = 314;
    private MdFact mFact;
    private MdUrl mUrl;

    @SmallTest
    public void testDataConstructor() {
        testRow(new RowFact(mFact, INDEX), false);
        testRow(new RowFact(mUrl, INDEX), true);
    }

    @SmallTest
    public void testCursorConstructor() {
        String[] cols = new String[]{RowFact.FACT_ID, RowFact.REVIEW_ID, RowFact
                .LABEL, RowFact.VALUE, RowFact.IS_URL};

        MatrixCursor cursor = new MatrixCursor(cols);
        ReviewId reviewId = mFact.getReviewId().toString();
        String datumId = getDatumId();
        cursor.addRow(new Object[]{datumId, reviewId, mFact.getLabel(), mFact.getValue(), mFact
                .isUrl() ? 1 : 0});
        cursor.moveToFirst();
        testRow(new RowFact(cursor), false);

        cursor = new MatrixCursor(cols);
        cursor.addRow(new Object[]{datumId, reviewId, mUrl.getLabel(), mUrl.getValue(), mUrl
                .isUrl() ? 1 : 0});
        cursor.moveToFirst();
        testRow(new RowFact(cursor), true);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        MdDataMocker mocker = new MdDataMocker();
        mFact = mocker.newFact();
        mUrl = mocker.newUrl();
    }

    //private methods
    private String getDatumId() {
        return mFact.getReviewId().toString() + FactoryReviewerDbTableRow.SEPARATOR + "f" +
                String.valueOf
                (INDEX);
    }

    private void testRow(RowFact row, boolean isUrl) {
        MdFact fact = isUrl ? mUrl : mFact;
        ContentValues values = row.getContentValues();
        assertEquals(getDatumId(), values.getAsString(RowFact.FACT_ID));
        assertEquals(fact.getReviewId().toString(), values.getAsString(RowFact.REVIEW_ID));
        assertEquals(fact.getLabel(), values.getAsString(RowFact.LABEL));
        assertEquals(fact.getValue(), values.getAsString(RowFact.VALUE));
        assertTrue(fact.isUrl() == values.getAsBoolean(RowFact.IS_URL));
        assertEquals(isUrl ? mUrl : mFact, row.toMdData());
    }
}
