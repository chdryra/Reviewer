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
 * On: 05/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class Path<T> {
    public abstract String getPath(T item);

    public static String path(String root, String...elements) {
        boolean hasRoot = root.length() > 0;
        boolean hasElements = elements.length > 0;
        String path = hasRoot ? root : hasElements ? elements[0] : "";

        int elementStart = hasRoot ? 0 : hasElements ? 1 : elements.length;
        for(int i = elementStart; i < elements.length; ++i) {
            path += "/" + elements[elementStart];
        }

        return path;
    }
}
