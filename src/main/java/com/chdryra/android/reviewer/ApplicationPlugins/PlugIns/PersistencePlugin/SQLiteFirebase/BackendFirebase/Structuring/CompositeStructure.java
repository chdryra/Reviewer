/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring;



import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CompositeStructure<T> extends DbStructureBasic<T> {
    private final Iterable<DbStructure<T>> mStructures;

    public CompositeStructure(Iterable<DbStructure<T>> structures) {
        mStructures = structures;
        setPathToStructure("");
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(T item, UpdateType updateType) {
        Updates updates = new Updates(updateType);
        for(DbUpdater<T> structure : mStructures) {
            updates.atPath(item).putMap(structure.getUpdatesMap(item, updateType));
        }

        return updates.toMap();
    }

}
