/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Interfaces;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase
        .FirebaseStructuring.PathMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation.ReviewDb;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StructureReviews extends DbUpdater<ReviewDb> {
    void setPathMaker(PathMaker<ReviewDb> pathMaker);

    String getReviewDataPath();

    String getReviewListPath();

    String getReviewPath(String reviewId);

    @NonNull
    @Override
    Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType);
}
