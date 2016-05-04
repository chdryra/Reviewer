/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DynamicPathUpdater<T> extends DbUpdaterBasic<T> {
    private PathGetter<T> mGetter;
    private DbUpdater<T> mDataStructure;

    public interface PathGetter<T> {
        String getPath(T item);
    }

    public DynamicPathUpdater(PathGetter<T> getter, DbUpdater<T> dataStructure) {
        mGetter = getter;
        mDataStructure = dataStructure;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(T item, UpdateType updateType) {
        return includePathInKeys(mGetter.getPath(item), mDataStructure.getUpdatesMap(item, updateType));
    }
}
