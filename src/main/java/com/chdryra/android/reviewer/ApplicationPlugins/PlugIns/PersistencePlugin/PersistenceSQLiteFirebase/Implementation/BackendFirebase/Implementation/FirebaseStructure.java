/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation
        .ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.PathMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.UpdaterBuilder;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviews;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Interfaces.StructureTags;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Interfaces.StructureUserData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUsersMap;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructure {
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
    private final StructureUserData mUserData;
    private final StructureTags mTags;

    private final DbUpdater<User> mUserUpdater;
    private final DbUpdater<ReviewDb> mReviewUploadUpdater;

    public FirebaseStructure() {
        mUserProfile = new StructureUserProfileImpl();
        mUsersMap = new StructureUsersMapImpl();
        mAuthorsMap = new StructureNamesMapImpl();
        mUserData = new StructureUserDataImpl(REVIEWS, TAGS, FEED);
        mReviews = new StructureReviewsImpl(REVIEWS_DATA, REVIEWS_LIST);
        mTags = new StructureTagsImpl(REVIEWS, USERS);

        mUserProfile.setPathMaker(new PathMaker<User>() {
            @Override
            public String getPath(User user) {
                String authorId = user.getAuthorId();
                return authorId != null ? pathToProfile(authorId) : "";
            }
        });

        mUsersMap.setPathMaker(new PathMaker<User>() {
            @Override
            public String getPath(User user) {
                return pathToUserAuthorMap();
            }
        });

        mAuthorsMap.setPathMaker(new PathMaker<User>() {
            @Override
            public String getPath(User item) {
                return pathToNamesAuthorIdMap();
            }
        });

        mUserData.setPathMaker(new PathMaker<ReviewDb>() {
            @Override
            public String getPath(ReviewDb item) {
                return pathToAuthor(item.getAuthor().getAuthorId());
            }
        });

        mTags.setPathMaker(new PathMaker<ReviewDb>() {
            @Override
            public String getPath(ReviewDb item) {
                return pathToTags();
            }
        });

        mReviews.setPathMaker(new PathMaker<ReviewDb>() {
            @Override
            public String getPath(ReviewDb item) {
                return pathToReviews();
            }
        });

        UpdaterBuilder<ReviewDb> builderReview = new UpdaterBuilder<>();
        mReviewUploadUpdater = builderReview.add(mReviews).add(mTags).add(mUserData).build();

        UpdaterBuilder<User> builderUser = new UpdaterBuilder<>();
        mUserUpdater = builderUser.add(mUserProfile).add(mUsersMap).add(mAuthorsMap).build();
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

    public String pathToUserAuthorMapping(String userId) {
        return path(pathToUserAuthorMap(), mUsersMap.getPathToUserAuthorMapping(userId));
    }

    public String pathToUserAuthorMap() {
        return USERS_MAP;
    }

    public String pathToReviewsData() {
        return path(pathToReviews(), mReviews.getReviewDataPath());
    }

    public String pathToReviewsList() {
        return path(pathToReviews(), mReviews.getReviewListPath());
    }

    public String pathToProfile(String authorId) {
        return path(pathToAuthor(authorId), PROFILE);
    }

    public String pathToFeed(String authorId) {
        return path(pathToAuthor(authorId), mUserData.getPathToFeed());
    }

    public String pathToAuthor(String authorId) {
        return path(pathToUsers(), authorId);
    }

    public String pathToAuthorNameMapping(String name) {
        return path(pathToNamesAuthorIdMap(), mAuthorsMap.getPathToAuthorNameMapping(name));
    }

    public String pathToNamesAuthorIdMap() {
        return path(pathToUsers(), AUTHOR_NAMES);
    }

    public String pathToReview(String reviewId) {
        return path(pathToReviews(), mReviews.getReviewPath(reviewId));
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
        return PathMaker.path(root, elements);
    }
}
