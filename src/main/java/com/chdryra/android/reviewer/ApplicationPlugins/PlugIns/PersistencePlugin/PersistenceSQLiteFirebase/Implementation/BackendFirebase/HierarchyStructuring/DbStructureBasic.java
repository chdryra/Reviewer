/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring;



import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbStructureBasic<T> implements DbStructure<T> {
    private Path<T> mPath;

    @Override
    public void setPathToStructure(Path<T> path) {
        mPath = path;
    }

    protected String path(String root, String...elements) {
        return Path.path(root, elements);
    }

    protected String absolutePath(T item) {
        return getPath(item);
    }

    protected String absolutePath(T item, String element, String...elements) {
        String[] root = {element};
        String[] relative = ArrayUtils.addAll(root, elements);
        return path(getPath(item), relative);
    }

    private String getPath(T item) {
        return mPath != null ? mPath.getPath(item) : "";
    }
}
