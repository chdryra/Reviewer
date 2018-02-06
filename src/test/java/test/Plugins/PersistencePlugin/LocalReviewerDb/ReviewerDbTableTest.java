/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.ValueNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.ForeignKeyConstraint;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewerDbTableTest<T extends DbTableRow> {
    private String mTableName;
    private ColumnInfo<?>[] mCols;
    private ColumnInfo<?>[] mNullables;
    private DbTable<T> mTableToTest;
    private Map<DbTable, ArrayList<ColumnInfo<?>>> mFkMap = new HashMap<>();

    protected abstract DbTable<T> getTableToTest(FactoryDbColumnDef colFactory,
                                                 FactoryForeignKeyConstraint constraintFactory);
    protected void setFkConstraints() {};

    public ReviewerDbTableTest(String tableName, ColumnInfo<?>[] cols, ColumnInfo<?>[] nullables) {
        mTableName = tableName;
        mCols = cols;
        mNullables = nullables;
        mTableToTest = getTableToTest(new FactoryDbColumnDef(), new FactoryForeignKeyConstraint());
        setFkConstraints();
    }

    @Test
    public void tableNameIsCorrect() {
        assertThat(mTableToTest.getName(), is(mTableName));
    }

    @Test
    public void primaryKeyIsCorrect() {
        ArrayList<DbColumnDefinition> pks = mTableToTest.getPrimaryKeys();
        assertThat(pks.size(), is(1));
        DbColumnDefinition pk = pks.get(0);
        assertThat(pk.getNullable(), is(ValueNullable.FALSE));
        checkColumn(pk, mCols[0]);
    }

    @Test
    public void columnsAreCorrect() {
        ArrayList<DbColumnDefinition> cols = mTableToTest.getColumns();
        assertThat(cols.size(), is(mCols.length));

        for(int i = 0; i < cols.size(); ++i) {
            checkColumn(cols.get(i), mCols[i]);
        }
    }

    @Test
    public void checkNullabilityOfColumns() {
        ArrayList<String> nullables = new ArrayList<>();
        for(ColumnInfo<?> col : mNullables) {
            nullables.add(col.getName());
        }
        
        for(ColumnInfo<?> col : mCols) {
            DbColumnDefinition column = mTableToTest.getColumn(col.getName());
            if(nullables.contains(column.getName())) {
                assertThat(column.getNullable(), is(ValueNullable.TRUE));
            } else {
                assertThat(column.getNullable(), is(ValueNullable.FALSE));
            }
        }
    }

    @Test
    public void checkConstraints() {
        ArrayList<ForeignKeyConstraint<?>> constraints = mTableToTest.getForeignKeyConstraints();
        for(ForeignKeyConstraint<?> constraint : constraints) {
            DbTable<?> table = constraint.getForeignTable();
            ArrayList<DbColumnDefinition> fkCols = constraint.getFkColumns();
            ArrayList<ColumnInfo<?>> expected = mFkMap.get(table);
            assertNotNull(expected);
            assertThat(fkCols.size(), is(expected.size()));
            for(int i = 0; i < expected.size(); ++i) {
                assertThat(fkCols.get(i).getName(), is(expected.get(i).getName()));
            }
        }
    }

    protected void addConstraint(DbTable<?> table, ColumnInfo<?> col) {
        ArrayList<ColumnInfo<?>> cols = mFkMap.get(table);
        if(cols == null) {
            cols = new ArrayList<>();
            mFkMap.put(table, cols);
        }
        cols.add(col);
    }

    private void checkColumn(DbColumnDefinition col, ColumnInfo<?> expected) {
        assertThat(col.getName(), is(expected.getName()));
        assertThat(col.getType().equals(expected.getType()), is(true));
    }
}
