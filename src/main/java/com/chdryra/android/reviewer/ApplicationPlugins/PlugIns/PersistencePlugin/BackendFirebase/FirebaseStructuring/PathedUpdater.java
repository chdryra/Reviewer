/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring;



import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PathedUpdater<T> extends DbUpdaterBasic<T> {
    private String mPath;
    private PathGetter<T> mGetter;
    private DbUpdater<T> mDataStructure;

    public interface PathGetter<T> {
        String getPath(T item);
    }

    public PathedUpdater(String path, DbUpdater<T> dataStructure) {
        mPath = path;
        mDataStructure = dataStructure;
    }

    public PathedUpdater(PathGetter<T> getter, DbUpdater<T> dataStructure) {
        mGetter = getter;
        mDataStructure = dataStructure;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(T item, UpdateType updateType) {
        String path = mPath != null ? mPath : mGetter.getPath(item);
        return includePathInKeys(path, mDataStructure.getUpdatesMap(item, updateType));
    }
}
