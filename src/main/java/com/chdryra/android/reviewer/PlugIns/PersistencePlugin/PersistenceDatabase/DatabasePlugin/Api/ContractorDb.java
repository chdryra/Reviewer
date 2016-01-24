package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbContract;


/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ContractorDb<T extends DbContract> {
    T getContract();

    TableTransactor getReadableTransactor();

    TableTransactor getWriteableTransactor();
}
