/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbStructure;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StructureUserTags extends DbStructure<ReviewDb> {
    String relativePathToTag(String tag);

    @NonNull
    @Override
    Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType);
}
