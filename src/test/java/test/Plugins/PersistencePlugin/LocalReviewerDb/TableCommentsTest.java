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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowCriterion;



/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableCommentsTest extends ReviewerDbTableTest<RowComment>{
    private static final String NAME = "Comments";
    private static final ColumnInfo<?>[] ROWS = {RowComment.COMMENT_ID, RowComment.REVIEW_ID,
            RowComment.COMMENT, RowComment.IS_HEADLINE};
    private static final ColumnInfo<?>[] NULLABLE = {};

    private TableReviews mReviewsTable;

    public TableCommentsTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowComment> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        TableAuthors tableAuthors = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, tableAuthors, constraintFactory);

        return new TableComments(colFactory, mReviewsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowCriterion.REVIEW_ID);
    }
}
