/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.RelationalDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbColumnNotNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbColumnNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbTableImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.ForeignKeyConstraintImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.ForeignKeyConstraint;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbTableImplTest {
    private static final int NUM = 3;
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    private DbTableForTesting<DbTableRow> mTable;

    @Before
    public void setUp() {
        mTable = new DbTableForTesting<>(RandomString.nextWord(), DbTableRow.class);
    }

    @Test
    public void getNameReturnsCorrectName() {
        String tableName = RandomString.nextWord();
        DbTableImpl<DbTableRow> table = new DbTableImpl<>(tableName, DbTableRow.class);
        assertThat(table.getName(), is(tableName));
    }

    @Test
    public void getRowClassReturnsCorrectClass() {
        Class<DbTableRow> rowClass = DbTableRow.class;
        DbTableImpl<DbTableRow> table = new DbTableImpl<>(RandomString.nextWord(), rowClass);
        assertThat(table.getRowClass().equals(rowClass), is(true));
    }

    @Test
    public void addPrimaryKeyThrowsIllegalArgumentExceptionOnNullableColumnPassed() {
        mExpectedException.expect(IllegalArgumentException.class);
        DbColumnDefinition col = new DbColumnNullable(RandomString.nextWord(), DbEntryType.TEXT);
        mTable.addPrimaryKeyColumn(col);

    }

    @Test
    public void addPrimaryKeyAddsPrimaryKeys() {
        ArrayList<DbColumnDefinition> pks = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            pks.add(col);
            mTable.addPrimaryKeyColumn(col);
        }

        assertThat(mTable.getPrimaryKeys(), is(pks));
    }

    @Test
    public void addColumnsAddsColumns() {
        ArrayList<DbColumnDefinition> cols = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            cols.add(col);
            mTable.addColumn(col);
        }

        assertThat(mTable.getColumns(), is(cols));
    }

    @Test
    public void getColumnsGetsPrimaryKeysAndOtherColumnsInThatOrder() {
        ArrayList<DbColumnDefinition> cols = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            cols.add(col);
            mTable.addPrimaryKeyColumn(col);
        }
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            cols.add(col);
            mTable.addColumn(col);
        }

        assertThat(mTable.getColumns(), is(cols));
    }

    @Test
    public void getColumnNamesGetsPrimaryKeysAndOtherColumnsInThatOrder() {
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            cols.add(col.getName());
            mTable.addPrimaryKeyColumn(col);
        }
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            cols.add(col.getName());
            mTable.addColumn(col);
        }

        assertThat(mTable.getColumnNames(), is(cols));
    }

    @Test
    public void getColumnReturnsCorrectColumn() {
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            mTable.addColumn(col);
        }

        String ofInterest = RandomString.nextWord();
        DbColumnDefinition col = new DbColumnNotNullable(ofInterest, DbEntryType.TEXT);
        mTable.addColumn(col);

        assertThat(mTable.getColumn(ofInterest), is(col));
    }

    @Test
    public void getColumnReturnsNullIfNotFound() {
        for (int i = 0; i < NUM; ++i) {
            String columnName = RandomString.nextWord();
            DbColumnDefinition col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
            mTable.addColumn(col);
        }

        String ofInterest = RandomString.nextWord();
        DbColumnDefinition col = new DbColumnNotNullable(ofInterest, DbEntryType.TEXT);
        mTable.addColumn(col);

        assertThat(mTable.getColumn(RandomString.nextWord()), is(nullValue()));
    }

    @Test
    public void addForeignKeyConstraintThrowsIllegalArgumentExceptionIfColumnNotFound() {
        mExpectedException.expect(IllegalArgumentException.class);
        ArrayList<DbColumnDefinition> cols = new ArrayList<>();
        cols.add(new DbColumnNullable(RandomString.nextWord(), DbEntryType.BYTE_ARRAY));
        ForeignKeyConstraint<DbTableRow> constraint = new ForeignKeyConstraintImpl<>(cols, mTable);
        mTable.addForeignKeyConstraint(constraint);
    }

    @Test
    public void getForeignKeyConstraintGetsCorrectConstraints() {
        DbColumnDefinition col1 = new DbColumnNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col2 = new DbColumnNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col3 = new DbColumnNullable(RandomString.nextWord(), DbEntryType.TEXT);

        mTable.addColumn(col1);
        mTable.addColumn(col2);
        mTable.addColumn(col3);

        DbTableForTesting<DbTableRow> fkTable1 = new DbTableForTesting<>(RandomString.nextWord(),
                DbTableRow.class);
        DbTableForTesting<DbTableRow> fkTable2 = new DbTableForTesting<>(RandomString.nextWord(),
                DbTableRow.class);

        fkTable1.addPrimaryKeyColumn(new DbColumnNotNullable(RandomString.nextWord(), DbEntryType
                .TEXT));
        fkTable2.addPrimaryKeyColumn(new DbColumnNotNullable(RandomString.nextWord(), DbEntryType
                .TEXT));
        fkTable2.addPrimaryKeyColumn(new DbColumnNotNullable(RandomString.nextWord(), DbEntryType
                .TEXT));

        ArrayList<DbColumnDefinition> fk1Cols = new ArrayList<>();
        fk1Cols.add(col1);
        ArrayList<DbColumnDefinition> fk2Cols = new ArrayList<>();
        fk2Cols.add(col2);
        fk2Cols.add(col3);
        ForeignKeyConstraint<DbTableRow> fk1 = new ForeignKeyConstraintImpl<>(fk1Cols, fkTable1);
        ForeignKeyConstraint<DbTableRow> fk2 = new ForeignKeyConstraintImpl<>(fk2Cols, fkTable2);

        ArrayList<ForeignKeyConstraint<DbTableRow>> fksIn = new ArrayList<>();
        fksIn.add(fk1);
        fksIn.add(fk2);

        mTable.addForeignKeyConstraint(fk1);
        mTable.addForeignKeyConstraint(fk2);

        assertThat(mTable.getForeignKeyConstraints().equals(fksIn), is(true));
    }

}
