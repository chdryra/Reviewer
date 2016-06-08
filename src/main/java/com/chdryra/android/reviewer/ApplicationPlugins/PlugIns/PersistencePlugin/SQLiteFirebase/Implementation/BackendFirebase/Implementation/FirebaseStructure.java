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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseStructure {
    //public for testing
    String REVIEWS = "Reviews";
    String REVIEWS_DATA = "Data";
    String REVIEWS_LIST = "List";
    String TAGS = "Tags";
    String USERS = "Users";
    String AUTHOR_DATA = "AuthorData";
    String AUTHOR_NAMES = "AuthorNames";
    String PROFILE = "Profile";
    String USERS_MAP = "ProviderUsersMap";
    String FEED = "Feed";

    DbUpdater<User> getProfileUpdater();

    DbUpdater<User> getUsersUpdater();

    DbUpdater<ReviewDb> getReviewUploadUpdater();

    Firebase getUserAuthorMappingDb(Firebase root, String userId);

    Firebase getReviewsDb(Firebase root, Author author);

    Firebase getReviewsListDb(Firebase root, Author author);

    Firebase getProfileDb(Firebase root, String authorId);

    Firebase getAuthorNameMappingDb(Firebase root, String name);

    Firebase getReviewDb(Firebase root, Author author, String reviewId);
}
