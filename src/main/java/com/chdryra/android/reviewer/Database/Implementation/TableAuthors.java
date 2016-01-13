package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";
    public static final String USER_ID = RowAuthor.COLUMN_USER_ID;
    public static final String NAME = RowAuthor.COLUMN_AUTHOR_NAME;

    public TableAuthors(FactoryDbColumnDef columnFactory, RowValueTypeDefinitions types) {
        super(TABLE, RowAuthor.class);

        RowValueType text = types.getTextType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(USER_ID, text));
        addColumn(columnFactory.newNotNullableColumn(NAME, text));
    }
}
