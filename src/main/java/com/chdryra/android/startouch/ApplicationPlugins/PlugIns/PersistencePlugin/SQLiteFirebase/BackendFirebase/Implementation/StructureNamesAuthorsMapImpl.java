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
        .Backend.Implementation.ProfileAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureNamesAuthorsMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbStructureBasic;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesAuthorsMapImpl extends DbStructureBasic<User> implements
        StructureNamesAuthorsMap {
    public StructureNamesAuthorsMapImpl(String path) {
        setPathToStructure(path);
    }

    @Override
    public String relativePathToName(String name) {
        return name;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        ProfileAuthor profile = user.getProfile();
        if (profile == null) return noUpdates();

        String name = profile.getAuthor().getName();
        String authorId = user.getAuthorId();

        Updates updates = new Updates(updateType);
        updates.atPath(user, relativePathToName(name)).putValue(authorId);

        return updates.toMap();
    }
}
