/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbStructureBasic;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUsers extends DbStructureBasic<User> {
    private final String mUsersPath;
    private final String mUsersDataPath;
    private final String mUsersMapPath;

    public StructureUsers(String usersPath, String usersDataPath, String usersMapPath) {
        mUsersPath = usersPath;
        mUsersDataPath = usersDataPath;
        mUsersMapPath = usersMapPath;
    }

    public String getUsersPath() {
        return mUsersPath;
    }

    public String getUsersMapPath(String fbUserId) {
        return path(mUsersPath, mUsersMapPath, fbUserId);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(User user, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Map<String, Object> profileMap = null;

        if(update) profileMap = new ObjectMapper().convertValue(user.getProfile(), Map.class);

        Map<String, Object> updates = new HashMap<>();
        String authorId = user.getAuthorId();
        updates.put(pathToAuthor(user.getAuthorId()), profileMap);
        updates.put(getUsersMapPath(user.getFbUserId()), update ? authorId : null);

        return updates;
    }

    public String pathToAuthor(String authorId) {
        return path(mUsersPath, mUsersDataPath, authorId);
    }
}
