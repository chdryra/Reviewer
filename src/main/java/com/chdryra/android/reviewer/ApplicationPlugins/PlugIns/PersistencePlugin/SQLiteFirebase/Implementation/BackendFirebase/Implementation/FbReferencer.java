/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendDataConverter;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReferencer {
    private BackendDataConverter mDataConverter;
    private BackendReviewConverter mReviewConverter;
    private ReviewsCache mCache;

    public FbReferencer(BackendDataConverter dataConverter, BackendReviewConverter
            reviewConverter, ReviewsCache cache) {
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mCache = cache;
    }

    public ReviewReference newReference(ReviewListEntry entry, Firebase reviewDb, Firebase aggregatesDb) {
        return new FbReviewReference(entry, reviewDb, aggregatesDb, mDataConverter, mReviewConverter, mCache);
    }
}
