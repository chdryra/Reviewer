package com.chdryra.android.reviewer.Database.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DatabaseProvider;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistenceSuite<T extends DbContract> {
    RowValueTypeDefinitions getStorageTypeDefinitions();
    DatabaseProvider<T> getDatabaseProvider(Context context , DbSpecification<T> spec, FactoryReviewerDbTableRow rowFactory);
}
