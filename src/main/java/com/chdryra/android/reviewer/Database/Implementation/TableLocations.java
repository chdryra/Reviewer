package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocations extends DbTableImpl<RowLocation> {
    private static final String TABLE = "Locations";
    public static final String LOCATION_ID = RowLocation.COLUMN_LOCATION_ID;
    public static final String REVIEW_ID = RowLocation.COLUMN_REVIEW_ID;
    public static final String LATITUDE = RowLocation.COLUMN_LATITUDE;
    public static final String LONGITUDE = RowLocation.COLUMN_LONGITUDE;
    public static final String NAME = RowLocation.COLUMN_NAME;

    public TableLocations(FactoryDbColumnDef columnFactory,
                          RowValueTypeDefinitions types,
                          DbTable<? extends RowReview> reviewsTable,
                          FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowLocation.class);

        RowValueType text = types.getTextType();
        RowValueType dbl = types.getDoubleType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(LOCATION_ID, text));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, text));
        addColumn(columnFactory.newNotNullableColumn(LATITUDE, dbl));
        addColumn(columnFactory.newNotNullableColumn(LONGITUDE, dbl));
        addColumn(columnFactory.newNotNullableColumn(NAME, text));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }

}
