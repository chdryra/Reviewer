/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;

import org.junit.Test;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 19/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class StructureTestBasic<T> implements StructureTester.Testable<T> {
    private StructureTester<T> mTester;

    protected void setStructure(DbUpdater<T> structure) {
        mTester = new StructureTester<>(structure, this);
    }

    @Test
    public void testInsertOrReplace() {
        mTester.testInsertOrReplace(getTestData());
    }

    @Test
    public void testDelete() {
        mTester.testDelete(getTestData());
    }

    @Override
    public abstract void testStructure(StructureTester<T> tester);

    protected abstract T getTestData();
}
