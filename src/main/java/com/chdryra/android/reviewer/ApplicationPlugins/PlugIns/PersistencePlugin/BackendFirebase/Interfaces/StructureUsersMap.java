/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.PathMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.User;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StructureUsersMap extends DbUpdater<User> {
    void setPathMaker(PathMaker<User> pathMaker);

    String getPathToUserAuthorMapping(String userId);

    @NonNull
    @Override
    Map<String, Object> getUpdatesMap(User user, UpdateType updateType);
}
