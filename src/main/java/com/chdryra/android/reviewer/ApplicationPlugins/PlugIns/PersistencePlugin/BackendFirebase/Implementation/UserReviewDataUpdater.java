/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdaterBasic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserReviewDataUpdater extends DbUpdaterBasic<FbReview> {
    private DbUpdater<FbReview> mDataStructure;

    public UserReviewDataUpdater(DbUpdater<FbReview> dataStructure) {
        mDataStructure = dataStructure;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(FbReview item, UpdateType updateType) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(path(item.getAuthor().getAuthorId()), mDataStructure.getUpdatesMap(item, updateType));

        return updates;
    }
}
