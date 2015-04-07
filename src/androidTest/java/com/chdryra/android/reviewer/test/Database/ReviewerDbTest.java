/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.ReviewerDbRow;
import com.chdryra.android.reviewer.Database.SQLiteTableDefinition;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTest extends AndroidTestCase {
    ReviewerDb mDatabase;

    @SmallTest
    public void testAddReviewToDb() {
        ReviewNode node = ReviewMocker.newReviewNode();
        mDatabase.addReviewNodeToDb(node);

        ReviewerDb.ReviewerDbHelper helper = mDatabase.getHelper();
        String countQuery = "SELECT * FROM " + ReviewerDbContract.TableReviewTrees.TABLE_NAME;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        int numNodes = 2 + node.getChildren().size();
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.TableReviewTrees.get();
        SQLiteTableDefinition.SQLiteColumn col = table.getColumn(ReviewerDbContract.TableReviewTrees
                .COLUMN_NAME_REVIEW_NODE_ID);

        ReviewerDbRow.ReviewTreesRow row = (ReviewerDbRow.ReviewTreesRow) mDatabase.getRowFor
                (table, col, node.getId().toString(), ReviewerDbRow.ReviewTreesRow.class);
        assertTrue(row.hasData());
    }

    @Override
    protected void setUp() throws Exception {
        Context context = getContext();
        mDatabase = ReviewerDb.getTestDatabase(getContext());
        context.deleteDatabase(mDatabase.getDatabaseName());
    }
}
