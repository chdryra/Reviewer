package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbSpecification<T extends DbContract> {
    String getDatabaseName();

    T getContract();

    int getVersionNumber();
}
