/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces;


import android.support.annotation.Nullable;

import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SnapshotConverter<T> {
    //TODO move SnapshotConverters into relevant DbStructures as implicitly use the structures
    @Nullable
    T convert(DataSnapshot snapshot);
}
