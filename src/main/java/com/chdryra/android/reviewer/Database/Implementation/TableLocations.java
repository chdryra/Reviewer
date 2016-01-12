package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
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
                          FactoryForeignKeyConstraint constraintFactory, RowValueTypeDefinitions typeFactory) {
        super(TABLE, RowLocation.class);
        DbColumnDef locationId = columnFactory.newPkColumnDef(RowLocation.COLUMN_LOCATION_ID,
                typeFactory.getStringType());
        DbColumnDef reviewId = columnFactory.newColumnDef(RowLocation.COLUMN_REVIEW_ID,
                typeFactory.getStringType(), ValueNullable.FALSE);
        DbColumnDef latitude = columnFactory.newColumnDef(RowLocation.COLUMN_LATITUDE,
                typeFactory.getFloatType(), ValueNullable.FALSE);
        DbColumnDef longitude = columnFactory.newColumnDef(RowLocation.COLUMN_LONGITUDE,
                typeFactory.getFloatType(), ValueNullable.FALSE);
        DbColumnDef name = columnFactory.newColumnDef(RowLocation.COLUMN_NAME,
                typeFactory.getStringType(), ValueNullable.FALSE);

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
