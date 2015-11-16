package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowAuthor.class);
        DbColumnDef userId = columnFactory.newPkColumnDef(RowAuthor.COLUMN_USER_ID,
                SQL.StorageType.TEXT);
        DbColumnDef name = columnFactory.newColumnDef(RowAuthor.COLUMN_AUTHOR_NAME,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);

        addPrimaryKey(userId);
        addColumn(name);
    }
}
