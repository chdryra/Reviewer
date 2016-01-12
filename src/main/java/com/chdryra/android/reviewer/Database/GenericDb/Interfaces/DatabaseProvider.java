package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.DatabaseInstance;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DatabaseProvider<T extends DbContract> {
    T getContract();

    DatabaseInstance getReadableInstance(FactoryReviewerDbTableRow rowFactory);

    DatabaseInstance getWriteableInstance(FactoryReviewerDbTableRow rowFactory);
}
