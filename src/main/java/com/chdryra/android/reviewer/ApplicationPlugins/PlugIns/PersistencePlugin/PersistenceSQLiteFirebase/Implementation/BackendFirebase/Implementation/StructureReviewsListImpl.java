/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbStructureBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviewsList;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewsListImpl extends DbStructureBasic<ReviewDb> implements StructureReviewsList {
    public final String mReviewsListPath;

    public StructureReviewsListImpl(String reviewsListPath) {
        mReviewsListPath = reviewsListPath;
    }

    @Override
    public String relativePathToReviewsList() {
        return mReviewsListPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        String reviewId = review.getReviewId();
        ReviewListEntry entry = new ReviewListEntry(review);

        Updates updates = new Updates(updateType);
        updates.atPath(review, relativePathToReviewsList(), reviewId).putObject(entry);

        return updates.toMap();
    }

}
