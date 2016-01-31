/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.DatabaseAndroidSqLite;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.EntryToStringConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.RowToValuesConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.SqLiteDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.SqLiteTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.TableTransactorSqLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.TablesSql;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        DbTable<RowTag> tagsTable = mContract.getTagsTable();
        TableRowList<RowTag> tags = mTransactor.loadTable(tagsTable, mRowFactory);
        String expected = "SELECT * FROM " + tagsTable.getName();
        verify(mDb).rawQuery(expected, null);

        //Cursor is null as not mocked therefore...
        assertThat(tags.size(), is(0));
    }

    @Test
    public void loadTableWithCursorSizeZero() {
        DbTable<RowTag> tagsTable = mContract.getTagsTable();
        String expected = "SELECT * FROM " + tagsTable.getName();

        Cursor cursor = mock(Cursor.class);
        when(mDb.rawQuery(expected, null)).thenReturn(cursor);
        when(cursor.getCount()).thenReturn(0);

        TableRowList<RowTag> tags = mTransactor.loadTable(tagsTable, mRowFactory);

        assertThat(tags.size(), is(0));
        verify(cursor).close();
    }

    @Test
    public void loadTableWithCursorSizeNonZero() {
        DbTable<RowTag> tagsTable = mContract.getTagsTable();
        String expected = "SELECT * FROM " + tagsTable.getName();
        Cursor cursor = mock(Cursor.class);
        when(mDb.rawQuery(expected, null)).thenReturn(cursor);

        int size = 5;
        when(cursor.getCount()).thenReturn(size);
        when(cursor.moveToNext()).thenReturn(true, true, true, true, true, false);

        TableRowList<RowTag> tags = mTransactor.loadTable(tagsTable, mRowFactory);

        assertThat(tags.size(), is(5));
        verify(cursor).close();
    }

    //ContentValues not mocked by Android...
    private class RowConverter extends RowToValuesConverter {
        @Override
        public <DbRow extends DbTableRow> ContentValues convert(DbTableRow<DbRow> row) {
            return mValues;
        }
    }
}
