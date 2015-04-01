/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 31 March, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.ReviewerDbHelper;
import com.chdryra.android.reviewer.Database.SQL;
import com.chdryra.android.reviewer.Database.SQLiteTableDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDbHelperTest extends AndroidTestCase {
    private ReviewerDbHelper                 mHelper;
    private ArrayList<SQLiteTableDefinition> mTables;

    @SmallTest
    public void testDatabaseExists() {
        deleteDatabase();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String dbName = ReviewerDbContract.getContract().getDatabaseName();
        File dbFile = getContext().getDatabasePath(dbName);
        assertTrue(dbFile.exists());

        for (SQLiteTableDefinition table : mTables) {
            assertTrue(tableExists(table, db));
        }
    }

    @SmallTest
    public void testTableColumnsCorrect() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        for (SQLiteTableDefinition table : mTables) {
            testTableColumns(table, db);
        }
    }

    @Override
    protected void setUp() throws Exception {
        mHelper = new ReviewerDbHelper(getContext());
        mTables = new ArrayList<>();
        mTables = ReviewerDbContract.getContract().getTableDefinitions();
    }

    private void testTableColumns(SQLiteTableDefinition tableDef, SQLiteDatabase db) {
        ArrayList<String> tableCols = getTableColumns(tableDef.getName(), db);
        ArrayList<String> colNames = tableDef.getColumnNames();
        assertEquals(colNames.size(), tableCols.size());
        for (int i = 0; i < colNames.size(); ++i) {
            assertEquals(colNames.get(i), tableCols.get(i));
        }
    }

    private boolean tableExists(SQLiteTableDefinition table, SQLiteDatabase database) {
        String query = SQL.SELECT + SQL.SPACE + SQL.DISTINCT + SQL.SPACE + "tbl_name" + SQL.SPACE;
        query += SQL.FROM + SQL.SPACE + "sqlite_master" + SQL.SPACE + SQL.WHERE + SQL.SPACE;
        query += "tbl_name = '" + table.getName() + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }

        return false;
    }

    private ArrayList<String> getTableColumns(String tableName, SQLiteDatabase db) {
        String query = SQL.SELECT + SQL.SPACE + SQL.ALL + SQL.SPACE + SQL.FROM + SQL.SPACE +
                tableName + SQL.SPACE + SQL.LIMIT + SQL.SPACE + "1";
        Cursor cursor = db.rawQuery(query, null);
        String[] colNames = cursor.getColumnNames();
        cursor.close();

        ArrayList<String> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(colNames));
        return ret;
    }

    private void deleteDatabase() {
        getContext().deleteDatabase(ReviewerDbContract.getContract().getDatabaseName());
    }
}
