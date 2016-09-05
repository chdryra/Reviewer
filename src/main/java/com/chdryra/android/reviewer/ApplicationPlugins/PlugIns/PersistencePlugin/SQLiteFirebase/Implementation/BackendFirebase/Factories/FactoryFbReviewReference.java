/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NullReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbReviewReference {
    private final FactoryFbReference mReferencer;
    private final BackendInfoConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final ReviewsCache mCache;

    public FactoryFbReviewReference(FactoryFbReference referencer,
                                    BackendInfoConverter dataConverter,
                                    BackendReviewConverter reviewConverter,
                                    ReviewsCache cache) {
        mReferencer = referencer;
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mCache = cache;
    }

    public TagsManager getTagsManager() {
        return mReviewConverter.getTagsManager();
    }


    public ReviewReference newReview(ReviewListEntry entry, Firebase reviewDb, Firebase aggregatesDb) {
        return new FbReviewReference(mDataConverter.convert(entry), reviewDb, aggregatesDb,
                new ConverterReview(mReviewConverter), mReferencer, mCache);
    }

    public ReviewReference newNullReview() {
        return new NullReviewReference();
    }
}
