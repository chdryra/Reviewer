/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureAuthorsNamesMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureAuthorsUsersMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesAuthorsMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureUsersAuthorsMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.StructureBuilder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbStructUsersLed implements FirebaseStructure {
    private StructureUserProfile mUserProfile;
    private StructureAuthorsUsersMap mAuthorsUsersMap;
    private StructureUsersAuthorsMap mUsersAuthorsMap;
    private StructureNamesAuthorsMap mNamesAuthorsMap;
    private StructureAuthorsNamesMap mAuthorsNamesMap;
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

    private static String path(String root, String... elements) {
        return Path.path(root, elements);
    }

    @Override
    public FbAuthorsReviews getAuthorsDb(AuthorId authorId) {
        return new FbAuthorsReviewsDb(authorId, this);
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
    public Firebase getListEntriesDb(Firebase root, AuthorId author) {
        return root.child(pathToReviewsList(author));
    }

    @Override
    public Firebase getListEntryDb(Firebase root, AuthorId authorId, ReviewId reviewId) {
        return root.child(pathToReview(authorId, reviewId));
    }

    @Override
    public Firebase getAggregatesDb(Firebase root, AuthorId authorId) {
        return root.child(pathToAggregates(authorId));
    }

    @Override
    public Firebase getListEntriesDb(Firebase root) {
        return root.child(pathToReviewsList());
    }

    @Override
    public Firebase getListEntryDb(Firebase root, ReviewId reviewId) {
        return root.child(pathToListEntry(reviewId));
    }

    @Override
    public Firebase getAggregatesDb(Firebase root, AuthorId authorId, ReviewId reviewId) {
        return root.child(pathToAggregates(authorId, reviewId));
    }

    @Override
    public Firebase getProfileDb(Firebase root, AuthorId authorId) {
        return root.child(pathToProfile(authorId.toString()));
    }

    @Override
    public Firebase getAuthorNameMappingDb(Firebase root, AuthorId id) {
        return root.child(pathToAuthorNameMapping(id.toString()));
    }

    @Override
    public Firebase getNameAuthorMappingDb(Firebase root, String name) {
        return root.child(pathToNameAuthorMapping(name));
    }

    @Override
    public Firebase getReviewDb(Firebase root, AuthorId authorId, ReviewId reviewId) {
        return root.child(pathToReview(authorId, reviewId));
    }

    //************Private**********//
    private void initialiseReviewsDb() {
        Path<ReviewDb> pathToReviews = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToReviews(toAuthorId(review));
            }
        };
        Path<ReviewDb> pathToList = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToReviewsList(toAuthorId(review));
            }
        };
        Path<ReviewDb> pathToAggregates = new Path<ReviewDb>() {
            @Override
            public String getPath(ReviewDb review) {
                return pathToAggregates(toAuthorId(review));
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
                if (authorId == null) throw new IllegalArgumentException("User must have authorId");
                return pathToProfile(authorId);
            }
        };

        mUserProfile = new StructureUserProfileImpl(pathToProfile);
        mUsersAuthorsMap = new StructureUsersAuthorsMapImpl(pathToUserAuthorMap());
        mAuthorsUsersMap = new StructureAuthorsUsersMapImpl(pathToAuthorUserMap());
        mNamesAuthorsMap = new StructureNamesAuthorsMapImpl(pathToNamesAuthorMap());
        mAuthorsNamesMap = new StructureAuthorsNamesMapImpl(pathToAuthorNamesMap());

        StructureBuilder<User> builderUser = new StructureBuilder<>();
        mUserUpdater = builderUser.add(mUserProfile).add(mUsersAuthorsMap).add(mAuthorsUsersMap)
                .add(mNamesAuthorsMap).add(mAuthorsNamesMap).build();
    }

    private AuthorId toAuthorId(ReviewDb review) {
        return new AuthorIdParcelable(review.getAuthorId());
    }

    private String pathToNameAuthorMapping(String name) {
        return path(pathToNamesAuthorMap(), mNamesAuthorsMap.relativePathToName(name));
    }

    private String pathToAuthorNameMapping(String authorId) {
        return path(pathToAuthorNamesMap(), mAuthorsNamesMap.relativePathToAuthor(authorId));
    }

    private String pathToReview(AuthorId authorId, ReviewId reviewId) {
        return path(pathToReviews(authorId), mReviews.relativePathToReview(reviewId.toString()));
    }

    private String pathToReviewsList() {
        return REVIEWS_LIST;
    }

    private String pathToAggregates() {
        return AGGREGATES;
    }

    private String pathToListEntry(ReviewId reviewId) {
        return path(pathToReviewsList(), mAllReviewsList.relativePathToReview(reviewId.toString()));
    }

    private String pathToUserAuthorMapping(String userId) {
        return path(pathToUserAuthorMap(), mUsersAuthorsMap.relativePathToUser(userId));
    }

    private String pathToProfile(String authorId) {
        return path(pathToAuthor(authorId), PROFILE);
    }

    private String pathToUserAuthorMap() {
        return PROVIDER_IDS_TO_AUTHOR_IDS;
    }

    private String pathToAuthorUserMap() {
        return AUTHOR_IDS_TO_PROVIDER_IDS;
    }

    private String pathToAuthor(String authorId) {
        return path(pathToUsers(), AUTHOR_DATA, authorId);
    }

    private String pathToNamesAuthorMap() {
        return path(pathToUsers(), AUTHOR_NAMES_TO_AUTHOR_IDS);
    }

    private String pathToAuthorNamesMap() {
        return path(pathToUsers(), AUTHOR_IDS_TO_AUTHOR_NAMES);
    }

    private String pathToReviews(AuthorId authorId) {
        return path(pathToAuthor(authorId.toString()), REVIEWS);
    }

    private String pathToReviewsList(AuthorId authorId) {
        return path(pathToAuthor(authorId.toString()), REVIEWS_LIST);
    }

    private String pathToAggregates(AuthorId authorId) {
        return path(pathToAuthor(authorId.toString()), AGGREGATES);
    }

    private String pathToAggregates(AuthorId authorId, ReviewId reviewId) {
        return path(pathToAuthor(authorId.toString()), AGGREGATES,
                mAggregates.relativePathToReview(reviewId.toString()));
    }

    private String pathToUsers() {
        return USERS;
    }
}
