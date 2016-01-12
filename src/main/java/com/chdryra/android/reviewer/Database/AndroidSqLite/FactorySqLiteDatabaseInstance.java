package com.chdryra.android.reviewer.Database.AndroidSqLite;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.DatabaseInstance;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySqLiteDatabaseInstance {
    private FactoryRowConverter mConverterFactory;

    public FactorySqLiteDatabaseInstance(FactoryRowConverter converterFactory) {
        mConverterFactory = converterFactory;
    }

    public DatabaseInstance newInstance(SQLiteDatabase db, FactoryReviewerDbTableRow rowFactory) {
        return new DatabaseInstanceSqlLite(db, rowFactory, mConverterFactory);
    }
}
