package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
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
                       FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowImage.class);
        DbColumnDef imageId = columnFactory.newPkColumnDef(RowImage.COLUMN_IMAGE_ID,
                SQL.StorageType.TEXT);
        DbColumnDef reviewId = columnFactory.newColumnDef(RowImage.COLUMN_REVIEW_ID,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef bitmap = columnFactory.newColumnDef(RowImage.COLUMN_BITMAP,
                SQL.StorageType.BLOB, SQL.Nullable.FALSE);
        DbColumnDef imageDate = columnFactory.newColumnDef(RowImage.COLUMN_IMAGE_DATE,
                SQL.StorageType.REAL, SQL.Nullable.TRUE);
        DbColumnDef caption = columnFactory.newColumnDef(RowImage.COLUMN_CAPTION,
                SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        DbColumnDef isCover = columnFactory.newColumnDef(RowImage.COLUMN_IS_COVER,
                SQL.StorageType.INTEGER, SQL.Nullable.FALSE);

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
