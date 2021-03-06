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
public class DbColumnNotNullableTest {
    @Test
    public void getNameReturnsCorrectName() {
        String columnName = RandomString.nextWord();
        DbColumnNotNullable col = new DbColumnNotNullable(columnName, DbEntryType.TEXT);
        assertThat(col.getName(), is(columnName));
    }

    @Test
    public void getTypeReturnsCorrectType() {
        DbEntryType<?> text = DbEntryType.TEXT;
        DbColumnNotNullable col = new DbColumnNotNullable(RandomString.nextWord(), text);
        assertThat(col.getType().equals(text), is(true));
    }

    @Test
    public void notNullableReturnsFalse() {
        DbColumnNotNullable col = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType
                .TEXT);
        assertThat(col.getNullable(), is(ValueNullable.FALSE));
    }
}
