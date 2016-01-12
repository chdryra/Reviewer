package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTableImpl<RowTag> {
    private static final String TABLE = "Tags";
   
    public TableTags(FactoryDbColumnDef columnFactory, StorageTypeDefinitions typeFactory) {
        super(TABLE, RowTag.class);
        DbColumnDef tag = columnFactory.newPkColumnDef(RowTag.COLUMN_TAG, typeFactory.getTextType());
        DbColumnDef reviews = columnFactory.newColumnDef(RowTag.COLUMN_REVIEWS, typeFactory.getTextType(), ValueNullable.FALSE);

        addPrimaryKey(tag);
        addColumn(reviews);
    }

}
