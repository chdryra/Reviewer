/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureNamesMapImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Map;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesMapImplTest {
    private static final String PATH = FirebaseStructure.USERS + "/" + FirebaseStructure.AUTHOR_NAMES;

    @Test
    public void testInsert() {
        testStructureAuthorsMap(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testDelete() {
        testStructureAuthorsMap(DbUpdater.UpdateType.DELETE);
    }

    private void testStructureAuthorsMap(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        StructureNamesMap db = new StructureNamesMapImpl(PATH);

        User user = randomUser();
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();

        Map<String, Object> updatesMap = db.getUpdatesMap(user, type);

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));

        String key = path(PATH, author.getName());
        assertThat(updatesMap.containsKey(key), is(true));
        assertThat(updatesMap.get(key), isDelete ? nullValue() : is((Object) author.getAuthorId()));
    }

    @NonNull
    private User randomUser() {
        return new User(RandomString.nextWord(), RandomString.nextWord(), randomProfile());
    }

    @NonNull
    private Profile randomProfile() {
        return new Profile(new AuthorProfile(RandomAuthor.nextAuthor(),
                RandomDataDate.nextDate()));
    }

    private String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
