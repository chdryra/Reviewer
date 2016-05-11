/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbUpdaterBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureNamesMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureNamesMapImpl extends DbUpdaterBasic<User> implements StructureNamesMap {


    @Override
    public String getPathToAuthorNameMapping(String name) {
        return name;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;

        Map<String, Object> updates = new HashMap<>();

        Profile profile = user.getProfile();
        if(profile == null) return updates;

        String name = profile.getAuthor().getName();
        updates.put(path(getPath(user), name), update ? user.getAuthorId() : null);

        return updates;
    }
}
