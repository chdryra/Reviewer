package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.TableTransactorSqlLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTransactorSqLite {
    private FactoryRowConverter mConverterFactory;
    private FactoryDbTableRow mRowFactory;

    public FactoryTransactorSqLite(FactoryRowConverter converterFactory, FactoryDbTableRow
            rowFactory) {
        mConverterFactory = converterFactory;
        mRowFactory = rowFactory;
    }

    public TableTransactor newInstance(SQLiteDatabase db) {
        return new TableTransactorSqlLite(db, mRowFactory, mConverterFactory);
    }
}
