package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends DbTable<RowTag> {
    private static final String TABLE = "Tags";
   
    public TableTags() {
        super(TABLE, RowTag.class);
        addPrimaryKey(RowTag.COLUMN_TAG, SQL.StorageType.TEXT);
        addColumn(RowTag.COLUMN_REVIEWS, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
    }

}
