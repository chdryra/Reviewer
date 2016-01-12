package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DatabaseProvider<T extends DbContract> {
    T getContract();

    TableTransactor getReadableInstance(FactoryDbTableRow rowFactory);

    TableTransactor getWriteableInstance(FactoryDbTableRow rowFactory);
}
