package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory, StorageTypeDefinitions typeFactory) {
        super(TABLE, RowAuthor.class);
        DbColumnDef userId = columnFactory.newPkColumnDef(RowAuthor.COLUMN_USER_ID,
                typeFactory.getTextType());
        DbColumnDef name = columnFactory.newColumnDef(RowAuthor.COLUMN_AUTHOR_NAME,
                typeFactory.getTextType(), ValueNullable.FALSE);

        addPrimaryKey(userId);
        addColumn(name);
    }
}
