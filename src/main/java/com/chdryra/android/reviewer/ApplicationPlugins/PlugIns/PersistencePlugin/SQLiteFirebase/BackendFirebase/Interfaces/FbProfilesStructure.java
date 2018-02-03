/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbProfilesStructure {
    String PROFILE = "Profile";

    DbUpdater<User> getProfileUpdater();

    Firebase getProfileDb(Firebase root, AuthorId authorId);
}
