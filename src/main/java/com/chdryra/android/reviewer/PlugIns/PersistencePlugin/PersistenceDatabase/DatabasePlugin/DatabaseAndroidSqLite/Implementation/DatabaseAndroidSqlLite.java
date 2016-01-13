package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractedTableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories.FactoryRowConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories.FactoryTableTransactorSqLite;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DatabaseAndroidSqlLite implements DatabasePlugin {
    private static final RowValueTypeDefinitions TYPES = new RowValueTypeDefinitionsSqlLite();
    private static final String EXT = ".db";

    @Override
    public RowValueTypeDefinitions getTypeDefinitions() {
        return TYPES;
    }

    @Override
    public <T extends DbContract> ContractedTableTransactor<T> newTableTransactor(Context context, DbSpecification<T> spec, FactoryDbTableRow rowFactory) {
        FactoryTableTransactorSqLite dbInstanceFactory = new FactoryTableTransactorSqLite(new FactoryRowConverter(), rowFactory);
        return new ContractedSqlLiteTransactor<>(context, spec, new SqlLiteContractExecutorImpl(), dbInstanceFactory);
    }

    @Override
    public String getDatabaseExtension() {
        return EXT;
    }
}
