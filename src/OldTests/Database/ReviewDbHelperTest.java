/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 31 March, 2015
 */

package com.chdryra.android.startouch.test.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.corelibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDbPlugin.AndroidSqLiteDb.Implementation.SQL;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerPersistence;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDbHelperTest extends AndroidTestCase {
    private ReviewerPersistence mDatabase;
    private SQLiteOpenHelper mHelper;
    private ArrayList<DbTable> mTables;

    @SmallTest
    public void testDatabaseExists() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        for (DbTable table : mTables) {
            assertTrue(tableExists(table, db));
        }
    }

    @SmallTest
    public void testTableColumnsCorrect() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        for (DbTable table : mTables) {
            testTableColumns(table, db);
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mDatabase = ReviewerPersistence.getTestDatabase(getContext(), new TagsManager());
        mHelper = mDatabase.getHelper();
        mTables = new ArrayList<>();
        mTables = ReviewerDbContractImpl.getContract().getTableDefinitions();
    }

    @Override
    protected void tearDown() throws Exception {
        getContext().deleteDatabase(mDatabase.getDatabaseName());
    }

    private void testTableColumns(DbTable tableDef, SQLiteDatabase db) {
        ArrayList<String> tableCols = getTableColumns(tableDef.getName(), db);
        ArrayList<String> colNames = tableDef.getColumnNames();
        assertEquals(colNames.size(), tableCols.size());
        for (int i = 0; i < colNames.size(); ++i) {
            assertEquals(colNames.get(i), tableCols.get(i));
        }
    }

    private boolean tableExists(DbTable table, SQLiteDatabase database) {
        String query = SQL.SELECT + SQL.DISTINCT + "tbl_name ";
        query += SQL.FROM + "sqlite_master " + SQL.WHERE;
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
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + tableName + " " + SQL.LIMIT + "1";
        Cursor cursor = db.rawQuery(query, null);
        String[] colNames = cursor.getColumnNames();
        cursor.close();

        ArrayList<String> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(colNames));
        return ret;
    }
}
