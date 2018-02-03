/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.StructureTagsImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureTagsImplTest extends StructureTestBasic<ReviewDb> {
    private static final String TAGS
            = Path.path(FirebaseStructure.TAGS);
    private static final String REVIEWS = FirebaseStructure.REVIEWS;
    private static final String USERS = FirebaseStructure.USERS;

    @Before
    public void setUp() {
        setStructure(new StructureTagsImpl(REVIEWS, USERS, TAGS));
    }

    @Override
    protected ReviewDb getTestData() {
        return BackendTestUtils.randomReview();
    }

    @Override
    public void testStructure(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = tester.getTestData();
        String reviewId = reviewDb.getReviewId();
        String authorId = reviewDb.getAuthor().getAuthorId();

        tester.checkMapSize(BackendTestUtils.NUM_TAGS * 2);
        for(String tag : reviewDb.getTags()) {
            tester.checkKeyValue(Path.path(TAGS, tag, REVIEWS, reviewId), true);
            tester.checkKeyValue(Path.path(TAGS, tag, USERS, authorId, reviewId), true);
        }
    }
}
