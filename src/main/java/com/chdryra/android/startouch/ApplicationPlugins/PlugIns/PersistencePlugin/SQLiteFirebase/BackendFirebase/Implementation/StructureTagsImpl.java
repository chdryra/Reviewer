/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.StructureTags;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureTagsImpl extends DbStructureBasic<ReviewDb> implements StructureTags {
    private final String mReviewsPath;
    private final String mUsersPath;

    public StructureTagsImpl(String reviewsPath, String usersPath) {
        mReviewsPath = reviewsPath;
        mUsersPath = usersPath;
    }

    public StructureTagsImpl(String reviewsPath, String usersPath, String pathToStructure) {
        mReviewsPath = reviewsPath;
        mUsersPath = usersPath;
        setPathToStructure(pathToStructure);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        String reviewId = review.getReviewId();
        String authorId = review.getAuthorId();

        Updates updates = new Updates(updateType);
        for (String tag : review.getTags()) {
            updates.atPath(review, tag, mReviewsPath, reviewId).putValue(true);
            updates.atPath(review, tag, mUsersPath, authorId, reviewId).putValue(true);
        }

        return updates.toMap();
    }
}
