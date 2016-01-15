package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocations extends DbTableImpl<RowLocation> {
    private static final String TABLE = "Locations";
    private static final String LOCATION_ID = RowLocation.COLUMN_LOCATION_ID;
    private static final String REVIEW_ID = RowLocation.COLUMN_REVIEW_ID;
    private static final String LATITUDE = RowLocation.COLUMN_LATITUDE;
    private static final String LONGITUDE = RowLocation.COLUMN_LONGITUDE;
    private static final String NAME = RowLocation.COLUMN_NAME;

    public TableLocations(FactoryDbColumnDef columnFactory,
                          DbTable<? extends RowReview> reviewsTable,
                          FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowLocation.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(LOCATION_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(LATITUDE, DbEntryTypes.DOUBLE));
        addColumn(columnFactory.newNotNullableColumn(LONGITUDE, DbEntryTypes.DOUBLE));
        addColumn(columnFactory.newNotNullableColumn(NAME, DbEntryTypes.TEXT));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }

}
