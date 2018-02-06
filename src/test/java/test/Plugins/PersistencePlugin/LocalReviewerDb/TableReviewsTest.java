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
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviewsTest extends ReviewerDbTableTest<RowReview>{
    private static final String NAME = "Reviews";
    private static final ColumnInfo<?>[] ROWS = {RowReview.REVIEW_ID,
            RowReview.AUTHOR_ID, RowReview.PUBLISH_DATE, RowReview.SUBJECT, RowReview.RATING,
            RowReview.RATING_WEIGHT, RowReview.IS_AVERAGE};
    private static final ColumnInfo<?>[] NULLABLE = {};

    private TableAuthors mAuthorsTable;

    public TableReviewsTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowReview> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        mAuthorsTable = new TableAuthors(colFactory);
        return new TableReviews(colFactory, mAuthorsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mAuthorsTable, RowReview.AUTHOR_ID);
    }
}
