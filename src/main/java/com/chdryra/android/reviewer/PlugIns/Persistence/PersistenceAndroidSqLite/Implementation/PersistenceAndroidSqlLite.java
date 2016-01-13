package com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.Persistence.Api.ContractedTableTransactor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Factories.FactoryRowConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Factories.FactoryTableTransactorSqLite;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceAndroidSqlLite implements PersistencePlugin {
    private static final RowValueTypeDefinitions TYPES = new RowValueTypeDefinitionsSqlLite();

    @Override
    public RowValueTypeDefinitions getTypeDefinitions() {
        return TYPES;
    }

    @Override
    public <T extends DbContract> ContractedTableTransactor<T> newTableTransactor(Context context, DbSpecification<T> spec, FactoryDbTableRow rowFactory) {
        FactoryTableTransactorSqLite dbInstanceFactory = new FactoryTableTransactorSqLite(new FactoryRowConverter(), rowFactory);
        return new ContractedSqlLiteTransactor<>(context, spec, new SqlLiteContractExecutorImpl(), dbInstanceFactory);
    }
}
