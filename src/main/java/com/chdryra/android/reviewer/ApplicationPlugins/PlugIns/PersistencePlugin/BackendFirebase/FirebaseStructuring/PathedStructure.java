/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring;


import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PathedStructure<T> extends DbStructureBasic<T> {
    private String mPath;
    private DbStructure<T> mUpdatesStructure;

    public PathedStructure(String path, DbStructure<T> updatesStructure) {
        mPath = path;
        mUpdatesStructure = updatesStructure;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(T item, UpdateType updateType) {
        Map<String, Object> updates = new HashMap<>();
        Map<String, Object> relativeMap = mUpdatesStructure.getUpdatesMap(item, updateType);
        for(Map.Entry<String, Object> entry : relativeMap.entrySet()) {
            updates.put(path(mPath, entry.getKey()), entry.getValue());
        }

        return updates;
    }
}
