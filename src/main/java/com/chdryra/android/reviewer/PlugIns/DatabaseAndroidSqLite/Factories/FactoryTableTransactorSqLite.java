package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation.TableTransactorSqlLite;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTableTransactorSqLite {
    private FactoryRowConverter mConverterFactory;
    private FactoryDbTableRow mRowFactory;

    public FactoryTableTransactorSqLite(FactoryRowConverter converterFactory, FactoryDbTableRow rowFactory) {
        mConverterFactory = converterFactory;
        mRowFactory = rowFactory;
    }

    public TableTransactor newInstance(SQLiteDatabase db) {
        return new TableTransactorSqlLite(db, mRowFactory, mConverterFactory);
    }
}
