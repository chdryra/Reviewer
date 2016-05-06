/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.FirebaseStructuring;


import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbUpdater<T> {
    enum UpdateType{INSERT_OR_UPDATE, DELETE}

    @NonNull
    Map<String, Object> getUpdatesMap(T item, UpdateType updateType);
}
