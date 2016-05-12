/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation
        .ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbStructureBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Interfaces.StructureUserData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserDataImpl extends DbStructureBasic<ReviewDb> implements StructureUserData {
    private final String mReviewsPath;
    private final String mTagsPath;
    private final String mFeedPath;

    public StructureUserDataImpl(String reviewsPath, String tagsPath, String feedPath) {
        mReviewsPath = reviewsPath;
        mTagsPath = tagsPath;
        mFeedPath = feedPath;
    }

    @Override
    public String relativePathToFeed() {
        return mFeedPath;
    }

    private String pathToTag(String tag) {
        return path(mTagsPath, tag);
    }

    private String pathToReviews() {
        return mReviewsPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Boolean trueValue = update ? true : null;

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();

        for (String tag : review.getTags()) {
            updates.put(absolutePath(review, pathToTag(tag), reviewId), trueValue);
        }

        updates.put(absolutePath(review, pathToReviews(), reviewId), trueValue);
        updates.put(absolutePath(review, relativePathToFeed(), reviewId), trueValue);

        return updates;
    }
}
