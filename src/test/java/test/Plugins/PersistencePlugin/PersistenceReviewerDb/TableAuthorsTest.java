/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthorsTest extends ReviewerDbTableTest<RowAuthor>{
    private static final String NAME = "Authors";
    private static final ColumnInfo<?>[] COLS = {RowAuthor.USER_ID, RowAuthor.AUTHOR_NAME};
    private static final ColumnInfo<?>[] NULLABLE = {};

    public TableAuthorsTest() {
        super(NAME, COLS, NULLABLE);
    }

    @Override
    protected DbTable<RowAuthor> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        return new TableAuthors(colFactory);
    }
}
