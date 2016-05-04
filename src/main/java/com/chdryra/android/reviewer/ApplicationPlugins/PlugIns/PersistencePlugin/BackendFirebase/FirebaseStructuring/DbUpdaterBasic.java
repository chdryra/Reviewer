/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring;



import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbUpdaterBasic<T> implements DbUpdater<T> {
    protected String path(String root, String...elements) {
        String path = root;
        for(String element : elements) {
            path += "/" + element;
        }

        return path;
    }

    protected Map<String, Object> includePathInKeys(String path, Map<String, Object> relativeMap) {
        Map<String, Object> updates = new HashMap<>();
        for(Map.Entry<String, Object> entry : relativeMap.entrySet()) {
            updates.put(path(path, entry.getKey()), entry.getValue());
        }

        return updates;
    }
}
