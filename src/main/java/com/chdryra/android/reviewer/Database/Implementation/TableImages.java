package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableImages extends DbTableImpl<RowImage> {
    private static final String TABLE = "Images";
    
    public TableImages(DbTable<? extends RowReview> reviewsTable, FactoryDbColumnDef columnFactory,
                       FactoryForeignKeyConstraint constraintFactory, RowValueTypeDefinitions typeFactory) {
        super(TABLE, RowImage.class);
        DbColumnDef imageId = columnFactory.newPkColumnDef(RowImage.COLUMN_IMAGE_ID,
                typeFactory.getStringType());
        DbColumnDef reviewId = columnFactory.newColumnDef(RowImage.COLUMN_REVIEW_ID,
                typeFactory.getStringType(), ValueNullable.FALSE);
        DbColumnDef bitmap = columnFactory.newColumnDef(RowImage.COLUMN_BITMAP,
                typeFactory.getByteArrayType(), ValueNullable.FALSE);
        DbColumnDef imageDate = columnFactory.newColumnDef(RowImage.COLUMN_IMAGE_DATE,
                typeFactory.getFloatType(), ValueNullable.TRUE);
        DbColumnDef caption = columnFactory.newColumnDef(RowImage.COLUMN_CAPTION,
                typeFactory.getStringType(), ValueNullable.TRUE);
        DbColumnDef isCover = columnFactory.newColumnDef(RowImage.COLUMN_IS_COVER,
                typeFactory.getBooleanType(), ValueNullable.FALSE);

        addPrimaryKey(imageId);
        addColumn(reviewId);
        addColumn(bitmap);
        addColumn(imageDate);
        addColumn(caption);
        addColumn(isCover);

        ArrayList<DbColumnDef> fkCols = new ArrayList<>();
        fkCols.add(reviewId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
