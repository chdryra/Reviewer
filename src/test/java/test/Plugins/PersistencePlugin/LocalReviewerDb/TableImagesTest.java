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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;



/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableImagesTest extends ReviewerDbTableTest<RowImage>{
    private static final String NAME = "Images";
    private static final ColumnInfo<?>[] ROWS = {RowImage.IMAGE_ID, RowImage.REVIEW_ID,
            RowImage.BITMAP, RowImage.IS_COVER, RowImage.CAPTION, RowImage.IMAGE_DATE};
    private static final ColumnInfo<?>[] NULLABLE = {RowImage.CAPTION, RowImage.IMAGE_DATE};

    private TableReviews mReviewsTable;

    public TableImagesTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowImage> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        TableAuthors tableAuthors = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, tableAuthors, constraintFactory);

        return new TableImages(colFactory, mReviewsTable, constraintFactory);
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowLocation.REVIEW_ID);
    }
}
