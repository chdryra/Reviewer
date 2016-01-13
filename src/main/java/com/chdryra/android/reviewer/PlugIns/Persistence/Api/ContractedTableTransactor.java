package com.chdryra.android.reviewer.PlugIns.Persistence.Api;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ContractedTableTransactor<T extends DbContract> {
    T getContract();

    TableTransactor getReadableTransactor();

    TableTransactor getWriteableTransactor();
}
