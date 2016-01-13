package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;

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
