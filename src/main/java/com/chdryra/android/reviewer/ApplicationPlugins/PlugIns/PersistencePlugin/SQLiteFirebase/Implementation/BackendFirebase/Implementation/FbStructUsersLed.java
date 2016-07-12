/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReview;
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
    private StructureUserProfile mUserProfile;
    private StructureUsersMap mUsersMap;
    private StructureNamesMap mAuthorsMap;
    private StructureReview mReviews;
    private StructureReview mList;
    private StructureReview mAggregates;
    private StructureReview mAllReviewsList;
    private StructureReview mAllReviewsAggregates;

    private DbUpdater<User> mUserUpdater;
    private DbUpdater<ReviewDb> mReviewUploadUpdater;

    public FbStructUsersLed() {
        initialiseUserDb();
        initialiseReviewsDb();
    }

    @Override
    public FbAuthorsReviews getAuthorsDb(Author author) {
        return new FbAuthorsDb(author, this);
    }

    private void initialiseReviewsDb() {
        Path<ReviewDb> pathToReviews = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToReviews(review.getAuthor());
            }
        };
        Path<ReviewDb> pathToList = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToReviewsList(review.getAuthor());
            }
        };
        Path<ReviewDb> pathToAggregates = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToAggregates(review.getAuthor());
            }
        };
        mReviews = new StructureReviewData.Reviews(pathToReviews);
        mList = new StructureReviewData.List(pathToList);
        mAggregates = new StructureReviewData.Aggregates(pathToAggregates);

        mAllReviewsList = new StructureReviewData.List(pathToReviewsList());
        mAllReviewsAggregates = new StructureReviewData.Aggregates(pathToAggregates());

        StructureBuilder<ReviewDb> builderReview = new StructureBuilder<>();
        mReviewUploadUpdater = builderReview.add(mReviews).add(mList).add(mAggregates).
                add(mAllReviewsList).add(mAllReviewsAggregates).build();
    }

    private void initialiseUserDb() {
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
    public Firebase getListEntriesDb(Firebase root, Author author) {
        return root.child(pathToReviewsList(author));
    }

    @Override
    public Firebase getListEntryDb(Firebase root, Author author, String reviewId) {
        return root.child(pathToReview(author, reviewId));
    }

    @Override
    public Firebase getAggregatesDb(Firebase root, Author author) {
        return root.child(pathToAggregates(author));
    }

    @Override
    public Firebase getListEntriesDb(Firebase root) {
        return root.child(pathToReviewsList());
    }

    @Override
    public Firebase getListEntryDb(Firebase root, String reviewId) {
        return root.child(pathToListEntry(reviewId));
    }
//
//    @Override
//    public Firebase getAggregatesDb(Firebase root) {
//        return root.child(pathToAggregates());
//    }
//
//    @Override
//    public Firebase getAggregatesDb(Firebase root, String reviewId) {
//        return root.child(pathToAggregates(reviewId));
//    }
//
    @Override
    public Firebase getAggregatesDb(Firebase root, Author author, String reviewId) {
        return root.child(pathToAggregates(author, reviewId));
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

    private String pathToAggregates() {
        return AGGREGATES;
    }

    private String pathToAggregates(String reviewId) {
        return path(pathToAggregates(), mAllReviewsAggregates.relativePathToReview(reviewId));
    }

    private String pathToListEntry(String reviewId) {
        return path(pathToReviewsList(), mAllReviewsList.relativePathToReview(reviewId));
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

    private String pathToReviewsList(Author author) {
        return path(pathToAuthor(author.getAuthorId()), REVIEWS_LIST);
    }

    private String pathToAggregates(Author author) {
        return path(pathToAuthor(author.getAuthorId()), AGGREGATES);
    }

    private String pathToAggregates(Author author, String reviewId) {
        return path(pathToAuthor(author.getAuthorId()), AGGREGATES, mAggregates.relativePathToReview(reviewId));
    }

    private String pathToUsers() {
        return USERS;
    }

    private static String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
