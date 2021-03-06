/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.RelationalDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbColumnNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.ValueNullable;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnNullableTest {
    @Test
    public void getNameReturnsCorrectName() {
        String columnName = RandomString.nextWord();
        DbColumnNullable col = new DbColumnNullable(columnName, DbEntryType.TEXT);
        assertThat(col.getName(), is(columnName));
    }

    @Test
    public void getTypeReturnsCorrectType() {
        DbEntryType<?> text = DbEntryType.TEXT;
        DbColumnNullable col = new DbColumnNullable(RandomString.nextWord(), text);
        assertThat(col.getType().equals(text), is(true));
    }

    @Test
    public void notNullableReturnsTrue() {
        DbColumnNullable col = new DbColumnNullable(RandomString.nextWord(), DbEntryType.TEXT);
        assertThat(col.getNullable(), is(ValueNullable.TRUE));
    }
}
