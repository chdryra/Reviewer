package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces;

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
