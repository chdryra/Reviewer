/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.TableFacts;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableFactsTest extends ReviewerDbTableTest<RowFact> {
    private static final String NAME = "Facts";
    private static final ColumnInfo<?>[] ROWS = {RowFact.FACT_ID, RowFact.REVIEW_ID, RowFact.LABEL,
            RowFact.VALUE, RowFact.IS_URL};
    private static final ColumnInfo<?>[] NULLABLE = {};

    private TableReviews mReviewsTable;

    public TableFactsTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowFact> getTableToTest(FactoryDbColumnDef colFactory,
                                              FactoryForeignKeyConstraint constraintFactory) {
        TableAuthors tableAuthors = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, tableAuthors, constraintFactory);

        return new TableFacts(colFactory, mReviewsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowLocation.REVIEW_ID);
    }
}
