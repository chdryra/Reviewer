package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTableImpl<RowTag> {
    private static final String TABLE = "Tags";
   
    public TableTags(FactoryDbColumnDef columnFactory, RowValueTypeDefinitions typeFactory) {
        super(TABLE, RowTag.class);
        DbColumnDef tag = columnFactory.newPkColumnDef(RowTag.COLUMN_TAG, typeFactory.getStringType());
        DbColumnDef reviews = columnFactory.newColumnDef(RowTag.COLUMN_REVIEWS, typeFactory.getStringType(), ValueNullable.FALSE);

        addPrimaryKeyColumn(tag);
        addColumn(reviews);
    }

}
