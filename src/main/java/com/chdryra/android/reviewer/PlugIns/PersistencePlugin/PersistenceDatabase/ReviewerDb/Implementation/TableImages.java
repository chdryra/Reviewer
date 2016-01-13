package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;

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
                       RowValueTypeDefinitions types,
                       DbTable<? extends RowReview> reviewsTable,
                       FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowImage.class);

        RowValueType text = types.getTextType();
        RowValueType bytes = types.getByteArrayType();
        RowValueType lng = types.getLongType();
        RowValueType bool = types.getBooleanType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(IMAGE_ID, text));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, text));
        addColumn(columnFactory.newNotNullableColumn(BITMAP, bytes));
        addColumn(columnFactory.newNullableColumn(IMAGE_DATE, lng));
        addColumn(columnFactory.newNullableColumn(CAPTION, text));
        addColumn(columnFactory.newNotNullableColumn(IS_COVER, bool));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
