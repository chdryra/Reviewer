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
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureUserProfileImpl;



import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.testutils.RandomString;


import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructureTest {
    private FirebaseStructure mStructure;

    @Before
    public void setUp() {
        mStructure = new FirebaseStructure();
    }

    @Test
    public void testStructureUserProfile() {
        DbUpdater.UpdateType type = DbUpdater.UpdateType.INSERT_OR_UPDATE;
        StructureUserProfileImpl profileDb = new StructureUserProfileImpl();
        profileDb.setPathToStructure(new Path<User>() {
            @Override
            public String getPath(User user) {
                String authorId = user.getAuthorId();
                return authorId != null ? "users/" + authorId + "/profile" : "";
            }
        });

        User user = randomUser(randomProfile());

        Map<String, Object> updatesMap = profileDb.getUpdatesMap(user, type);

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(3));
        assertThat(updatesMap.containsKey("users/" + user.getAuthorId() + "/profile/author/authorId"), is(true));
        assertThat(updatesMap.containsKey("users/" + user.getAuthorId() + "/profile/author/name"), is(true));
        assertThat(updatesMap.containsKey("users/" + user.getAuthorId() + "/profile/dateJoined"), is(true));
    }

    @NonNull
    private User randomUser(Profile profile) {
        return new User(RandomString.nextWord(), RandomString.nextWord(), profile);
    }

    @NonNull
    private Profile randomProfile() {
        return new Profile(new AuthorProfile(RandomAuthor.nextAuthor(),
                    RandomDataDate.nextDate()));
    }

}
