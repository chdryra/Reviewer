package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ContractedTableTransactor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.PersistentTablesModel;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories.FactoryRowConverter;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories.FactoryTableTransactorSqLite;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AndroidSqlLitePersistence implements PersistentTablesModel {
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
