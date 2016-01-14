package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValueType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";
    private static final String USER_ID = RowAuthor.COLUMN_USER_ID;
    private static final String NAME = RowAuthor.COLUMN_AUTHOR_NAME;

    public TableAuthors(FactoryDbColumnDef columnFactory, RowValueTypeDefinitions types) {
        super(TABLE, RowAuthor.class);

        RowValueType text = types.getTextType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(USER_ID, text));
        addColumn(columnFactory.newNotNullableColumn(NAME, text));
    }
}
