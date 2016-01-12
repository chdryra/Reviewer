package com.chdryra.android.reviewer.Database.AndroidSqLite;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageType;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.StorageTypeImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StorageTypeDefinitionsSqlLite implements StorageTypeDefinitions {
    @Override
    public StorageType getTextType() {
        return new StorageTypeImpl("TEXT");
    }

    @Override
    public StorageType getRealType() {
        return new StorageTypeImpl("REAL");
    }

    @Override
    public StorageType getBooleanType() {
        return getIntegerType();
    }

    @Override
    public StorageType getIntegerType() {
        return new StorageTypeImpl("INTEGER");
    }

    @Override
    public StorageType getBitmapType() {
        return new StorageTypeImpl("BLOB");
    }
}
