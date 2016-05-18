/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation
        .StructureUserProfileImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
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
public class StructureUserProfileImplTest {
    @Test
    public void testInsert() {
        testStructureUserProfile(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testDelete() {
        testStructureUserProfile(DbUpdater.UpdateType.DELETE);
    }

    private void testStructureUserProfile(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        StructureUserProfile db = new StructureUserProfileImpl(new Path<User>() {
            @Override
            public String getPath(User user) {
                String authorId = user.getAuthorId();
                return authorId != null ? "users/" + authorId + "/profile" : "";
            }
        });

        User user = randomUser();
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();

        Map<String, Object> updatesMap = db.getUpdatesMap(user, type);

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(3));

        String keyId = "users/" + user.getAuthorId() + "/profile/author/authorId";
        String keyName = "users/" + user.getAuthorId() + "/profile/author/name";
        String keyDate = "users/" + user.getAuthorId() + "/profile/dateJoined";
        assertThat(updatesMap.containsKey(keyId), is(true));
        assertThat(updatesMap.containsKey(keyName), is(true));
        assertThat(updatesMap.containsKey(keyDate), is(true));

        assertThat(updatesMap.get(keyId), isDelete ? nullValue() : is((Object) author.getAuthorId()));
        assertThat(updatesMap.get(keyName), isDelete ? nullValue() : is((Object) author.getName()));
        assertThat(updatesMap.get(keyDate), isDelete ? nullValue() : is((Object) profile.getDateJoined()));
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
}
