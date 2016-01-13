package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbSpecification<T extends DbContract> {
    T getContract();

    String getDatabaseName();

    int getVersionNumber();
}
