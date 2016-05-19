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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureNamesMapImpl;

import org.junit.Before;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesMapImplTest extends StructureTestBasic<User>{
    private static final String PATH = FirebaseStructure.USERS + "/" + FirebaseStructure.AUTHOR_NAMES;

    @Before
    public void setUp() {
        setStructure(new StructureNamesMapImpl(PATH));
    }

    @Override
    protected void testStructure() {
        User user = setData(randomUser());
        Profile profile = user.getProfile();
        assertNotNull(profile);
        Author author = profile.getAuthor();

        checkMapSize(1);
        checkKeyValue(path(PATH, author.getName()), author.getAuthorId());
    }
}
