package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableFacts extends DbTable<RowFact> {
    private static final String TABLE = "Facts";
    
    public TableFacts(DbTable<? extends RowReview> reviewsTable) {
        super(TABLE, RowFact.class);
        addPrimaryKey(RowFact.COLUMN_FACT_ID, SQL.StorageType.TEXT);
        addColumn(RowFact.COLUMN_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowFact.COLUMN_LABEL, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowFact.COLUMN_VALUE, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowFact.COLUMN_IS_URL, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        addForeignKeyConstraint(new String[]{RowFact.COLUMN_REVIEW_ID}, reviewsTable);
    }
}
