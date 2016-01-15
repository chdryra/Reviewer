package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableImages extends DbTableImpl<RowImage> {
    private static final String TABLE = "Images";
    private static final String IMAGE_ID = RowImage.COLUMN_IMAGE_ID;
    private static final String REVIEW_ID = RowImage.COLUMN_REVIEW_ID;
    private static final String BITMAP = RowImage.COLUMN_BITMAP;
    private static final String IMAGE_DATE = RowImage.COLUMN_IMAGE_DATE;
    private static final String CAPTION = RowImage.COLUMN_CAPTION;
    private static final String IS_COVER = RowImage.COLUMN_IS_COVER;

    public TableImages(FactoryDbColumnDef columnFactory,
                       DbTable<? extends RowReview> reviewsTable,
                       FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowImage.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(IMAGE_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(BITMAP, DbEntryTypes.BYTE_ARRAY));
        addColumn(columnFactory.newNullableColumn(IMAGE_DATE, DbEntryTypes.LONG));
        addColumn(columnFactory.newNullableColumn(CAPTION, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(IS_COVER, DbEntryTypes.BOOLEAN));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
