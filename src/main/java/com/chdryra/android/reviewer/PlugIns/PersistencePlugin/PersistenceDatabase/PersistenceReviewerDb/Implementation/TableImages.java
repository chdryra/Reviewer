package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableImages extends ReviewerDbTableImpl<RowImage> {
    private static final String TABLE = "Images";

    public TableImages(FactoryDbColumnDef columnFactory,
                       DbTable<? extends RowReview> reviewsTable,
                       FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowImage.class, columnFactory);

        addPkColumn(RowImage.IMAGE_ID);
        addNotNullableColumn(RowImage.REVIEW_ID);
        addNotNullableColumn(RowImage.BITMAP);
        addNotNullableColumn(RowImage.IS_COVER);
        addNullableColumn(RowImage.CAPTION);
        addNullableColumn(RowImage.IMAGE_DATE);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(RowImage.REVIEW_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}