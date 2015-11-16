package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbSpecificationImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbSpecification {
    public <T extends DbContract> DbSpecification<T> newSpecification(String name,
                                                                      T contract,
                                                                      int versionNumber) {
        return new DbSpecificationImpl<>(name, contract, versionNumber);
    }
}
