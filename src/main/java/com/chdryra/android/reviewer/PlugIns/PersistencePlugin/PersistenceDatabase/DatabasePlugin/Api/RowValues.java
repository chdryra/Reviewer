package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowValues {
    String getString(String columnName);
    Float getFloat(String columnName);
    Double getDouble(String columnName);
    Long getLong(String columnName);
    Integer getInteger(String columnName);
    Boolean getBoolean(String columnName);
    Byte[] getByteArray(String columnName);
}
