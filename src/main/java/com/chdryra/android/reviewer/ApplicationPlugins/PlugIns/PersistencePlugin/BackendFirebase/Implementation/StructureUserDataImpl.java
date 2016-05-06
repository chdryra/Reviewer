/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses
        .ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Interfaces.StructureUserData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserDataImpl extends DbUpdaterBasic<ReviewDb> implements StructureUserData {
    private final String mReviewsPath;
    private final String mTagsPath;
    private final String mFeedPath;

    public StructureUserDataImpl(String reviewsPath, String tagsPath, String feedPath) {
        mReviewsPath = reviewsPath;
        mTagsPath = tagsPath;
        mFeedPath = feedPath;
    }

    @Override
    public String getPathToFeed() {
        return mFeedPath;
    }

    private String getPathToTag(ReviewDb review, String tag) {
        return path(getPath(review), mTagsPath, tag);
    }

    private String getPathToReviews(ReviewDb review) {
        return path(getPath(review), mReviewsPath);
    }

    private String getPathToFeed(ReviewDb review) {
        return path(getPath(review), mFeedPath);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Boolean trueValue = update ? true : null;

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();

        for (String tag : review.getTags()) {
            updates.put(path(getPathToTag(review, tag), reviewId), trueValue);
        }

        updates.put(path(getPathToReviews(review), reviewId), trueValue);
        updates.put(path(getPathToFeed(review), reviewId), trueValue);

        return updates;
    }
}
