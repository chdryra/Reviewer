/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbAuthorsDb extends FbReviewsUpdateable, HasAuthorId {
    Firebase getReviewDb(Firebase root, ReviewId reviewId);

    Firebase getAggregatesDb(Firebase root, ReviewId reviewId);
}
