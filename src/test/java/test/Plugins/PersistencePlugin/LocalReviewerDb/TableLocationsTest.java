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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocationsTest extends ReviewerDbTableTest<RowLocation>{
    private static final String NAME = "Locations";
    private static final ColumnInfo<?>[] ROWS = {RowLocation.LOCATION_ID, RowLocation.REVIEW_ID,
            RowLocation.LATITUDE, RowLocation.LONGITUDE, RowLocation.NAME};
    private static final ColumnInfo<?>[] NULLABLE = {};

    private TableReviews mReviewsTable;

    public TableLocationsTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowLocation> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        TableAuthors tableAuthors = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, tableAuthors, constraintFactory);

        return new TableLocations(colFactory, mReviewsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowLocation.REVIEW_ID);
    }
}
