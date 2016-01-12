package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryDbTableRow {
    <T extends DbTableRow> T emptyRow(Class<T> rowClass);

    <T extends DbTableRow> T newRow(RowValues values, Class<T> rowClass);
}
