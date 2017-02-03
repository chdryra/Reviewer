/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserMappingUpdater extends DbStructureBasic<User> {
    private final DbUpdater<User> mStructure;

    public UserMappingUpdater(DbUpdater<User> structure) {
        mStructure = structure;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        if(updateType == UpdateType.DELETE) return noUpdates();

        User oldUser = user.getCurrentUser();
        User newUser = user.getUpdatedUser();

        Profile oldProfile = oldUser.getProfile();
        Profile newProfile = newUser.getProfile();

        if(oldProfile == null || newProfile == null || oldProfile == newProfile) return noUpdates();

        Updates updates = new Updates(updateType);
        updates.atPath(null).putMap(mStructure.getUpdatesMap(oldUser, UpdateType.DELETE));
        updates.atPath(null).putMap(mStructure.getUpdatesMap(newUser, UpdateType.INSERT_OR_UPDATE));

        return updates.toMap();
    }
}
