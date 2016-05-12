/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbStructureBasic<T> implements DbStructure<T> {
    private Path<T> mPath;

    protected String path(String root, String... elements) {
        return Path.path(root, elements);
    }

    protected Map<String, Object> noUpdates() {
        return new HashMap<>();
    }

    @Override
    public void setPathToStructure(Path<T> path) {
        mPath = path;
    }

    private String path(T item) {
        return getPath(item);
    }

    private String path(T item, String element, String... elements) {
        String[] root = {element};
        String[] relative = ArrayUtils.addAll(root, elements);
        return path(getPath(item), relative);
    }

    private String getPath(T item) {
        return mPath != null ? mPath.getPath(item) : "";
    }

    protected class Updates {
        private Map<String, Object> mUpdatesMap;
        private ArrayList<Update<T>> mUpdates;
        private boolean mDelete;

        public Updates(UpdateType type) {
            mDelete = type == UpdateType.DELETE;
            initialise();
        }

        private void initialise() {
            mUpdatesMap = new HashMap<>();
            mUpdates = new ArrayList<>();
        }

        public Update<T> atPath(T itemPathToStructure, String root, String... elements) {
            Update<T> update = new Update<>(itemPathToStructure, root, elements);
            mUpdates.add(update);
            return update;
        }

        public Update<T> atPath(T itemPathToStructure) {
            Update<T> update = new Update<>(itemPathToStructure);
            mUpdates.add(update);
            return update;
        }

        public Map<String, Object> toMap() {
            for (Update<T> value : mUpdates) {
                value.commit();
            }

            try {
                return mUpdatesMap;
            } finally {
                initialise();
            }
        }

        public class Update<P extends T> {
            private String mPath;
            private Object mValue;

            private Update(P itemPath, String root, String... elements) {
                mPath = path(itemPath, root, elements);
            }

            private Update(P itemPath) {
                mPath = path(itemPath);
            }

            public void putValue(@Nullable Object value) {
                mValue = value;
            }

            public void putObject(@Nullable Object value) {
                mValue = value != null ? new ObjectMapper().convertValue(value, Map.class) : null;
            }

            private void commit() {
                mUpdatesMap.put(mPath, mDelete ? null : mValue);
            }
        }
    }
}
