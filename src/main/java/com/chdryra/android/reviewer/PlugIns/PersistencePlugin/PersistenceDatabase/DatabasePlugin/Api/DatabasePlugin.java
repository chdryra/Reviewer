package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DatabasePlugin {
    RowValueTypeDefinitions getTypeDefinitions();

    <T extends DbContract> ContractedTableTransactor<T> newTableTransactor(Context context, DbSpecification<T> spec, FactoryDbTableRow rowFactory);

    String getDatabaseExtension();
}
