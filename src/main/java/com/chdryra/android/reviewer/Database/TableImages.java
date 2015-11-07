package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableImages extends ReviewerDbTable<RowImage> {
    private static final String TABLE = "Images";
    
    public TableImages(DbTableDef reviewsTable) {
        super(TABLE, RowImage.class);
        addPrimaryKey(RowImage.COLUMN_IMAGE_ID, SQL.StorageType.TEXT);
        addColumn(RowImage.COLUMN_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowImage.COLUMN_BITMAP, SQL.StorageType.BLOB, SQL.Nullable.FALSE);
        addColumn(RowImage.COLUMN_IMAGE_DATE, SQL.StorageType.REAL, SQL.Nullable.TRUE);
        addColumn(RowImage.COLUMN_CAPTION, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        addColumn(RowImage.COLUMN_IS_COVER, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        addForeignKeyConstraint(new String[]{RowImage.COLUMN_REVIEW_ID}, reviewsTable);
    }
}
