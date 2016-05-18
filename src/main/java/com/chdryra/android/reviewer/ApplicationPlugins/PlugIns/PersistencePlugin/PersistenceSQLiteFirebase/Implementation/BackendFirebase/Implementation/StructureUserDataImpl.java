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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbStructureBasic;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviewsList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Interfaces.StructureUserData;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureUserDataImpl extends DbStructureBasic<ReviewDb> implements StructureUserData {
    private final StructureReviewsList mReviews;
    private final StructureReviewsList mFeed;
    private final StructureUserTags mTags;

    public StructureUserDataImpl(StructureReviewsList reviews, StructureReviewsList feed, StructureUserTags tags) {
        mReviews = reviews;
        mTags = tags;
        mFeed = feed;
    }

    @Override
    public String relativePathToFeed() {
        return mFeed.relativePathToReviewsList();
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        Updates updates = new Updates(updateType);

        updates.atPath(review).putMap(mTags.getUpdatesMap(review, updateType));
        updates.atPath(review).putMap(mFeed.getUpdatesMap(review, updateType));
        updates.atPath(review).putMap(mReviews.getUpdatesMap(review, updateType));

        return updates.toMap();
    }
}
