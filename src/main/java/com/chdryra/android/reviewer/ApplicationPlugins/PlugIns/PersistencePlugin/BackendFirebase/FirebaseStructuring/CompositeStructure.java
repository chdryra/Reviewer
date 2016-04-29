/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring;



import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CompositeStructure<T> extends DbStructureBasic<T> {
    private Iterable<DbStructure<T>> mStructures;

    private CompositeStructure(Iterable<DbStructure<T>> structures) {
        mStructures = structures;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(T item, UpdateType updateType) {
        Map<String, Object> updates = new HashMap<>();
        for(DbStructure<T> structure : mStructures) {
            updates.putAll(structure.getUpdatesMap(item, updateType));
        }

        return updates;
    }

    public static class Builder<T> {
        private ArrayList<DbStructure<T>> mStructures;

        public Builder() {
            mStructures = new ArrayList<>();
        }

        public Builder<T> add(DbStructure<T> structure) {
            mStructures.add(structure);
            return this;
        }

        public Builder<T> add(Collection<DbStructure<T>> structures) {
            mStructures.addAll(structures);
            return this;
        }

        public DbStructure<T> build() {
            return new CompositeStructure<>(mStructures);
        }
    }
}
