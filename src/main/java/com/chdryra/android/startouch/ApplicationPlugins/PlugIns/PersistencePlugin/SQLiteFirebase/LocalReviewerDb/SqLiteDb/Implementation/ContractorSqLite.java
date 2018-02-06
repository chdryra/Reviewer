/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.SqLiteDb.Implementation;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.ContractorDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbSpecification;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb
        .Interfaces.DbTableRow;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContractorSqLite<T extends DbContract> extends SQLiteOpenHelper
        implements ContractorDb<T> {
    private final T mContract;
    private final TablesSql mSql;
    private final FactoryTransactorSqLite mFactory;

    public ContractorSqLite(Context context,
                            DbSpecification<T> spec,
                            TablesSql sql,
                            FactoryTransactorSqLite factory) {
        super(context, spec.getDatabaseName(), null, spec.getVersionNumber());
        mContract = spec.getContract();
        mSql = sql;
        mFactory = factory;
    }

    @Override
    public TableTransactor getReadableTransactor() {
        return mFactory.newTransactor(getReadableDatabase(), mSql);
    }

    @Override
    public TableTransactor getWriteableTransactor() {
        return mFactory.newTransactor(getWritableDatabase(), mSql);
    }

    @Override
    public T getContract() {
        return mContract;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<DbTable<? extends DbTableRow>> tables = mContract.getTables();
        for (DbTable<?> table : tables) {
            try {
                db.execSQL(mSql.createTableSql(table));
            } catch (SQLException e) {
                throw new RuntimeException("Problem creating table " + table.getName(), e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(mSql.dropAllTablesSql(mContract.getTableNames()));
        } catch (SQLException e) {
            throw new RuntimeException("Problem dropping tables", e);
        }
    }
}
