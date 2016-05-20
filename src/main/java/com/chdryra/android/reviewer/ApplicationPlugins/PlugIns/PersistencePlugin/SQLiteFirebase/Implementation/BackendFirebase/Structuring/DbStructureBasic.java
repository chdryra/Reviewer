/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Structuring;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbStructureBasic<T> implements DbStructure<T> {
    private Path<T> mPath;

    @NonNull
    @Override
    public abstract Map<String, Object> getUpdatesMap(T item, UpdateType updateType);

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

    @Override
    public void setPathToStructure(final String path) {
        mPath = new Path<T>() {
            @Override
            public String getPath(T item) {
                return path;
            }
        };
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
        return mPath != null ? mPath.getPath(item) : item.toString();
    }

    protected class Updates {
        private Map<String, Object> mUpdatesMap;
        private List<Update<T>> mUpdates;
        private boolean mDelete;

        public Updates(UpdateType type) {
            mDelete = type == UpdateType.DELETE;
            initialise();
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

        private void initialise() {
            mUpdatesMap = new HashMap<>();
            mUpdates = new ArrayList<>();
        }

        public class Update<P extends T> {
            private String mPath;
            private Object mValue;
            private Map<String, Object> mMap;

            private Update(P itemPath, String root, String... elements) {
                setPath(path(itemPath, root, elements));
            }

            private Update(P itemPath) {
                setPath(path(itemPath));
            }

            public void putValue(@Nullable Object value) {
                mValue = value;
            }

            public void putMap(Map<String, Object> updatesMap) {
                mMap = updatesMap;
            }

            public void putObject(@Nullable Object value) {
                mMap = value != null ? new ObjectMapper().convertValue(value, Map.class) : null;
            }

            private void setPath(String path) {
                mPath = path;
            }

            private void commit() {
                if (mMap == null) {
                    mUpdatesMap.put(mPath, mDelete ? null : mValue);
                } else {
                    Map<String, Object> absolute = new HashMap<>();
                    makeAbsolute(mPath, mMap, absolute);
                    mUpdatesMap.putAll(absolute);
                }
            }

            private void makeAbsolute(String pathStem,
                                      Map<String, Object> relativeMap,
                                      Map<String, Object> result) {
                for (Map.Entry<String, Object> entry : relativeMap.entrySet()) {
                    String newPath = path(pathStem, entry.getKey());
                    resolveValueIsNullOrMapOrAsIs(entry.getValue(), newPath, result);
                }
            }

            private void resolveValueIsNullOrMapOrAsIs(Object value, String newPath,
                                                       Map<String, Object> result) {
                if (value == null) {
                    result.put(newPath, null);
                } else {
                    try {
                        makeAbsolute(newPath, (Map<String, Object>) value, result);
                    } catch (ClassCastException e) {
                        result.put(newPath, mDelete ? null : value);
                    }
                }
            }
        }
    }
}
