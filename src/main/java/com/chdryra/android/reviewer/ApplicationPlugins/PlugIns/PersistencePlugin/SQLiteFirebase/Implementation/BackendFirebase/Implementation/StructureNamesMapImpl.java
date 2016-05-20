/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesMapImpl extends DbStructureBasic<User> implements StructureNamesMap {
    public StructureNamesMapImpl() {
    }

    public StructureNamesMapImpl(String path) {
        setPathToStructure(path);
    }

    @Override
    public String relativePathToAuthor(String name) {
        return name;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        Profile profile = user.getProfile();
        if(profile == null) return noUpdates();

        String name = profile.getAuthor().getName();
        String authorId = user.getAuthorId();

        Updates updates = new Updates(updateType);
        updates.atPath(user, relativePathToAuthor(name)).putValue(authorId);

        return updates.toMap();
    }
}
