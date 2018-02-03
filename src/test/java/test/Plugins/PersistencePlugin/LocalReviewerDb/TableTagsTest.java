/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTagsTest extends ReviewerDbTableTest<RowTag>{
    private static final String NAME = "Tags";
    private static final ColumnInfo<?>[] COLS = {RowTag.TAG, RowTag.REVIEWS};
    private static final ColumnInfo<?>[] NULLABLE = {};

    public TableTagsTest() {
        super(NAME, COLS, NULLABLE);
    }

    @Override
    protected DbTable<RowTag> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        return new TableTags(colFactory);
    }
}
