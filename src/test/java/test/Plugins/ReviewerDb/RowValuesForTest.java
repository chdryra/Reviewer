package test.Plugins.ReviewerDb;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.ColumnInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
class RowValuesForTest implements RowValues {
    private Map<ColumnInfo<?>, Object> mValues = new HashMap<>();


    void put(ColumnInfo<?> col, @Nullable Object value) {
        mValues.put(col, value);
    }

    @Override
    public <T> T getValue(String columnName, DbEntryType<T> entryType) {
        ColumnInfo<T> col = new ColumnInfo<>(columnName, entryType);
        return entryType.cast(mValues.get(col));
    }
}
