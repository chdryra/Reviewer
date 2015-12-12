package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocations extends DbTableImpl<RowLocation> {
    private static final String TABLE = "Locations";

    public TableLocations(DbTable<? extends RowReview> reviewsTable,
                          FactoryDbColumnDef columnFactory,
                          FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowLocation.class);
        DbColumnDef locationId = columnFactory.newPkColumnDef(RowLocation.COLUMN_LOCATION_ID,
                SQL.StorageType.TEXT);
        DbColumnDef reviewId = columnFactory.newColumnDef(RowLocation.COLUMN_REVIEW_ID,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef latitude = columnFactory.newColumnDef(RowLocation.COLUMN_LATITUDE,
                SQL.StorageType.REAL, SQL.Nullable.FALSE);
        DbColumnDef longitude = columnFactory.newColumnDef(RowLocation.COLUMN_LONGITUDE,
                SQL.StorageType.REAL, SQL.Nullable.FALSE);
        DbColumnDef name = columnFactory.newColumnDef(RowLocation.COLUMN_NAME,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);

        addPrimaryKey(locationId);
        addColumn(reviewId);
        addColumn(latitude);
        addColumn(longitude);
        addColumn(name);

        ArrayList<DbColumnDef> fkCols = new ArrayList<>();
        fkCols.add(reviewId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }

}
