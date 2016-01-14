package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValueType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTableImpl<RowTag> {
    private static final String TABLE = "Tags";
    private static final String TAG = RowTag.COLUMN_TAG;
    private static final String REVIEWS = RowTag.COLUMN_REVIEWS;

    public TableTags(FactoryDbColumnDef columnFactory, RowValueTypeDefinitions types) {
        super(TABLE, RowTag.class);

        RowValueType text = types.getTextType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(TAG, text));
        addColumn(columnFactory.newNotNullableColumn(REVIEWS, text));
    }

}
