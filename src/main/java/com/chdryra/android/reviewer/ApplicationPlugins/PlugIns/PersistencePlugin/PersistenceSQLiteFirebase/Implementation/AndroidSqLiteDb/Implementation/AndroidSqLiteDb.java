/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.AndroidSqLiteDb.Implementation;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AndroidSqLiteDb implements SqLiteDb {
    private SQLiteDatabase mDb;

    public AndroidSqLiteDb(SQLiteDatabase db) {
        mDb = db;
    }

    @Override
    public void beginTransaction() {
        mDb.beginTransaction();
    }

    @Override
    public void endTransaction() {
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        mDb.close();
    }

    @Override
    public long insertOrThrow(String table, ContentValues values, String id) {
        try {
            return mDb.insertOrThrow(table, null, values);
        } catch (SQLException e) {
            String message = id + " into " + table + " table ";
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    @Override
    public long replaceOrThrow(String table, ContentValues values, String id) {
        try {
            return mDb.replaceOrThrow(table, null, values);
        } catch (SQLException e) {
            String message = id + " in " + table + " table ";
            throw new RuntimeException("Couldn't replace " + message, e);
        }
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        try {
            return mDb.delete(table, whereClause, whereArgs);
        } catch (SQLException e) {
            String message = "Couldn't delete values" + " from " + table + " table ";
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public Cursor rawQuery(String query, String[] selectionArgs) {
        return mDb.rawQuery(query, selectionArgs);
    }
}
