package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTableImpl<RowTag> {
    private static final String TABLE = "Tags";
    private static final String TAG = RowTag.COLUMN_TAG;
    private static final String REVIEWS = RowTag.COLUMN_REVIEWS;

    public TableTags(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowTag.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(TAG, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(REVIEWS, DbEntryTypes.TEXT));
    }

}
