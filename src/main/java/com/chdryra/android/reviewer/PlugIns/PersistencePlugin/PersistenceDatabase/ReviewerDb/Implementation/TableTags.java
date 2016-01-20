package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends ReviewerDbTableImpl<RowTag> {
    private static final String NAME = "Tags";

    public TableTags(FactoryDbColumnDef columnFactory) {
        super(NAME, RowTag.class, columnFactory);

        addPkColumn(RowTag.TAG);
        addNotNullableColumn(RowTag.REVIEWS);
    }

}
