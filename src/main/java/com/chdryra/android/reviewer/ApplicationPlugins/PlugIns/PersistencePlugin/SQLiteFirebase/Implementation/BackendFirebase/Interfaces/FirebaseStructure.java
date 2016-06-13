/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces;


/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseStructure extends FbUsersStructure, FbReviewsStructure {
    String USERS_MAP = "ProviderUsersMap";
    String USERS = "Users";
    String AUTHOR_NAMES = "AuthorNames";
    String PROFILE = "Profile";
    String AUTHOR_DATA = "AuthorData";
    String REVIEWS = "Reviews";
    String REVIEWS_DATA = "ReviewsData";
    String REVIEWS_LIST = "ReviewsList";
}
