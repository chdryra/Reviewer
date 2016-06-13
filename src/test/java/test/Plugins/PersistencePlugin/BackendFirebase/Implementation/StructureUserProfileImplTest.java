/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureUserProfileImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserProfileImplTest extends StructureTestBasic<User> {
    private static final String USERS = FirebaseStructure.USERS;
    private static final String PROFILE = FirebaseStructure.PROFILE;

    @Before
    public void setUp() {
        setStructure(new StructureUserProfileImpl(new Path<User>() {
            @Override
            public String getPath(User user) {
                String authorId = user.getAuthorId();
                return authorId != null ? path(USERS, authorId, PROFILE) : "";
            }
        }));
    }

    @Override
    protected User getTestData() {
        return BackendTestUtils.randomUser();
    }

    @Override
    public void testStructure(StructureTester<User> tester) {
        User user = tester.getTestData();
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();

        String profilePath = Path.path(USERS, user.getAuthorId(), PROFILE);

        tester.checkMapSize(3);
        tester.checkKeyValue(Path.path(profilePath, "author", "authorId"), author.getAuthorId());
        tester.checkKeyValue(Path.path(profilePath, "author", "name"), author.getName());
        tester.checkKeyValue(Path.path(profilePath, "dateJoined"), profile.getDateJoined());   
    }

}
