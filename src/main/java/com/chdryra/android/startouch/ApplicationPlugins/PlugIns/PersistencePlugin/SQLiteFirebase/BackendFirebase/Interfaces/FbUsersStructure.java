/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbUsersStructure extends FbProfilesStructure, FbSocialStructure {
    String PROVIDER_IDS_TO_AUTHOR_IDS = "ProviderIds_AuthorIds";
    String AUTHOR_IDS_TO_PROVIDER_IDS = "AuthorIds_ProviderIds";
    String AUTHOR_NAMES_TO_AUTHOR_IDS = "AuthorNames_AuthorIds";
    String AUTHOR_IDS_TO_AUTHOR_NAMES = "AuthorIds_AuthorNames";
    String USERS = "Users";
    String AUTHOR_DATA = "AuthorData";

    DbUpdater<User> getUsersUpdater();

    Firebase getUserAuthorMappingDb(Firebase root, String userId);

    Firebase getAuthorNameMappingDb(Firebase root, AuthorId id);

    Firebase getNameAuthorMappingDb(Firebase root, String name);

    Firebase getNameAuthorMapDb(Firebase root);
}
