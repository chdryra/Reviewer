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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Implementation
        .StructureNamesMapImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesMapImplTest extends StructureTestBasic<User>{
    private static final String PATH = FirebaseStructure.USERS + "/" + FirebaseStructure.AUTHOR_NAMES;

    @Before
    public void setUp(){
        setStructure(new StructureNamesMapImpl(PATH));
    }

    @Override
    protected User getTestData() {
        return BackendTestUtils.randomUser();
    }

    @Override
    public void testStructure(StructureTester<User> tester) {
        Author author = getAuthor(tester);

        tester.checkMapSize(1);
        tester.checkKeyValue(Path.path(PATH, author.getName()), author.getAuthorId());
    }

    private Author getAuthor(StructureTester<User> tester) {
        User user = tester.getTestData();
        Profile profile = user.getProfile();
        assertNotNull(profile);
        return profile.getAuthor();
    }
}
