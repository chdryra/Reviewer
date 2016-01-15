package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories;



import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryContractor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.ContractorSqlLite;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.RowToValuesConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.SqlLiteContractExecutorImpl;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.SqlLiteTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces.SqlLiteContractExecutor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryContractorSqLite implements FactoryContractor{
    @Override
    public <T extends DbContract> ContractorDb<T> newContractor(Context context, DbSpecification<T> spec) {
        FactoryTransactorSqLite transactorFactory
                = new FactoryTransactorSqLite(new RowToValuesConverter());
        SqlLiteContractExecutor executor = new SqlLiteContractExecutorImpl(new SqlLiteTypeDefinitions());

        return new ContractorSqlLite<>(context, spec, executor, transactorFactory);
    }
}
