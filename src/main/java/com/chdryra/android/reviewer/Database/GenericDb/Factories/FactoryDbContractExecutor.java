package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbContractExecutorImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContractExecutor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbContractExecutor {

    public DbContractExecutor newExecutor() {
        return new DbContractExecutorImpl();
    }
}
