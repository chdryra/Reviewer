package test.Plugins.ReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.RowTableBasic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class RowTableBasicTest<T extends RowTableBasic> {
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    private String mRowIdColumnName;
    private int mSize;

    protected abstract T newRow();
    protected abstract String getRowId(T row);

    public RowTableBasicTest(String rowIdColumnName, int size) {
        mRowIdColumnName = rowIdColumnName;
        mSize = size;
    }

    @Test
    public void getRowIdReturnsCorrectId() {
        T row = newRow();
        assertThat(row.getRowId(), is(getRowId(row)));
    }

    @Test
    public void getRowIdColumnNameCorrectColumnName() {
        T row = newRow();
        assertThat(row.getRowIdColumnName(), is(mRowIdColumnName));
    }

    @Test
    public void iteratorIsSize() {
        T row = newRow();
        Iterator<RowEntry<?>> it = row.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            ++i;
        }
        assertThat(i, is(mSize));
    }

    @Test
    public void iteratorThrowsNoElementExceptionAfterTooManyNexts() {
        mExpectedException.expect(NoSuchElementException.class);
        T row = newRow();
        Iterator<RowEntry<?>> it = row.iterator();
        while (it.hasNext()) it.next();
        it.next();
    }

    @Test
    public void iteratorThrowsUnsupportedOperationExceptionOnRemove() {
        mExpectedException.expect(UnsupportedOperationException.class);
        T row = newRow();
        Iterator<RowEntry<?>> it = row.iterator();
        it.remove();
    }

    @NonNull
    protected ArrayList<RowEntry<?>> getRowEntries(T row) {
        ArrayList<RowEntry<?>> entries = new ArrayList<>();
        for (RowEntry<?> entry : row) {
            entries.add(entry);
        }
        return entries;
    }

    protected <T> void checkEntry(RowEntry<?> entry, ColumnInfo<T> column, T value) {
        assertThat(entry.getColumnName(), is(column.getName()));
        assertThat(entry.getEntryType().equals(column.getType()), is(true));
        assertThat((T) entry.getValue(), is(value));
    }
}
