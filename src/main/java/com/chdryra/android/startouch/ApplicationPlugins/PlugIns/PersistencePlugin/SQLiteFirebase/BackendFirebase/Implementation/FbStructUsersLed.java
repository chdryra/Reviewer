/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Follow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureAuthorsNamesMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureAuthorsUsersMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureNamesAuthorsMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructurePlaylist;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureUsersAuthorsMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.Path;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.StructureBuilder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbStructUsersLed implements FirebaseStructure {
    private StructureUsersAuthorsMap mUsersAuthorsMap;
    private StructureNamesAuthorsMap mNamesAuthorsMap;
    private StructureAuthorsNamesMap mAuthorsNamesMap;
    private StructureReview mReviews;
    private StructureReviewData.List mReviewsList;
    private StructureReview mAggregates;
    private StructureReview mAllReviewsList;

    private DbUpdater<User> mProfileUpdater;
    private DbUpdater<User> mUserUpdater;
    private DbUpdater<ReviewDb> mReviewUpdater;
    private DbStructure<Follow> mSocialUpdater;

    public FbStructUsersLed() {
        initialiseUserDb();
        initialiseReviewsDb();
        initialiseSocialDb();
    }

    private static String path(String root, String... elements) {
        return Path.path(root, elements);
    }

    public String pathToCollectionNamesIndex(String name, AuthorId authorId) {
        return path(pathToCollectionNames(authorId), name);
    }

    public String pathToCollectionEntry(String name, AuthorId authorId, ReviewId reviewId) {
        return path(pathToCollection(name, authorId), StructureCollectionImpl.relativePathToReview
                (reviewId.toString()));
    }

    @Override
    public FbAuthorsDb getAuthorsDb(AuthorId authorId) {
        return new FbAuthorsReviewsDb(authorId, this);
    }

    @Override
    public DbUpdater<User> getProfileUpdater() {
        return mProfileUpdater;
    }

    @Override
    public DbUpdater<User> getUsersUpdater() {
        return mUserUpdater;
    }

    @Override
    public DbUpdater<Follow> getSocialUpdater() {
        return mSocialUpdater;
    }

    @Override
    public DbUpdater<ReviewDb> getReviewUpdater() {
        return mReviewUpdater;
    }

    @Override
    public DbUpdater<ReviewId> getCollectionUpdater(final String name, final AuthorId authorId) {
        StructurePlaylist collection = new StructureCollectionImpl(pathToCollection(name,
                authorId));
        StructureCollectionNames names = new StructureCollectionNames(pathToCollectionNamesIndex
                (name,
                        authorId));
        StructureBuilder<ReviewId> builderCollection = new StructureBuilder<>();
        return builderCollection.add(collection).add(names).build();
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
    public Firebase getCollectionDb(Firebase root, AuthorId authorId, String playlistName) {
        return root.child(pathToCollection(playlistName, authorId));
    }

    @Override
    public Firebase getCollectionEntryDb(Firebase root, AuthorId authorId, String collectionName,
                                         ReviewId reviewId) {
        return root.child(pathToCollectionEntry(collectionName, authorId, reviewId));
    }

    @Override
    public Firebase getListEntryDb(Firebase root, AuthorId authorId, ReviewId reviewId) {
        return root.child(pathToListEntry(authorId, reviewId));
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
    public Firebase getFollowingDb(Firebase root, AuthorId authorId) {
        return getSocialDb(root, authorId).child(FOLLOWING);
    }

    @Override
    public Firebase getFollowersDb(Firebase root, AuthorId authorId) {
        return getSocialDb(root, authorId).child(FOLLOWERS);
    }

    @Override
    public Firebase getAuthorNameMappingDb(Firebase root, AuthorId authorId) {
        return root.child(pathToAuthorNameMapping(authorId.toString()));
    }

    @Override
    public Firebase getNameAuthorMappingDb(Firebase root, String name) {
        return root.child(pathToNameAuthorMapping(name));
    }

    @Override
    public Firebase getNameAuthorMapDb(Firebase root) {
        return root.child(pathToNamesAuthorMap());
    }

    @Override
    public Firebase getReviewDb(Firebase root, AuthorId authorId, ReviewId reviewId) {
        return root.child(pathToReview(authorId, reviewId));
    }

    @Override
    public Firebase getSocialDb(Firebase root, AuthorId authorId) {
        return root.child(pathToSocial(authorId.toString()));
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
        mReviewsList = new StructureReviewData.List(pathToList);
        mAggregates = new StructureReviewData.Aggregates(pathToAggregates);

        mAllReviewsList = new StructureReviewData.List(pathToReviewsList());
        StructureReview allReviewsAggregates = new StructureReviewData.Aggregates
                (pathToAggregates());

        StructureBuilder<ReviewDb> builderReview = new StructureBuilder<>();
        mReviewUpdater = builderReview.add(mReviews).add(mReviewsList).add(mAggregates).
                add(mAllReviewsList).add(allReviewsAggregates).build();
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

        mUsersAuthorsMap = new StructureUsersAuthorsMapImpl(pathToUserAuthorMap());
        StructureAuthorsUsersMap authorsUsersMap = new StructureAuthorsUsersMapImpl
                (pathToAuthorUserMap());
        mNamesAuthorsMap = new StructureNamesAuthorsMapImpl(pathToNamesAuthorMap());
        mAuthorsNamesMap = new StructureAuthorsNamesMapImpl(pathToAuthorNamesMap());

        StructureUserProfileImpl profile = new StructureUserProfileImpl(pathToProfile);
        StructureBuilder<User> builderUser = new StructureBuilder<>();
        mUserUpdater = builderUser.add(profile).add(mUsersAuthorsMap).add(authorsUsersMap)
                .add(mNamesAuthorsMap).add(mAuthorsNamesMap).build();
        StructureBuilder<User> builderProfile = new StructureBuilder<>();

        DbStructure<User> profileUpdate = new UserStructureUpdater(profile);
        DbStructure<User> nameAuthorsUpdate = new UserStructureUpdater(mNamesAuthorsMap);
        DbStructure<User> authorsNamesUpdate = new UserStructureUpdater(mAuthorsNamesMap);
        mProfileUpdater = builderProfile.add(profileUpdate).add(nameAuthorsUpdate).add
                (authorsNamesUpdate).build();
    }

    private void initialiseSocialDb() {
        Path<Follow> pathToFollowing = new Path<Follow>() {
            @Override
            public String getPath(Follow follow) {
                return pathToFollowing(follow.getFollower().toString());
            }
        };

        Path<Follow> pathToFollowers = new Path<Follow>() {
            @Override
            public String getPath(Follow follow) {
                return pathToFollowers(follow.getFollowing().toString());
            }
        };

        StructureFollowing following = new StructureFollowing(pathToFollowing);
        StructureFollowers followers = new StructureFollowers(pathToFollowers);

        StructureBuilder<Follow> builderUser = new StructureBuilder<>();
        mSocialUpdater = builderUser.add(following).add(followers).build();
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

    private String pathToListEntry(AuthorId authorId, ReviewId reviewId) {
        return path(pathToReviewsList(authorId), mReviewsList.relativePathToReview(reviewId
                .toString()));
    }

    private String pathToUserAuthorMapping(String userId) {
        return path(pathToUserAuthorMap(), mUsersAuthorsMap.relativePathToUser(userId));
    }

    private String pathToProfile(String authorId) {
        return path(pathToAuthor(authorId), PROFILE);
    }

    private String pathToSocial(String authorId) {
        return path(pathToAuthor(authorId), SOCIAL);
    }

    private String pathToFollowing(String authorId) {
        return path(pathToSocial(authorId), FOLLOWING);
    }

    private String pathToFollowers(String authorId) {
        return path(pathToSocial(authorId), FOLLOWERS);
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

    private String pathToCollection(String name, AuthorId authorId) {
        return path(pathToAuthor(authorId.toString()), PLAYLISTS, name);
    }

    private String pathToCollectionNames(AuthorId authorId) {
        return path(pathToAuthor(authorId.toString()), PLAYLISTS, PLAYLIST_INDEX);
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
