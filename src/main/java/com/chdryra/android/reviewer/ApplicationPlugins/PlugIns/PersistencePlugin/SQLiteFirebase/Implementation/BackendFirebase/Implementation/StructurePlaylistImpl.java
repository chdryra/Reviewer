/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructurePlaylist;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbStructureBasic;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructurePlaylistImpl extends DbStructureBasic<ReviewListEntry> implements StructurePlaylist {
    public StructurePlaylistImpl(String path) {
        setPathToStructure(path);
    }

    public String relativePathToReview(String reviewId) {
        return reviewId;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewListEntry entry, UpdateType updateType) {
        Updates updates = new Updates(updateType);
        updates.atPath(entry, relativePathToReview(entry.getReviewId())).putObject(entry);

        return updates.toMap();
    }
}
