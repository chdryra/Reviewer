/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructure;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StructureUsersAuthorsMap extends DbStructure<User> {
    String relativePathToUser(String userId);

    @Override
    void setPathToStructure(Path<User> path);

    @NonNull
    @Override
    Map<String, Object> getUpdatesMap(User user, UpdateType updateType);
}
