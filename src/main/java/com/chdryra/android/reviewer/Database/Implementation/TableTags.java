package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTableImpl<RowTag> {
    private static final String TABLE = "Tags";
   
    public TableTags(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowTag.class);
        DbColumnDef tag = columnFactory.newPkColumnDef(RowTag.COLUMN_TAG, SQL.StorageType.TEXT);
        DbColumnDef reviews = columnFactory.newColumnDef(RowTag.COLUMN_REVIEWS,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);

        addPrimaryKey(tag);
        addColumn(reviews);
    }

}
