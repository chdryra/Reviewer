/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;

import java.util.Map;

import test.TestUtils.RandomReview;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructureTest extends StructureTestBasic<User>{
    private FirebaseStructure mStructure;

    @Before
    public void setUp() {
        mStructure = new FirebaseStructure();
        setStructure(mStructure.getUsersUpdater());
    }

    @Override
    protected void testStructure() {
        testUserUpdater();
    }

    private void testUserUpdater() {
        User user = setData(randomUser());
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();
        String authorId = author.getAuthorId();
        assertThat(user.getAuthorId(), is(authorId));

        String usersPath = path(FirebaseStructure.USERS);
        String profilePath = path(usersPath, authorId, FirebaseStructure.PROFILE);

        checkMapSize(5);
        checkKeyValue(path(FirebaseStructure.USERS_MAP, user.getProviderUserId()), user.getAuthorId());
        checkKeyValue(path(usersPath, FirebaseStructure.AUTHOR_NAMES, author.getName()), authorId);
        checkKeyValue(path(profilePath, "author", "name"), author.getName());
        checkKeyValue(path(profilePath, "author", "authorId"), authorId);
        checkKeyValue(path(profilePath, "dateJoined"), profile.getDateJoined());

    }

    private void testReviewUploadUpdater(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        ReviewDb reviewDb= new ReviewDb(RandomReview.nextReview(), RandomString.nextWordArray(3));
        DbUpdater<ReviewDb> updater = mStructure.getReviewUploadUpdater();
        Map<String, Object> updatesMap = updater.getUpdatesMap(reviewDb, type);
    }
}
