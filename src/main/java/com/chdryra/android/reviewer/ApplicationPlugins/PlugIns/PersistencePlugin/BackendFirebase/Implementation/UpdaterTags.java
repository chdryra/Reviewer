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
public class UpdaterTags extends DbUpdaterBasic<FbReview> {
    private String mTagsPath;
    private String mReviewsPath;
    private String mUsersPath;

    public UpdaterTags(String tagsPath, String reviewsPath, String usersPath) {
        mTagsPath = tagsPath;
        mReviewsPath = reviewsPath;
        mUsersPath = usersPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(FbReview review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Boolean trueValue = update ? true : null;

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();
        String authorId = review.getAuthor().getAuthorId();
        for (String tag : review.getTags()) {
            updates.put(path(mTagsPath, tag, mReviewsPath, reviewId), trueValue);
            updates.put(path(mTagsPath, tag, mUsersPath, authorId, reviewId), trueValue);
        }

        return updates;
    }
}
