package com.chdryra.android.reviewer.Database.AndroidSqLite;

import android.content.Context;

import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.Factories.PersistenceSuite;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DatabaseProvider;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceSuiteAndroidSqlLite<T extends DbContract> implements PersistenceSuite<T> {
    private static final RowValueTypeDefinitions TYPES= new RowValueTypeDefinitionsSqlLite();

    @Override
    public RowValueTypeDefinitions getStorageTypeDefinitions() {
        return TYPES;
    }

    @Override
    public DatabaseProvider<T> getDatabaseProvider(Context context , DbSpecification<T> spec, FactoryReviewerDbTableRow rowFactory) {
        FactorySqLiteDatabaseInstance dbInstanceFactory = new FactorySqLiteDatabaseInstance(new FactoryRowConverter());
        return new DatabaseProviderSqlLite<>(context, spec, new SqlLiteContractExecutorImpl(), dbInstanceFactory);
    }
}
