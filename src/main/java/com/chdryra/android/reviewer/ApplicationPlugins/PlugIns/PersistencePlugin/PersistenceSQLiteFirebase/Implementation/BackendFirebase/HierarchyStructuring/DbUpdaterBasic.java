/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring;



/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbUpdaterBasic<T> implements DbUpdater<T> {
    private PathMaker<T> mPathMaker;

    public void setPathMaker(PathMaker<T> pathMaker) {
        mPathMaker = pathMaker;
    }

    protected String path(String root, String...elements) {
        return PathMaker.path(root, elements);
    }


    protected String getPath(T item) {
        return mPathMaker != null ? mPathMaker.getPath(item) : "";
    }
}
