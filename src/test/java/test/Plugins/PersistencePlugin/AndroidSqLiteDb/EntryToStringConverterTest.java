/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.AndroidSqLiteDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.AndroidSqLiteDb.Implementation
        .EntryToStringConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EntryToStringConverterTest {
    private static final Random RAND = new Random();
    private EntryToStringConverter mConverter;

    @Before
    public void setUp() {
        mConverter = new EntryToStringConverter();
    }

    @Test
    public void convertString() {
        String string = RandomString.nextWord();
        assertThat(mConverter.convert(getRowEntry(DbEntryType.TEXT, string)), is(string));
    }

    @Test
    public void convertBoolean() {
        assertThat(mConverter.convert(getRowEntry(DbEntryType.BOOLEAN, true)), is("1"));
        assertThat(mConverter.convert(getRowEntry(DbEntryType.BOOLEAN, false)), is("0"));
    }

    @Test
    public void convertInteger() {
        int value = RAND.nextInt();
        assertThat(mConverter.convert(getRowEntry(DbEntryType.INTEGER, value)), is(String.valueOf(value)));
    }

    @Test
    public void convertFloat() {
        float value = RAND.nextFloat();
        assertThat(mConverter.convert(getRowEntry(DbEntryType.FLOAT, value)), is(String.valueOf(value)));
    }

    @Test
    public void convertDouble() {
        double value = RAND.nextDouble();
        assertThat(mConverter.convert(getRowEntry(DbEntryType.DOUBLE, value)), is(String.valueOf(value)));
    }

    @Test
    public void convertLong() {
        long value = RAND.nextInt();
        assertThat(mConverter.convert(getRowEntry(DbEntryType.LONG, value)), is(String.valueOf(value)));
    }

    @NonNull
    private <T> RowEntryImpl<Row, T> getRowEntry(DbEntryType<T> type, T value) {
        return new RowEntryImpl<>(Row.class, new ColumnInfo<>(RandomString.nextWord(), type), value);
    }

    private class Row implements DbTableRow<Row> {
        @Override
        public String getRowId() {
            fail();
            return null;
        }

        @Override
        public String getRowIdColumnName() {
            fail();
            return null;
        }

        @Override
        public boolean hasData(DataValidator validator) {
            fail();
            return false;
        }

        @Override
        public Iterator<RowEntry<Row, ?>> iterator() {
            fail();
            return null;
        }
    }
}
