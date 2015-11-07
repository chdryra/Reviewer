package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocations extends DbTable<RowLocation> {
    private static final String TABLE = "Locations";

    public TableLocations(DbTable reviewsTable) {
        super(TABLE, RowLocation.class);
        addPrimaryKey(RowLocation.COLUMN_LOCATION_ID, SQL.StorageType.TEXT);
        addColumn(RowLocation.COLUMN_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowLocation.COLUMN_LATITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
        addColumn(RowLocation.COLUMN_LONGITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
        addColumn(RowLocation.COLUMN_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addForeignKeyConstraint(new String[]{RowLocation.COLUMN_REVIEW_ID}, reviewsTable);
    }

}
