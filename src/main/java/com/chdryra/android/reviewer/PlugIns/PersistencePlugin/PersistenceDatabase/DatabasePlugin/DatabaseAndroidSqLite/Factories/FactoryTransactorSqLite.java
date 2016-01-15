package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.RowToValuesConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.TableTransactorSqlLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTransactorSqLite {
    private RowToValuesConverter mConverter;

    public FactoryTransactorSqLite(RowToValuesConverter converter) {
        mConverter = converter;
    }

    public TableTransactor newTransactor(SQLiteDatabase db, FactoryDbTableRow rowFactory) {
        return new TableTransactorSqlLite(db, rowFactory, mConverter);
    }
}
