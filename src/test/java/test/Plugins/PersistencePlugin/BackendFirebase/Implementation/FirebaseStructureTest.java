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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomReview;

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
public class FirebaseStructureTest {
    private FirebaseStructure mStructure;

    @Before
    public void setUp() {
        mStructure = new FirebaseStructure();
    }

    @Test
    public void testUserUpdaterInsert() {
        testUserUpdater(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testUserUpdaterDelete() {
        testUserUpdater(DbUpdater.UpdateType.DELETE);
    }

    private void testUserUpdater(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        User user = randomUser();
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();
        String authorId = author.getAuthorId();
        assertThat(user.getAuthorId(), is(authorId));

        DbUpdater<User> updater = mStructure.getUsersUpdater();
        Map<String, Object> updatesMap = updater.getUpdatesMap(user, type);
        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(5));

        String keyUsersMap = path(FirebaseStructure.USERS_MAP, user.getProviderUserId());
        assertThat(updatesMap.containsKey(keyUsersMap), is(true));
        assertThat(updatesMap.get(keyUsersMap), isDelete ? nullValue() : is((Object) user.getAuthorId()));

        String keyNamesMap = path(FirebaseStructure.USERS, FirebaseStructure.AUTHOR_NAMES, author.getName());
        assertThat(updatesMap.containsKey(keyNamesMap), is(true));
        assertThat(updatesMap.get(keyNamesMap), isDelete ? nullValue() : is((Object) authorId));

        String keyAuthorName = path(FirebaseStructure.USERS, authorId, FirebaseStructure.PROFILE, "author", "name");
        assertThat(updatesMap.containsKey(keyAuthorName), is(true));
        assertThat(updatesMap.get(keyAuthorName), isDelete ? nullValue() : is((Object) author.getName()));

        String keyAuthorId = path(FirebaseStructure.USERS, authorId, FirebaseStructure.PROFILE, "author", "authorId");
        assertThat(updatesMap.containsKey(keyAuthorId), is(true));
        assertThat(updatesMap.get(keyAuthorId), isDelete ? nullValue() : is((Object) authorId));

        String keyDate = path(FirebaseStructure.USERS, authorId, FirebaseStructure.PROFILE, "dateJoined");
        assertThat(updatesMap.containsKey(keyDate), is(true));
        assertThat(updatesMap.get(keyDate), isDelete ? nullValue() : is((Object) profile.getDateJoined()));
    }

    private void testReviewUploadUpdater(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        ReviewDb reviewDb= new ReviewDb(RandomReview.nextReview(), RandomString.nextWordArray(3));
        DbUpdater<ReviewDb> updater = mStructure.getReviewUploadUpdater();
        Map<String, Object> updatesMap = updater.getUpdatesMap(reviewDb, type);
    }

    @NonNull
    private User randomUser() {
        return new User(RandomString.nextWord(), RandomString.nextWord(), randomProfile());
    }

    private String path(String root, String... elements) {
        return Path.path(root, elements);
    }

    @NonNull
    private Profile randomProfile() {
        return new Profile(new AuthorProfile(RandomAuthor.nextAuthor(),
                    RandomDataDate.nextDate()));
    }

}
