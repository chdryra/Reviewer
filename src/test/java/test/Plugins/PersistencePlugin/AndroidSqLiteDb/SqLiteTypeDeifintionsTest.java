/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.AndroidSqLiteDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.SqLiteTypeDefinitions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SqLiteTypeDeifintionsTest {
    private SqLiteTypeDefinitions mDefs;

    @Before
    public void setUp() {
        mDefs = new SqLiteTypeDefinitions();
    }

    @Test
    public void testDefs() {
        assertThat(mDefs.getSqlTypeName(DbEntryType.TEXT), is("TEXT"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.BYTE_ARRAY), is("BLOB"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.DOUBLE), is("REAL"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.FLOAT), is("REAL"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.INTEGER), is("INTEGER"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.BOOLEAN), is("INTEGER"));
        assertThat(mDefs.getSqlTypeName(DbEntryType.LONG), is("INTEGER"));
    }
}
