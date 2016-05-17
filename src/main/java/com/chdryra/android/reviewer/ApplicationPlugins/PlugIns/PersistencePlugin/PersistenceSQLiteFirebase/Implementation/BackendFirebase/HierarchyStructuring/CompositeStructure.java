/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring;



import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CompositeStructure<T> extends DbStructureBasic<T> {
    private Iterable<DbUpdater<T>> mStructures;

    private CompositeStructure(Iterable<DbUpdater<T>> structures) {
        mStructures = structures;
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

    public static class Builder<T> {
        private ArrayList<DbUpdater<T>> mStructures;

        public Builder() {
            mStructures = new ArrayList<>();
        }

        public Builder<T> add(DbUpdater<T> structure) {
            mStructures.add(structure);
            return this;
        }

        public Builder<T> add(Collection<DbUpdater<T>> structures) {
            mStructures.addAll(structures);
            return this;
        }

        public DbStructure<T> build() {
            return new CompositeStructure<>(mStructures);
        }
    }
}
