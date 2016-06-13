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
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbStructReviewsLed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Before;
import org.junit.Test;

import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.BackendTestUtils;
import test.Plugins.PersistencePlugin.BackendFirebase.TestUtils.StructureTester;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructureTest {
    private FirebaseStructure mStructure;

    @Before
    public void setUp() {
        mStructure = new FbStructReviewsLed();
    }

    @Test
    public void UserUpdaterInsertsIntoUserAuthorMap() {
        testUserInsert(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testAuthorMappingDb(tester);
            }
        });
    }

    @Test
    public void UserUpdaterDeletesUserAuthorMapping() {
        testUserDelete(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testAuthorMappingDb(tester);
            }
        });
    }

    @Test
    public void UserUpdaterInsertsIntoAuthorNamesMap() {
        testUserInsert(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testAuthorNameMappingDb(tester);
            }
        });
    }

    @Test
    public void UserUpdaterDeletesAuthorNamesMapping() {
        testUserDelete(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testAuthorNameMappingDb(tester);
            }
        });
    }

    @Test
    public void UserUpdaterInsertsIntoProfileDb() {
        testUserInsert(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testProfileDb(tester);
            }
        });
    }

    @Test
    public void UserUpdaterDeletesProfile() {
        testUserDelete(new StructureTester.Testable<User>() {
            @Override
            public void testStructure(StructureTester<User> tester) {
                testProfileDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterInsertsIntoReviewsDb() {
        testReviewInsert(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testReviewsDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterDeletesReviewFromReviewDb() {
        testReviewDelete(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testReviewsDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterInsertsIntoReviewsListDb() {
        testReviewInsert(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testReviewsListDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterDeletesReviewFromReviewsListDb() {
        testReviewDelete(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testReviewsListDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterInsertsIntoTagsDb() {
        testReviewInsert(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testTagsDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterDeletesTagsEntryFromTagsDb() {
        testReviewDelete(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testTagsDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterInsertsIntoUserDataDb() {
        testReviewInsert(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testUserDataDb(tester);
            }
        });
    }

    @Test
    public void ReviewUploadUpdaterDeletesFromUserDataDb() {
        testReviewDelete(new StructureTester.Testable<ReviewDb>() {
            @Override
            public void testStructure(StructureTester<ReviewDb> tester) {
                testUserDataDb(tester);
            }
        });
    }

    private void testUserInsert(StructureTester.Testable<User> testable) {
        testInsert(testable, mStructure.getUsersUpdater(), getRandomUser());
    }

    private void testUserDelete(StructureTester.Testable<User> testable) {
        testDelete(testable, mStructure.getUsersUpdater(), getRandomUser());
    }

    private void testReviewInsert(StructureTester.Testable<ReviewDb> testable) {
        testInsert(testable, mStructure.getReviewUploadUpdater(), getRandomReview());
    }

    private void testReviewDelete(StructureTester.Testable<ReviewDb> testable) {
        testDelete(testable, mStructure.getReviewUploadUpdater(), getRandomReview());
    }

    private <T> void testInsert(StructureTester.Testable<T> testable, DbUpdater<T> updater, T data) {
        new StructureTester<>(updater, testable).testInsertOrReplace(data);
    }

    private <T> void testDelete(StructureTester.Testable<T> testable, DbUpdater<T> updater,T data) {
        new StructureTester<>(updater, testable).testDelete(data);
    }

    private void testAuthorMappingDb(StructureTester<User> tester) {
        User user = getUser(tester);

        checkUserUpdatesMapSize(tester);
        tester.checkKeyValue(Path.path(FirebaseStructure.USERS_MAP, user.getProviderUserId()),
                user.getAuthorId());
    }

    private void testAuthorNameMappingDb(StructureTester<User> tester) {
        checkUserUpdatesMapSize(tester);

        Author author = getAuthor(tester);
        String namesPath = Path.path(FirebaseStructure.USERS, FirebaseStructure.AUTHOR_NAMES);
        tester.checkKeyValue(Path.path(namesPath, author.getName()), author.getAuthorId());
    }

    private void testProfileDb(StructureTester<User> tester) {
        User user = getUser(tester);
        Profile profile = getProfile(tester);
        Author author = getAuthor(tester);

        String profilePath = Path.path(FirebaseStructure.USERS, user.getAuthorId(),
                FirebaseStructure.PROFILE);

        checkUserUpdatesMapSize(tester);
        tester.checkKeyValue(Path.path(profilePath, "author", "authorId"), author.getAuthorId());
        tester.checkKeyValue(Path.path(profilePath, "author", "name"), author.getName());
        tester.checkKeyValue(Path.path(profilePath, "dateJoined"), profile.getDateJoined());
    }

    private void checkUserUpdatesMapSize(StructureTester<User> tester) {
        tester.checkMapSize(5);
    }

    private void testReviewsDb(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = getReview(tester);

        String reviewPath = Path.path(FirebaseStructure.REVIEWS, FirebaseStructure.REVIEWS_DATA, reviewDb.getReviewId());

        checkReviewUploadUpdatesMapSize(tester);
        tester.checkKeyValue(Path.path(reviewPath, "reviewId"), reviewDb.getReviewId());
        tester.checkKeyValue(Path.path(reviewPath, "subject"), reviewDb.getSubject());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "rating"), reviewDb.getRating()
                .getRating());
        tester.checkKeyValue(Path.path(reviewPath, "rating", "ratingWeight"), reviewDb.getRating()
                .getRatingWeight());
        tester.checkKeyValue(Path.path(reviewPath, "author", "name"), reviewDb.getAuthor()
                .getName());
        tester.checkKeyValue(Path.path(reviewPath, "author", "authorId"), reviewDb.getAuthor()
                .getAuthorId());
        tester.checkKeyValue(Path.path(reviewPath, "publishDate"), reviewDb.getPublishDate());
        tester.checkKeyValue(Path.path(reviewPath, "average"), reviewDb.isAverage());
        tester.checkKeyValue(Path.path(reviewPath, "tags"), reviewDb.getTags());

        tester.checkKeyList(Path.path(reviewPath, "criteria"), reviewDb.getCriteria(),
                BackendTestUtils.criterionGetters());
        tester.checkKeyList(Path.path(reviewPath, "comments"), reviewDb.getComments(),
                BackendTestUtils.commentGetters());
        tester.checkKeyList(Path.path(reviewPath, "facts"), reviewDb.getFacts(), BackendTestUtils
                .factGetters());
        tester.checkKeyList(Path.path(reviewPath, "images"), reviewDb.getImages(),
                BackendTestUtils.imageDataGetters());
        tester.checkKeyList(Path.path(reviewPath, "locations"), reviewDb.getLocations(),
                BackendTestUtils.locationGetters());
    }

    private void testReviewsListDb(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = getReview(tester);
        checkReviewUploadUpdatesMapSize(tester);
        String reviewPath = Path.path(FirebaseStructure.REVIEWS, FirebaseStructure.REVIEWS_LIST, reviewDb.getReviewId());
        testListEntry(tester, reviewDb, reviewPath);
    }

    private ReviewDb getReview(StructureTester<ReviewDb> tester) {
        return tester.getTestData();
    }

    private void testTagsDb(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = getReview(tester);
        String reviewId = getReviewId(tester);
        String authorId = getAuthorId(tester);

        checkReviewUploadUpdatesMapSize(tester);
        for(String tag : reviewDb.getTags()) {
            tester.checkKeyValue(Path.path(FirebaseStructure.TAGS, tag, FirebaseStructure.REVIEWS, reviewId), true);
            tester.checkKeyValue(Path.path(FirebaseStructure.TAGS, tag, FirebaseStructure.USERS, authorId, reviewId), true);
        }
    }

    private void testUserDataDb(StructureTester<ReviewDb> tester) {
        ReviewDb reviewDb = getReview(tester);
        String reviewId = getReviewId(tester);
        String authorId = getAuthorId(tester);

        String authorPath = Path.path(FirebaseStructure.USERS, authorId);

        checkReviewUploadUpdatesMapSize(tester);
        for(String tag : reviewDb.getTags()) {
            tester.checkKeyValue(Path.path(authorPath, FirebaseStructure.TAGS, tag, reviewId), true);
        }

        String reviewPath = Path.path(authorPath, FirebaseStructure.REVIEWS, reviewDb.getReviewId());
        testListEntry(tester, reviewDb, reviewPath);

        String feedPath = Path.path(authorPath, FirebaseStructure.FEED, reviewDb.getReviewId());
        testListEntry(tester, reviewDb, feedPath);
    }

    private void testListEntry(StructureTester<ReviewDb> tester, ReviewDb reviewDb, String path) {
        tester.checkKeyValue(Path.path(path, "subject"), reviewDb.getSubject());
        tester.checkKeyValue(Path.path(path, "rating", "rating"), reviewDb.getRating()
                .getRating());
        tester.checkKeyValue(Path.path(path, "rating", "ratingWeight"), reviewDb.getRating()
                .getRatingWeight());
        tester.checkKeyValue(Path.path(path, "publishDate"), reviewDb.getPublishDate());
    }

    private String getAuthorId(StructureTester<ReviewDb> tester) {
        return getReview(tester).getAuthor().getAuthorId();
    }

    private String getReviewId(StructureTester<ReviewDb> tester) {
        return getReview(tester).getReviewId();
    }

    private void checkReviewUploadUpdatesMapSize(StructureTester<ReviewDb> tester) {
        tester.checkMapSize(35);
    }

    @NonNull
    private User getRandomUser() {
        return BackendTestUtils.randomUser();
    }

    private ReviewDb getRandomReview() {
        return BackendTestUtils.randomReview();
    }

    private User getUser(StructureTester<User> tester) {
        return tester.getTestData();
    }

    @NonNull
    private Profile getProfile(StructureTester<User> tester) {
        Profile profile = getUser(tester).getProfile();
        assertNotNull(profile);
        return profile;
    }

    @NonNull
    private Author getAuthor(StructureTester<User> tester) {
        User user = getUser(tester);
        Profile profile = getProfile(tester);

        Author author = profile.getAuthor();
        String authorId = author.getAuthorId();
        assertThat(user.getAuthorId(), is(authorId));
        return author;
    }
}
