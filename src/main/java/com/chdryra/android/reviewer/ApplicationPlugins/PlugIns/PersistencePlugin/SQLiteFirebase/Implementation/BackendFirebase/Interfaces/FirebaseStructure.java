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
public interface FirebaseStructure extends FbUsersStructure, FbReviews {
    String PROVIDER_IDS_TO_AUTHOR_IDS = "ProviderIds_AuthorIds";
    String AUTHOR_IDS_TO_PROVIDER_IDS = "AuthorIds_ProviderIds";
    String AUTHOR_NAMES_TO_AUTHOR_IDS = "AuthorNames_AuthorIds";
    String AUTHOR_IDS_TO_AUTHOR_NAMES = "AuthorIds_AuthorNames";
    String USERS = "Users";
    String PROFILE = "Profile";
    String AUTHOR_DATA = "AuthorData";
    String REVIEWS = "Reviews";
    String REVIEWS_DATA = "ReviewsData";
    String REVIEWS_LIST = "ReviewsList";
    String AGGREGATES = "Aggregates";
}
