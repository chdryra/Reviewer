/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.StructureUsersAuthorsMapImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUsersAuthorsMapImplTest extends StructureTestBasic<User> {
    private static final String USERS_MAP = FirebaseStructure.ProviderIds_AuthorIds;

    @Before
    public void setUp() {
        setStructure(new StructureUsersAuthorsMapImpl(USERS_MAP));
    }

    @Override
    protected User getTestData() {
        return BackendTestUtils.randomUser();
    }

    @Override
    public void testStructure(StructureTester<User> tester) {
        User user = tester.getTestData();

        tester.checkMapSize(1);
        tester.checkKeyValue(Path.path(USERS_MAP, user.getProviderUserId()), user.getAuthorId());
    }
}
