/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation
        .ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase
        .Interfaces.StructureTags;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureTagsImpl extends DbUpdaterBasic<ReviewDb> implements StructureTags {
    private String mReviewsPath;
    private String mUsersPath;

    public StructureTagsImpl(String reviewsPath, String usersPath) {
        mReviewsPath = reviewsPath;
        mUsersPath = usersPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;
        Boolean trueValue = update ? true : null;

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();
        String authorId = review.getAuthor().getAuthorId();
        for (String tag : review.getTags()) {
            updates.put(path(getPath(review), tag, mReviewsPath, reviewId), trueValue);
            updates.put(path(getPath(review), tag, mUsersPath, authorId, reviewId), trueValue);
        }

        return updates;
    }
}
