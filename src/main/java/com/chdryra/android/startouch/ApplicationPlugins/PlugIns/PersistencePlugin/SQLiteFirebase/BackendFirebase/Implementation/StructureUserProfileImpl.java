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
        .BackendFirebase.Interfaces.StructureUserProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.Path;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserProfileImpl extends DbStructureBasic<User> implements
        StructureUserProfile {
    public StructureUserProfileImpl(Path<User> path) {
        setPathToStructure(path);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        Updates updates = new Updates(updateType);
        updates.atPath(user).putObject(user.getUpdatedUser().getProfile());

        return updates.toMap();
    }
}
