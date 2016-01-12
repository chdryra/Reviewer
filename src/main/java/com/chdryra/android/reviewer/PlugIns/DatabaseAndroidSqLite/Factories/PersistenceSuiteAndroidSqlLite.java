package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation.ContractedSqlLiteTransactor;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation.RowValueTypeDefinitionsSqlLite;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation.SqlLiteContractExecutorImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ContractedTableTransactor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.PersistenceSuite;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceSuiteAndroidSqlLite<T extends DbContract> implements PersistenceSuite<T> {
    private static final RowValueTypeDefinitions TYPES= new RowValueTypeDefinitionsSqlLite();

    @Override
    public RowValueTypeDefinitions getTypeDefinitions() {
        return TYPES;
    }

    @Override
    public ContractedTableTransactor<T> newDatabaseProvider(Context context, DbSpecification<T> spec) {
        FactoryTableTransactorSqLite dbInstanceFactory = new FactoryTableTransactorSqLite(new FactoryRowConverter());
        return new ContractedSqlLiteTransactor<>(context, spec, new SqlLiteContractExecutorImpl(), dbInstanceFactory);
    }
}
