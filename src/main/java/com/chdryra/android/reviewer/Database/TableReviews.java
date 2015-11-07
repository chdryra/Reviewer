package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends ReviewerDbTable<RowReview> {
    private static final String TABLE = "Reviews";
    
    public TableReviews(DbTableDef authorsTable) {
        super(TABLE, RowReview.class);
        addPrimaryKey(RowReview.COLUMN_REVIEW_ID, SQL.StorageType.TEXT);
        addColumn(RowReview.COLUMN_AUTHOR_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowReview.COLUMN_PUBLISH_DATE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        addColumn(RowReview.COLUMN_SUBJECT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowReview.COLUMN_RATING, SQL.StorageType.REAL, SQL.Nullable.FALSE);
        addColumn(RowReview.COLUMN_RATING_IS_AVERAGE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        addColumn(RowReview.COLUMN_PARENT_ID, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        addForeignKeyConstraint(new String[]{RowReview.COLUMN_AUTHOR_ID}, authorsTable);
        addForeignKeyConstraint(new String[]{RowReview.COLUMN_PARENT_ID}, this);
    }

}
