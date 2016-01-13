package com.chdryra.android.reviewer.PlugIns.Persistence.Api;

import android.content.Context;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistencePlugin {
    RowValueTypeDefinitions getTypeDefinitions();

    <T extends DbContract> ContractedTableTransactor<T> newTableTransactor(Context context, DbSpecification<T> spec, FactoryDbTableRow rowFactory);
}
