/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureBuilder<T> {
    private ArrayList<DbStructure<T>> mStructures;

    public StructureBuilder() {
        mStructures = new ArrayList<>();
    }

    public StructureBuilder<T> add(DbStructure<T> structure) {
        mStructures.add(structure);
        return this;
    }

    public StructureBuilder<T> add(String path, DbStructure<T> structure) {
        mStructures.add(new PathedStructure<>(path, structure));
        return this;
    }

    public StructureBuilder<T> add(Collection<DbStructure<T>> structures) {
        mStructures.addAll(structures);
        return this;
    }

    public StructureBuilder<T> add(String path, Collection<DbStructure<T>> structures) {
        for(DbStructure<T> structure : structures) {
            add(path, structure);
        }

        return this;
    }

    public DbStructure<T> build() {
        CompositeStructure.Builder<T> builder = new CompositeStructure.Builder<>();
        return builder.add(mStructures).build();
    }
}
