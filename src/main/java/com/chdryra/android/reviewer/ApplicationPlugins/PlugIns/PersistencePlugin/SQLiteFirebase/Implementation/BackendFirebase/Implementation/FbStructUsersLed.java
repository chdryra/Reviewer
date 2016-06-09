/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviewsList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUsersMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.StructureBuilder;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbStructUsersLed implements FirebaseStructure {
    private final StructureUserProfile mUserProfile;
    private final StructureUsersMap mUsersMap;
    private final StructureNamesMap mAuthorsMap;
    private final StructureReviews mReviews;
    private final StructureReviewsList mReviewsList;

    private final DbUpdater<User> mUserUpdater;
    private final DbUpdater<ReviewDb> mReviewUploadUpdater;

    public FbStructUsersLed() {
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

        Path<ReviewDb> pathToReview = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToReviews(review.getAuthor());
            }
        };
        mReviews = new StructureReviewsImpl(pathToReview);
        mReviewsList = new StructureReviewsListImpl(pathToReviewsList());

        StructureBuilder<ReviewDb> builderReview = new StructureBuilder<>();
        mReviewUploadUpdater = builderReview.add(mReviews).add(mReviewsList).build();
    }

    @Override
    public DbUpdater<User> getProfileUpdater() {
        return mUserProfile;
    }

    @Override
    public DbUpdater<User> getUsersUpdater() {
        return mUserUpdater;
    }

    @Override
    public DbUpdater<ReviewDb> getReviewUploadUpdater() {
        return mReviewUploadUpdater;
    }

    @Override
    public Firebase getUserAuthorMappingDb(Firebase root, String userId) {
        return root.child(pathToUserAuthorMapping(userId));
    }

    @Override
    public Firebase getReviewsDb(Firebase root, Author author) {
        return root.child(pathToReviews(author));
    }

    @Override
    public Firebase getListEntriesDb(Firebase root, @Nullable Author author) {
        return author == null ? root.child(pathToReviewsList())
                : root.child(pathToAuthorReviews(author));
    }

    @Override
    public Firebase getListEntryDb(Firebase root, String reviewId) {
        return root.child(pathToListEntry(reviewId));
    }

    @Override
    public Firebase getProfileDb(Firebase root, String authorId) {
        return root.child(pathToProfile(authorId));
    }

    @Override
    public Firebase getAuthorNameMappingDb(Firebase root, String name) {
        return root.child(pathToAuthorNameMapping(name));
    }

    @Override
    public Firebase getReviewDb(Firebase root, Author author, String reviewId) {
        return root.child(pathToReview(author, reviewId));
    }

    //************Private**********//
    private String pathToAuthorNameMapping(String name) {
        return path(pathToNamesAuthorIdMap(), mAuthorsMap.relativePathToAuthor(name));
    }

    private String pathToReview(Author author, String reviewId) {
        return path(pathToReviews(author), mReviews.relativePathToReview(reviewId));
    }

    private String pathToAuthorReviews(Author author) {
        return path(pathToAuthor(author.getAuthorId()), REVIEWS);
    }

    private String pathToReviewsList() {
        return REVIEWS_LIST;
    }

    private String pathToListEntry(String reviewId) {
        return path(pathToReviewsList(), mReviewsList.relativePathToEntry(reviewId));
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
        return path(pathToUsers(), AUTHOR_DATA, authorId);
    }

    private String pathToNamesAuthorIdMap() {
        return path(pathToUsers(), AUTHOR_NAMES);
    }

    private String pathToReviews(Author author) {
        return path(pathToAuthor(author.getAuthorId()), REVIEWS);
    }

    private String pathToUsers() {
        return USERS;
    }

    private static String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
