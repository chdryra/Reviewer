/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewAggregates;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewReference extends FbReviewItemRef<Review> implements ReviewReference {
    private final DataReviewInfo mInfo;
    private final Firebase mReference;
    private final Firebase mAggregate;
    private final ReviewsCache mCache;
    private final FactoryFbReference mReferencer;

    public FbReviewReference(DataReviewInfo info,
                             Firebase reviewReference,
                             Firebase aggregateReference,
                             SnapshotConverter<Review> converter,
                             FactoryFbReference referencer,
                             ReviewsCache cache) {
        super(info.getReviewId(), reviewReference, converter);
        mInfo = info;
        mReference = reviewReference;
        mAggregate = aggregateReference;
        mCache = cache;

        mReferencer = referencer;
    }

    @Override
    public DataSubject getSubject() {
        return mInfo.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mInfo.getRating();
    }

    @Override
    public DataDate getPublishDate() {
        return mInfo.getPublishDate();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mInfo.getAuthorId();
    }

    @Override
    public ReviewId getReviewId() {
        return mInfo.getReviewId();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mReferencer.newImage(mReference.child(ReviewDb.COVER), getReviewId());
    }

    @Override
    public RefDataList<DataCriterion> getCriteria() {
        return mReferencer.newCriteria(mReference.child(ReviewDb.CRITERIA), getReviewId(),
                getSize(ReviewAggregates.CRITERIA));
    }

    @Override
    public RefDataList<DataTag> getTags() {
        return mReferencer.newTags(mReference.child(ReviewDb.TAGS), getReviewId(),
                getSize(ReviewAggregates.TAGS));
    }

    @Override
    public RefCommentList getComments() {
        return mReferencer.newComments(mReference.child(ReviewDb.COMMENTS), getReviewId(),
                getSize(ReviewAggregates.COMMENTS));
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return mReferencer.newFacts(mReference.child(ReviewDb.FACTS), getReviewId(),
                getSize(ReviewAggregates.FACTS));
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return mReferencer.newImages(mReference.child(ReviewDb.IMAGES), getReviewId(),
                getSize(ReviewAggregates.IMAGES));
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return mReferencer.newLocations(mReference.child(ReviewDb.LOCATIONS), getReviewId(),
                getSize(ReviewAggregates.LOCATIONS));
    }

    @Override
    public void dereference(final DereferenceCallback<Review> callback) {
        ReviewId reviewId = mInfo.getReviewId();
        if (mCache.contains(reviewId)) {
            callback.onDereferenced(mCache.get(reviewId), CallbackMessage.ok());
        } else {
            super.dereference(callback);
        }
    }

    @Override
    protected void onDereferenced(Review value) {
        mCache.add(value);
    }

    private ReviewItemReference<DataSize> getSize(String aggregatesName) {
        return mReferencer.newSize(mAggregate.child(aggregatesName), getReviewId());
    }
}
