/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.StructureUsersMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUsersMapImpl extends DbUpdaterBasic<User> implements StructureUsersMap {

    @Override
    public String getPathToUserAuthorMapping(String userId) {
        return userId;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;

        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(user), update ? user.getAuthorId() : null);

        return updates;
    }
}
