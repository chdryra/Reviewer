/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.AndroidSqLiteDb;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowAuthorNameImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.EntryToStringConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.RowToValuesConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.SqLiteDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.SqLiteTypeDefinitions;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.TableTransactorSqLite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.TablesSql;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomReviewData;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class TableTransactorSqLiteTest {
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    @Mock
    private SqLiteDb mDb;
    private FactoryDbTableRow mRowFactory;
    private TableTransactorSqLite mTransactor;
    private ContentValues mValues;
    private ReviewerDbContract mContract;

    @Before
    public void setUp() {
        TablesSql sql = new TablesSql(new SqLiteTypeDefinitions());
        mTransactor = new TableTransactorSqLite(mDb, sql, new RowConverter(),
                new EntryToStringConverter());
        mRowFactory = new FactoryReviewerDbTableRow();
        mValues = new ContentValues();
        mContract = new FactoryReviewerDbContract().newContract();
    }

    @Test
    public void beginTransaction() {
        mTransactor.beginTransaction();
        verify(mDb).beginTransaction();
    }

    @Test
    public void endTransaction() {
        mTransactor.endTransaction();
        verify(mDb).endTransaction();
    }

    @Test
    public void loadTableWithNullCursor() {
        DbTable<RowTag> table = mContract.getTagsTable();
        TableRowList<RowTag> rows = mTransactor.loadTable(table, mRowFactory);
        String expected = "SELECT * FROM " + table.getName();
        verify(mDb).rawQuery(expected, null);

        //Cursor is null as not mocked therefore...
        assertThat(rows.size(), is(0));
    }

    @Test
    public void loadTableWithCursorSizeZero() {
        checkLoadTableWithSize(0);
    }

    @Test
    public void loadTableWithCursorSizeNonZero() {
        checkLoadTableWithSize(5);
    }

    @Test
    public void getRowsWhereReturnsEmptyListIfNullCursorReturned() {
        DbTable<RowTag> table = mContract.getTagsTable();
        ColumnInfo<String> column = RowTag.TAG;
        RowEntry<RowTag, String> clause = asClause(RowTag.class, column, RandomString.nextWord());

        TableRowList<RowTag> rows = mTransactor.getRowsWhere(table, clause, mRowFactory);

        assertThat(rows.size(), is(0));
    }

    @Test
    public void getRowsWhereReturnsEmptyListIfNoRowsReturned() {
        checkGetRowsWhereWithSize(0);
    }

    @Test
    public void getRowsWhereReturnsListWithSizeIfRowsFound() {
        checkGetRowsWhereWithSize(5);
    }

    @Test
    public void isIdInTableReturnsTrueIfCursorSizeOne() {
        DbTable<RowTag> table = mContract.getTagsTable();
        DbColumnDefinition idCol = table.getColumn(RowTag.TAG.getName());
        String id = RandomString.nextWord();

        Cursor cursor = setIdInTable(table, idCol, id, true);

        assertThat(mTransactor.isIdInTable(id, idCol, table), is(true));
        verify(cursor).close();
    }

    @Test
    public void isIdInTableReturnsFalseIfCursorIsNull() {
        DbTable<RowTag> table = mContract.getTagsTable();
        DbColumnDefinition idCol = table.getColumn(RowTag.TAG.getName());
        String id = RandomString.nextWord();

        assertThat(mTransactor.isIdInTable(id, idCol, table), is(false));
    }

    @Test
    public void isIdInTableReturnsFalseIfCursorSizeZero() {
        DbTable<RowTag> table = mContract.getTagsTable();
        DbColumnDefinition idCol = table.getColumn(RowTag.TAG.getName());
        String id = RandomString.nextWord();

        Cursor cursor = setIdInTable(table, idCol, id, false);

        assertThat(mTransactor.isIdInTable(id, idCol, table), is(false));
        verify(cursor).close();
    }

    @Test
    public void isIdInTableThrowsIllegalStateExceptionIdCursorSizeGreaterThanOne() {
        DbTable<RowTag> table = mContract.getTagsTable();
        DbColumnDefinition idCol = table.getColumn(RowTag.TAG.getName());
        String id = RandomString.nextWord();

        String sql = "SELECT * FROM " + table.getName() + " WHERE "
                + idCol.getName() + " = ?";

        Cursor cursor = getCursorWithSize(2);

        String[] value = {id};
        when(mDb.rawQuery(eq(sql), AdditionalMatchers.aryEq(value))).thenReturn(cursor);

        mExpectedException.expect(IllegalStateException.class);
        mTransactor.isIdInTable(id, idCol, table);
        verify(cursor).close();
    }

    @Test
    public void insertRowDoesNotInsertAndReturnsFalseIfIdInTable() {
        DbTable<RowAuthorName> table = mContract.getAuthorsTable();
        RowAuthorName row = new RowAuthorNameImpl(RandomAuthor.nextAuthor());
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowAuthorName.AUTHOR_ID.getName());

        setIdInTable(table, idCol, id, true);

        assertThat(mTransactor.insertRow(row, table), is(false));
        verify(mDb, never()).insertOrThrow(anyString(), eq(mValues), anyString());
    }

    @Test
    public void insertRowDoesInsertAndReturnsTrueIfIdNotInTable() {
        DbTable<RowAuthorName> table = mContract.getAuthorsTable();
        RowAuthorName row = new RowAuthorNameImpl(RandomAuthor.nextAuthor());
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowAuthorName.AUTHOR_ID.getName());

        setIdInTable(table, idCol, id, false);

        assertThat(mTransactor.insertRow(row, table), is(true));
        verify(mDb).insertOrThrow(table.getName(), mValues, id);
    }

    @Test
    public void insertRowReturnsFalseIfInsertTriedAndMinusOneReturned() {
        DbTable<RowAuthorName> table = mContract.getAuthorsTable();
        RowAuthorName row = new RowAuthorNameImpl(RandomAuthor.nextAuthor());
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowAuthorName.AUTHOR_ID.getName());

        setIdInTable(table, idCol, id, false);

        when(mDb.insertOrThrow(table.getName(), mValues, id)).thenReturn(-1l);
        assertThat(mTransactor.insertRow(row, table), is(false));
    }

    @Test
    public void insertOrReplaceRowDoesReplaceAndReturnsTrueIfIdInTable() {
        DbTable<RowFact> table = mContract.getFactsTable();
        RowFact row = new RowFactImpl(RandomReviewData.nextFact(), 1);
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowFact.FACT_ID.getName());

        setIdInTable(table, idCol, id, true);

        assertThat(mTransactor.insertOrReplaceRow(row, table), is(true));
        verify(mDb).replaceOrThrow(table.getName(), mValues, id);
    }

    @Test
    public void insertOrReplaceRowDoesInsertAndReturnsTrueIfIdNotInTable() {
        DbTable<RowFact> table = mContract.getFactsTable();
        RowFact row = new RowFactImpl(RandomReviewData.nextFact(), 1);
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowFact.FACT_ID.getName());

        setIdInTable(table, idCol, id, false);

        assertThat(mTransactor.insertOrReplaceRow(row, table), is(true));
        verify(mDb).insertOrThrow(table.getName(), mValues, id);
    }

    @Test
    public void insertOrReplaceRowReturnsFalseIfReplaceTriedAndMinusOneReturned() {
        DbTable<RowFact> table = mContract.getFactsTable();
        RowFact row = new RowFactImpl(RandomReviewData.nextFact(), 1);
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowFact.FACT_ID.getName());


        setIdInTable(table, idCol, id, true);

        when(mDb.replaceOrThrow(table.getName(), mValues, id)).thenReturn(-1l);
        assertThat(mTransactor.insertOrReplaceRow(row, table), is(false));
    }

    @Test
    public void insertOrReplaceRowReturnsFalseIfInsertTriedAndMinusOneReturned() {
        DbTable<RowFact> table = mContract.getFactsTable();
        RowFact row = new RowFactImpl(RandomReviewData.nextFact(), 1);
        String id = row.getRowId();
        DbColumnDefinition idCol = table.getColumn(RowFact.FACT_ID.getName());

        setIdInTable(table, idCol, id, false);

        when(mDb.insertOrThrow(table.getName(), mValues, id)).thenReturn(-1l);
        assertThat(mTransactor.insertOrReplaceRow(row, table), is(false));
    }

    @Test
    public void deleteRowsWhereCallsDelete() {
        DbTable<RowLocation> table = mContract.getLocationsTable();
        ColumnInfo<String> column = RowLocation.NAME;
        String value = RandomString.nextWord();

        mTransactor.deleteRowsWhere(table, asClause(table.getRowClass(), column, value));

        String query = column.getName() + " = ?";
        String[] val = new String[]{value};
        verify(mDb).delete(eq(table.getName()), eq(query), AdditionalMatchers.aryEq(val));
    }

    @Test
    public void deleteRowsThrowsIllegalArgumentExceptionIfClauseHasNullValue() {
        mExpectedException.expect(IllegalArgumentException.class);
        DbTable<RowLocation> table = mContract.getLocationsTable();
        ColumnInfo<String> column = RowLocation.NAME;

        mTransactor.deleteRowsWhere(table, asClause(table.getRowClass(), column, null));
    }

    @Test
    public void deleteRowsReturnsNumberRowsDeleted() {
        DbTable<RowLocation> table = mContract.getLocationsTable();
        ColumnInfo<String> column = RowLocation.NAME;
        String value = RandomString.nextWord();

        String query = column.getName() + " = ?";
        String[] val = new String[]{value};
        when(mDb.delete(eq(table.getName()), eq(query), AdditionalMatchers.aryEq(val)))
                .thenReturn(10);

        assertThat(mTransactor.deleteRowsWhere(table, asClause(table.getRowClass(), column,
                value)), is(10));
    }

    private void checkGetRowsWhereWithSize(int size) {
        DbTable<RowTag> table = mContract.getTagsTable();
        ColumnInfo<String> column = RowTag.TAG;
        RowEntry<RowTag, String> clause = asClause(RowTag.class, column, RandomString.nextWord());

        String sql = "SELECT * FROM " + table.getName() + " WHERE "
                + clause.getColumnName() + " = ?";

        Cursor cursor = getCursorWithSize(size);

        String[] value = {clause.getValue()};
        when(mDb.rawQuery(eq(sql), AdditionalMatchers.aryEq(value))).thenReturn(cursor);

        TableRowList<RowTag> rows = mTransactor.getRowsWhere(table, clause, mRowFactory);

        assertThat(rows.size(), is(size));
        verify(cursor).close();
    }

    private void checkLoadTableWithSize(int size) {
        Cursor cursor = getCursorWithSize(size);

        DbTable<RowTag> table = mContract.getTagsTable();
        String expected = "SELECT * FROM " + table.getName();

        when(mDb.rawQuery(expected, null)).thenReturn(cursor);

        TableRowList<RowTag> rows = mTransactor.loadTable(table, mRowFactory);

        assertThat(rows.size(), is(size));
        verify(cursor).close();
    }

    @NonNull
    private Cursor getCursorWithSize(int size) {
        Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(size);
        if (size > 0) {
            Boolean[] rets = new Boolean[size];
            for (int i = 0; i < size - 1; ++i) {
                rets[i] = true;
            }
            rets[size - 1] = false;
            when(cursor.moveToNext()).thenReturn(true, rets);
        }
        return cursor;
    }

    @NonNull
    private <Row extends DbTableRow> Cursor setIdInTable(DbTable<Row> table,
                                                         DbColumnDefinition idCol,
                                                         String id, boolean inTable) {
        String sql = "SELECT * FROM " + table.getName() + " WHERE "
                + idCol.getName() + " = ?";

        Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(inTable ? 1 : 0);
        if (inTable) when(cursor.moveToFirst()).thenReturn(true);

        String[] value = {id};
        when(mDb.rawQuery(eq(sql), AdditionalMatchers.aryEq(value))).thenReturn(cursor);
        return cursor;
    }

    private <Row extends DbTableRow, T> RowEntry<Row, T> asClause(Class<Row> rowClass,
                                                                  ColumnInfo<T> column,
                                                                  @Nullable T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }

    //ContentValues not mocked by Android...
    private class RowConverter extends RowToValuesConverter {
        @Override
        public <DbRow extends DbTableRow> ContentValues convert(DbTableRow<DbRow> row) {
            return mValues;
        }
    }
}
