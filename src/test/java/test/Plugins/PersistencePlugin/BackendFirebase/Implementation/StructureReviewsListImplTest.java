/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.StructureReviewsListImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewsListImplTest extends StructureTestBasic<ReviewDb> {
    private static final String REVIEWS
            = Path.path(FirebaseStructure.REVIEWS, FirebaseStructure.REVIEWS_LIST);

    @Before
    public void setUp() {
        setStructure(new StructureReviewsListImpl(REVIEWS));
    }

    @Override
    protected ReviewDb getTestData() {
        return BackendTestUtils.randomReview();
    }

    @Override
    public void testStructure(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = tester.getTestData();

        String reviewPath = Path.path(REVIEWS, reviewDb.getReviewId());

        tester.checkMapSize(4);
        tester.checkKeyValue(Path.path(reviewPath, "subject"), reviewDb.getSubject());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "rating"), reviewDb.getRating().getRating());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "ratingWeight"), reviewDb.getRating().getRatingWeight());
        tester.checkKeyValue(Path.path(reviewPath, "publishDate"), reviewDb.getPublishDate());
    }
}
