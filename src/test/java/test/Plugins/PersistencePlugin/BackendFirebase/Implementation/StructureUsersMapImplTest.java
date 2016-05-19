/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureUsersMapImpl;

import org.junit.Before;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUsersMapImplTest extends StructureTestBasic<User>{
    private static final String USERS_MAP = FirebaseStructure.USERS_MAP;

    @Before
    public void setUp() {
        setStructure(new StructureUsersMapImpl(USERS_MAP));
    }

    @Override
    protected void testStructure() {
        User user = setData(randomUser());

        checkMapSize(1);
        checkKeyValue(path(USERS_MAP, user.getProviderUserId()), user.getAuthorId());
    }
}
