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
        .FirebaseStructuring.DbStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbStructureBasic;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.StructureBuilder;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureDenormalised extends DbStructureBasic<FbReview> {
    private StructureTags mTags;
    private StructureUsers mUsers;
    private StructureUserReviews mUserReviews;

    public StructureDenormalised(StructureTags tags, StructureUsers users, StructureUserReviews userReviews) {
        mTags = tags;
        mUsers = users;
        mUserReviews = userReviews;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(FbReview item, UpdateType updateType) {
        StructureBuilder<FbReview> builder = new StructureBuilder<>();
        DbStructure<FbReview> structure = builder
                .add(mTags)
                .add(mUsers.pathToAuthor(item.getAuthor().getAuthorId()), mUserReviews)
                .build();

        return structure.getUpdatesMap(item, updateType);
    }
}
