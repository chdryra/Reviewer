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
        .LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.test.TestUtils.RandomAuthor;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorTest extends TestCase {
    private DatumAuthor mAuthor;

    @SmallTest
    public void testAuthorConstructor() {
        testRow(new RowAuthor(mAuthor));
    }

    @SmallTest
    public void testCursorConstructor() {
        String[] cols = new String[]{RowAuthor.AUTHOR_NAME, RowAuthor.USER_ID};
        MatrixCursor cursor = new MatrixCursor(cols);
        cursor.addRow(new Object[]{mAuthor.getName(), mAuthor.getUserId().toString()});
        cursor.moveToFirst();
        testRow(new RowAuthor(cursor));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = RandomAuthor.nextAuthor();
    }

    private void testRow(RowAuthor row) {
        ContentValues values = row.getContentValues();
        assertEquals(mAuthor.getName(), values.getAsString(RowAuthor.AUTHOR_NAME));
        assertEquals(mAuthor.getUserId().toString(), values.getAsString(RowAuthor.USER_ID));
        assertEquals(mAuthor, row.toAuthor());
    }
}
