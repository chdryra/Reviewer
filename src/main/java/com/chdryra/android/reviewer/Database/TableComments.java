package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableComments extends ReviewerDbTable<RowComment> {
    private static final String TABLE = "Comments";
    
    public TableComments(DbTableDef reviewsTable) {
        super(TABLE, RowComment.class);
        addPrimaryKey(RowComment.COLUMN_COMMENT_ID, SQL.StorageType.TEXT);
        addColumn(RowComment.COLUMN_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowComment.COLUMN_COMMENT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        addColumn(RowComment.COLUMN_IS_HEADLINE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        addForeignKeyConstraint(new String[]{RowComment.COLUMN_REVIEW_ID}, reviewsTable);
    }
}
