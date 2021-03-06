/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureAuthorsUsersMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbStructureBasic;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureAuthorsUsersMapImpl extends DbStructureBasic<User> implements
        StructureAuthorsUsersMap {
    public StructureAuthorsUsersMapImpl(String path) {
        setPathToStructure(path);
    }

    @Override
    public String relativePathToAuthor(String authorId) {
        return authorId;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        String userId = user.getProviderUserId();
        String authorId = user.getAuthorId();

        Updates updates = new Updates(updateType);
        if (authorId != null) updates.atPath(user, relativePathToAuthor(authorId)).putValue(userId);

        return updates.toMap();
    }
}
