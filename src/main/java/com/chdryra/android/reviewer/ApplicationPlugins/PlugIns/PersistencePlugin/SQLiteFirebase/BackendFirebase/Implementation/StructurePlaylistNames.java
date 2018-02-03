/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.StructurePlaylist;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructurePlaylistNames extends DbStructureBasic<ReviewId> implements StructurePlaylist {
    public StructurePlaylistNames(String path) {
        setPathToStructure(path);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewId reviewId, UpdateType updateType) {
        Updates updates = new Updates(updateType);
        updates.atPath(reviewId).putValue(true);

        return updates.toMap();
    }
}
