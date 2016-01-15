package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";
    private static final String USER_ID = RowAuthor.COLUMN_USER_ID;
    private static final String NAME = RowAuthor.COLUMN_AUTHOR_NAME;

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowAuthor.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(USER_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(NAME, DbEntryTypes.TEXT));
    }
}
