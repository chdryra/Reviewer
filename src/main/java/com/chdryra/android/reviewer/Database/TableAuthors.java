package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends ReviewerDbTable<RowAuthor> {
    private static final String TABLE = "Authors";

    public TableAuthors() {
        super(TABLE, RowAuthor.class);
        addPrimaryKey(RowAuthor.COLUMN_USER_ID, SQL.StorageType.TEXT);
        addColumn(RowAuthor.COLUMN_AUTHOR_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
    }
}
