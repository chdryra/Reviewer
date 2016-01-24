package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces;


/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryDbTableRow {
    <T extends DbTableRow> T emptyRow(Class<T> rowClass);

    <T extends DbTableRow, D> T newRow(Class<T> rowClass, D values);

    <T extends DbTableRow, D> T newRow(Class<T> rowClass, D values, int index);
}
