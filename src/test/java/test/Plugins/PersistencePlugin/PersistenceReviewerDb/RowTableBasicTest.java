/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowTableBasic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class RowTableBasicTest<S extends DbTableRow, T extends RowTableBasic<S>> {
    protected static final Random RAND = new Random();

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
        Iterator<RowEntry<S, ?>> it = row.iterator();
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
        Iterator<RowEntry<S, ?>> it = row.iterator();
        while (it.hasNext()) it.next();
        it.next();
    }

    @Test
    public void iteratorThrowsUnsupportedOperationExceptionOnRemove() {
        mExpectedException.expect(UnsupportedOperationException.class);
        T row = newRow();
        Iterator<RowEntry<S, ?>> it = row.iterator();
        it.remove();
    }

    @NonNull
    protected ArrayList<RowEntry<S, ?>> getRowEntries(T row) {
        ArrayList<RowEntry<S, ?>> entries = new ArrayList<>();
        for (RowEntry<S, ?> entry : row) {
            entries.add(entry);
        }
        return entries;
    }

    @SuppressWarnings("unchecked")
    protected <Type> void checkEntry(RowEntry<S, ?> entry, ColumnInfo<Type> column, Type value) {
        assertThat(entry.getColumnName(), is(column.getName()));
        assertThat(entry.getEntryType().equals(column.getType()), is(true));
        assertThat((Type) entry.getValue(), is(value));
    }
}
