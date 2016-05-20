/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureUserTagsImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 20/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserTagsImplTest extends StructureTestBasic<ReviewDb> {
    private static final String TAGS = FirebaseStructure.TAGS;

    @Before
    public void setUp() {
        StructureUserTagsImpl structure = new StructureUserTagsImpl();
        structure.setPathToStructure(TAGS);
        setStructure(structure);
    }

    @Override
    protected ReviewDb getTestData() {
        return BackendTestUtils.randomReview();
    }

    @Override
    public void testStructure(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = tester.getTestData();
        String reviewId = reviewDb.getReviewId();

        tester.checkMapSize(BackendTestUtils.NUM_TAGS);
        for(String tag : reviewDb.getTags()) {
            tester.checkKeyValue(Path.path(TAGS, tag, reviewId), true);
        }
    }
}
