/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendInfoConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterRating;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterSubject;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbReviewReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewReferencer {
    private final FbDataReferencer mReferencer;
    private final BackendInfoConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final ReviewsCache mCache;

    public FbReviewReferencer(FbDataReferencer referencer,
                              BackendInfoConverter dataConverter,
                              BackendReviewConverter reviewConverter,
                              ReviewsCache cache) {
        mReferencer = referencer;
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mCache = cache;
    }

    public ReviewReference newReference(ReviewListEntry entry, Firebase reviewDb, Firebase
            aggregatesDb) {
        ReviewId reviewId = new DatumReviewId(entry.getReviewId());
        return new FbReviewReference(mDataConverter.convert(entry), reviewDb, aggregatesDb,
                new ConverterSubject(reviewId), new ConverterRating(reviewId),
                new ConverterReview(mReviewConverter), mReferencer, mCache);
    }
}
