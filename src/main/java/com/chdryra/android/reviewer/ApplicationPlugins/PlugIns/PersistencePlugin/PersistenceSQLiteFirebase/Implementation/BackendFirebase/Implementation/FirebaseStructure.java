/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviewsList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureTags;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUsersMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.StructureBuilder;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructure {
    //public for testing
    public static final String REVIEWS = "Reviews";
    public static final String REVIEWS_DATA = "Data";
    public static final String REVIEWS_LIST = "List";
    public static final String TAGS = "Tags";
    public static final String USERS = "Users";
    public static final String AUTHOR_NAMES = "AuthorNames";
    public static final String PROFILE = "Profile";
    public static final String USERS_MAP = "ProviderUsersMap";
    public static final String FEED = "Feed";

    private final StructureUserProfile mUserProfile;
    private final StructureUsersMap mUsersMap;
    private final StructureNamesMap mAuthorsMap;
    private final StructureReviews mReviews;
    private final StructureReviewsList mReviewsList;
    private final DbStructure<ReviewDb> mUserData;
    private final StructureTags mTags;

    private final DbUpdater<User> mUserUpdater;
    private final DbUpdater<ReviewDb> mReviewUploadUpdater;

    public FirebaseStructure() {
        Path<User> pathToProfile = new Path<User>() {
            @Override
            public String getPath(User user) {
                String authorId = user.getAuthorId();
                if(authorId == null) throw new IllegalArgumentException("User must have authorId");
                return pathToProfile(authorId);
            }
        };
        mUserProfile = new StructureUserProfileImpl(pathToProfile);
        mUsersMap = new StructureUsersMapImpl(pathToUserAuthorMap());
        mAuthorsMap = new StructureNamesMapImpl(pathToNamesAuthorIdMap());

        StructureBuilder<User> builderUser = new StructureBuilder<>();
        mUserUpdater = builderUser.add(mUserProfile).add(mUsersMap).add(mAuthorsMap).build();

        mReviews = new StructureReviewsImpl(pathToReviewsData());
        mReviewsList = new StructureReviewsListImpl(pathToReviewsList());
        mTags = new StructureTagsImpl(REVIEWS, USERS, pathToTags());
        Path<ReviewDb> pathToUserData = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToAuthor(review.getAuthor().getAuthorId());
            }
        };
        StructureBuilder<ReviewDb> builder = new StructureBuilder<>();
        mUserData = builder.add(REVIEWS, new StructureReviewsListImpl())
                .add(FEED, new StructureReviewsListImpl())
                .add(TAGS, new StructureUserTagsImpl())
                .setPath(pathToUserData)
                .build();

        StructureBuilder<ReviewDb> builderReview = new StructureBuilder<>();
        mReviewUploadUpdater = builderReview
                .add(mReviews).add(mReviewsList).add(mTags).add(mUserData).build();

    }

    public DbUpdater<User> getProfileUpdater() {
        return mUserProfile;
    }

    public DbUpdater<User> getUsersUpdater() {
        return mUserUpdater;
    }

    public DbUpdater<ReviewDb> getReviewUploadUpdater() {
        return mReviewUploadUpdater;
    }

    public Firebase getUserAuthorMappingDb(Firebase root, String userId) {
        return root.child(pathToUserAuthorMapping(userId));
    }

    public Firebase getReviewsDb(Firebase root) {
        return root.child(pathToReviewsData());
    }

    public Firebase getReviewsListDb(Firebase root) {
        return root.child(pathToReviewsList());
    }

    public Firebase getProfileDb(Firebase root, String authorId) {
        return root.child(pathToProfile(authorId));
    }

    public Firebase getFeedDb(Firebase root, String authorId) {
        return root.child(pathToFeed(authorId));
    }

    public Firebase getAuthorNameMappingDb(Firebase root, String name) {
        return root.child(pathToAuthorNameMapping(name));
    }

    public Firebase getReviewDb(Firebase root, String reviewId) {
        return root.child(pathToReview(reviewId));
    }

    private String pathToFeed(String authorId) {
        return path(pathToAuthor(authorId), FEED);
    }

    private String pathToAuthorNameMapping(String name) {
        return path(pathToNamesAuthorIdMap(), mAuthorsMap.relativePathToAuthor(name));
    }

    private String pathToReview(String reviewId) {
        return path(pathToReviews(), mReviews.relativePathToReview(reviewId));
    }

    private String pathToReviewsData() {
        return path(pathToReviews(), REVIEWS_DATA);
    }

    private String pathToReviewsList() {
        return path(pathToReviews(), REVIEWS_LIST);
    }

    private String pathToUserAuthorMapping(String userId) {
        return path(pathToUserAuthorMap(), mUsersMap.relativePathToUser(userId));
    }

    private String pathToProfile(String authorId) {
        return path(pathToAuthor(authorId), PROFILE);
    }

    private String pathToUserAuthorMap() {
        return USERS_MAP;
    }

    private String pathToAuthor(String authorId) {
        return path(pathToUsers(), authorId);
    }

    private String pathToNamesAuthorIdMap() {
        return path(pathToUsers(), AUTHOR_NAMES);
    }

    private String pathToReviews() {
        return REVIEWS;
    }

    private String pathToTags() {
        return TAGS;
    }

    private String pathToUsers() {
        return USERS;
    }

    private String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
