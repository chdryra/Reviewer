/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureBuilder<T> {
    private ArrayList<DbStructure<T>> mStructures;
    private Path<T> mPath;

    public StructureBuilder() {
        mStructures = new ArrayList<>();
    }

    public StructureBuilder<T> add(final String path, DbStructure<T> structure) {
        structure.setPathToStructure(new Path<T>() {
            @Override
            public String getPath(T item) {
                return path;
            }
        });

        mStructures.add(structure);
        return this;
    }

    public StructureBuilder<T> add(DbStructure<T> structure) {
        mStructures.add(structure);
        return this;
    }

    public StructureBuilder<T> add(Collection<DbStructure<T>> structures) {
        mStructures.addAll(structures);
        return this;
    }

    public StructureBuilder<T> setPath(Path<T> path) {
        mPath = path;
        return this;
    }

    public StructureBuilder<T> setPath(final String path) {
        mPath = new Path<T>() {
            @Override
            public String getPath(T item) {
                return path;
            }
        };

        return this;
    }

    public DbStructure<T> build() {
        CompositeStructure<T> composite = new CompositeStructure<>(mStructures);
        if (mPath != null) composite.setPathToStructure(mPath);
        return composite;
    }
}
