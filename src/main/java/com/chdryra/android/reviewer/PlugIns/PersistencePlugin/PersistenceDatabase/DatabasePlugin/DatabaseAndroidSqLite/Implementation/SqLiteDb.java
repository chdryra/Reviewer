/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation;


import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SqLiteDb {
    void beginTransaction();

    void endTransaction();

    long insertOrThrow(String table, ContentValues values, String id);

    long replaceOrThrow(String table, ContentValues values, String id);

    int delete(String table, String whereClause, @Nullable String[] whereArgs);

    Cursor rawQuery(String sql, @Nullable String[] selectionArgs);
}
