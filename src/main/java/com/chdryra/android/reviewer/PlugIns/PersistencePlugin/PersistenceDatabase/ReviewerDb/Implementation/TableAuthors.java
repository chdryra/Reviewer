package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;



import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends DbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowAuthor.class, columnFactory);

        addPkColumn(RowAuthor.COLUMN_USER_ID, RowAuthor.COLUMN_USER_ID_TYPE);
        addNotNullableColumn(RowAuthor.COLUMN_AUTHOR_NAME, RowAuthor.COLUMN_AUTHOR_NAME_TYPE);
    }
}
