/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructure;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StructureReview extends DbStructure<ReviewDb> {
    String relativePathToReview(String reviewId);

    @NonNull
    @Override
    Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType);
}
