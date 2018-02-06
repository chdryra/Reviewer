/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.StructureReviewImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;

import org.junit.Before;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewImplTest extends StructureTestBasic<ReviewDb> {
    private static final String REVIEWS
            = Path.path(FirebaseStructure.REVIEWS, FirebaseStructure.REVIEWS_DATA);

    @Before
    public void setUp() {
        setStructure(new StructureReviewImpl(REVIEWS));
    }

    @Override
    protected ReviewDb getTestData() {
        return BackendTestUtils.randomReview();
    }

    @Override
    public void testStructure(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = tester.getTestData();

        String reviewPath = Path.path(REVIEWS, reviewDb.getReviewId());

        tester.checkMapSize(reviewDb.size());
        tester.checkKeyValue(Path.path(reviewPath, "reviewId"), reviewDb.getReviewId());
        tester.checkKeyValue(Path.path(reviewPath, "subject"), reviewDb.getSubject());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "rating"), reviewDb.getRating().getRating());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "ratingWeight"), reviewDb.getRating()
                .getRatingWeight());
        tester.checkKeyValue(Path.path(reviewPath, "author", "name"), reviewDb.getAuthor().getName());
        tester.checkKeyValue(Path.path(reviewPath, "author", "authorId"), reviewDb.getAuthor().getAuthorId());
        tester.checkKeyValue(Path.path(reviewPath, "publishDate"), reviewDb.getPublishDate());
        tester.checkKeyValue(Path.path(reviewPath, "average"), reviewDb.isAverage());
        tester.checkKeyValue(Path.path(reviewPath, "tags"), reviewDb.getTags());

        tester.checkKeyList(Path.path(reviewPath, "criteria"), reviewDb.getCriteria(), BackendTestUtils.criterionGetters());
        tester.checkKeyList(Path.path(reviewPath, "comments"), reviewDb.getComments(), BackendTestUtils.commentGetters());
        tester.checkKeyList(Path.path(reviewPath, "facts"), reviewDb.getFacts(), BackendTestUtils.factGetters());
        tester.checkKeyList(Path.path(reviewPath, "images"), reviewDb.getImages(), BackendTestUtils.imageDataGetters());
        tester.checkKeyList(Path.path(reviewPath, "locations"), reviewDb.getLocations(), BackendTestUtils.locationGetters());
    }
}
