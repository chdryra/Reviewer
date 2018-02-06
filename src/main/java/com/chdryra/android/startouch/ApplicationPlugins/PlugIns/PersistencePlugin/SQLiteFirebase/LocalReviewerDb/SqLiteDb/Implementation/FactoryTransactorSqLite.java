/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.SqLiteDb.Implementation;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTransactorSqLite {
    private final RowToValuesConverter mRowConverter;
    private final EntryToStringConverter mEntryConverter;

    public FactoryTransactorSqLite(RowToValuesConverter rowConverter,
                                   EntryToStringConverter entryConverter) {
        mRowConverter = rowConverter;
        mEntryConverter = entryConverter;
    }

    public TableTransactor newTransactor(SQLiteDatabase db, TablesSql sql) {
        return new TableTransactorSqLite(new AndroidSqLiteDb(db), sql, mRowConverter, mEntryConverter);
    }
}
