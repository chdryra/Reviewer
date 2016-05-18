/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureUsersMapImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUsersMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUsersMapImplTest {
    private static final String PATH = "ProviderUsersMap";

    @Test
    public void testInsert() {
        testStructureUsersMap(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testDelete() {
        testStructureUsersMap(DbUpdater.UpdateType.DELETE);
    }

    private void testStructureUsersMap(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;
        User user = randomUser();

        StructureUsersMap db = new StructureUsersMapImpl(PATH);
        Map<String, Object> updatesMap = db.getUpdatesMap(user, type);

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));

        String key = PATH + "/" + user.getProviderUserId();
        assertThat(updatesMap.containsKey(key), is(true));
        assertThat(updatesMap.get(key), isDelete ? nullValue() : is((Object) user.getAuthorId()));
    }

    @NonNull
    private User randomUser() {
        return new User(RandomString.nextWord(), RandomString.nextWord(), RandomString.nextWord());
    }
}
