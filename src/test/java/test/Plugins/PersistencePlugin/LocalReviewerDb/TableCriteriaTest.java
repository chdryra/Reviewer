/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableCriteria;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableCriteriaTest extends ReviewerDbTableTest<RowCriterion>{
    private static final String NAME = "Criteria";
    private static final ColumnInfo<?>[] ROWS = {RowCriterion.CRITERION_ID, RowCriterion.REVIEW_ID,
            RowCriterion.SUBJECT, RowCriterion.RATING};
    private static final ColumnInfo<?>[] NULLABLE = {};

    private TableReviews mReviewsTable;

    public TableCriteriaTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowCriterion> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        TableAuthors tableAuthors = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, tableAuthors, constraintFactory);

        return new TableCriteria(colFactory, mReviewsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowLocation.REVIEW_ID);
    }
}
