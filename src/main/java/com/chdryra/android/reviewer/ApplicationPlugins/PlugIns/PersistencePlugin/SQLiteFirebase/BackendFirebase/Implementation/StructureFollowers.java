/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Follow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.StructureFollow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructureBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureFollowers extends DbStructureBasic<Follow> implements StructureFollow {
    public StructureFollowers(Path<Follow> path) {
        setPathToStructure(path);
    }


    @Override
    public String relativePathToEntry(Follow follow) {
        return follow.getFollower().toString();
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(Follow item, UpdateType updateType) {
        Updates updates = new Updates(updateType);
        updates.atPath(item, relativePathToEntry(item)).putValue(true);

        return updates.toMap();
    }
}
