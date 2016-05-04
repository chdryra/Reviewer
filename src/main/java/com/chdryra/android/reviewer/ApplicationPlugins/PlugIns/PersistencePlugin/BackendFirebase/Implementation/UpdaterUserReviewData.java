/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UpdaterUserReviewData extends DbUpdaterBasic<FbReview> {
    private final String mReviewsPath;
    private final String mTagsPath;
    private final String mFeedPath;

    public UpdaterUserReviewData(String reviewsPath, String tagsPath, String feedPath) {
        mReviewsPath = reviewsPath;
        mTagsPath = tagsPath;
        mFeedPath = feedPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(FbReview review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Boolean trueValue = update ? true : null;

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();

        for (String tag : review.getTags()) {
            updates.put(path(mTagsPath, tag , reviewId), trueValue);
        }

        updates.put(path(mReviewsPath, reviewId), trueValue);
        updates.put(path(mFeedPath, reviewId), trueValue);

        return updates;
    }
}
