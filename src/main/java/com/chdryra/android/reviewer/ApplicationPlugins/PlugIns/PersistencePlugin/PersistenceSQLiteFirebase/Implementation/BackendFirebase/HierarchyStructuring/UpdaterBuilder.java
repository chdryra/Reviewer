/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UpdaterBuilder<T> {
    private ArrayList<DbUpdater<T>> mStructures;

    public UpdaterBuilder() {
        mStructures = new ArrayList<>();
    }

    public UpdaterBuilder<T> add(DbUpdater<T> structure) {
        mStructures.add(structure);
        return this;
    }

    public UpdaterBuilder<T> add(Collection<DbUpdater<T>> structures) {
        mStructures.addAll(structures);
        return this;
    }

    public DbUpdater<T> build() {
        CompositeUpdater.Builder<T> builder = new CompositeUpdater.Builder<>();
        return builder.add(mStructures).build();
    }
}
