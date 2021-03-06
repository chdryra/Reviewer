/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewAggregates;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewReference extends FbReviewItemRef<Review> implements ReviewReference {
    private final DataReview mInfo;
    private final SnapshotConverter<DataSubject> mSubjectConverter;
    private final SnapshotConverter<DataRating> mRatingConverter;
    private final Firebase mReference;
    private final Firebase mAggregate;
    private final ReviewsCache mCache;
    private final FbDataReferencer mReferencer;

    private ValueEventListener mRatingListener;
    private ValueEventListener mSubjectListener;

    private DataSubject mSubject;
    private DataRating mRating;

    private ArrayList<ReviewReferenceObserver> mObservers;
    private ArrayList<ReviewReferenceObserver> mToRemove;
    private ArrayList<ReviewReferenceObserver> mToAdd;
    private boolean mLocked = false;

    public FbReviewReference(DataReview info,
                             Firebase reviewReference,
                             Firebase aggregateReference,
                             SnapshotConverter<DataSubject> subjectConverter,
                             SnapshotConverter<DataRating> ratingConverter,
                             SnapshotConverter<Review> converter,
                             FbDataReferencer referencer,
                             ReviewsCache cache) {
        super(info.getReviewId(), reviewReference, converter);
        mInfo = info;
        mSubject = mInfo.getSubject();
        mRating = mInfo.getRating();

        mSubjectConverter = subjectConverter;
        mRatingConverter = ratingConverter;
        mReference = reviewReference;
        mSubjectListener = subjectChangeListener();
        mRatingListener = ratingChangeListener();
        mAggregate = aggregateReference;
        mCache = cache;
        mReferencer = referencer;
        mObservers = new ArrayList<>();
        mToRemove = new ArrayList<>();
        mToAdd = new ArrayList<>();
    }

    @Override
    public void registerObserver(ReviewReferenceObserver observer) {
        addSubjectRatingListenersIfNecessary();

        if (!mObservers.contains(observer)) {
            if (!mLocked) {
                mObservers.add(observer);
            } else if (!mToAdd.contains(observer)) {
                mToAdd.add(observer);
            }
        }
    }

    @Override
    public void unregisterObserver(ReviewReferenceObserver observer) {
        if (mObservers.contains(observer)) {
            if (!mLocked) {
                mObservers.remove(observer);
            } else if (!mToRemove.contains(observer)) {
                mToRemove.add(observer);
            }
        }
        removeSubjectRatingListenersIfNecessary();
    }

    @Override
    public DataSubject getSubject() {
        return mSubject;
    }

    @Override
    public DataRating getRating() {
        return mRating;
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
    public DataListRef<DataCriterion> getCriteria() {
        return mReferencer.newCriteria(mReference.child(ReviewDb.CRITERIA), getReviewId(),
                getSize(ReviewAggregates.CRITERIA));
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return mReferencer.newTags(mReference.child(ReviewDb.TAGS), getReviewId(),
                getSize(ReviewAggregates.TAGS));
    }

    @Override
    public CommentListRef getComments() {
        return mReferencer.newComments(mReference.child(ReviewDb.COMMENTS), getReviewId(),
                getSize(ReviewAggregates.COMMENTS));
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return mReferencer.newFacts(mReference.child(ReviewDb.FACTS), getReviewId(),
                getSize(ReviewAggregates.FACTS));
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return mReferencer.newImages(mReference.child(ReviewDb.IMAGES), getReviewId(),
                getSize(ReviewAggregates.IMAGES));
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return mReferencer.newLocations(mReference.child(ReviewDb.LOCATIONS), getReviewId(),
                getSize(ReviewAggregates.LOCATIONS));
    }

    @Override
    public void dereference(final DereferenceCallback<Review> callback) {
        ReviewId reviewId = mInfo.getReviewId();
        if (mCache.contains(reviewId)) {
            callback.onDereferenced(new DataValue<>(mCache.get(reviewId)));
        } else {
            super.dereference(callback);
        }
    }

    @Override
    protected void onDereferenced(Review value) {
        mCache.add(value);
    }

    private void addSubjectRatingListenersIfNecessary() {
        if (mObservers.size() == 0) {
            mSubjectListener = subjectChangeListener();
            mRatingListener = ratingChangeListener();
            mReference.child(ReviewDb.SUBJECT).addValueEventListener(mSubjectListener);
            mReference.child(ReviewDb.RATING).addValueEventListener(mRatingListener);
        }
    }

    private void removeSubjectRatingListenersIfNecessary() {
        if (mObservers.size() == 0) {
            mReference.child(ReviewDb.SUBJECT).removeEventListener(mSubjectListener);
            mReference.child(ReviewDb.RATING).removeEventListener(mRatingListener);
        }
    }

    private void notifySubjectChanged(DataSubject newSubject) {
        mLocked = true;

        for (ReviewReferenceObserver observer : mObservers) {
            observer.onSubjectChanged(newSubject);
        }

        updateObserverRegistrations();

        mLocked = false;
    }

    private void updateObserverRegistrations() {
        if (mToAdd.size() > 0) {
            addSubjectRatingListenersIfNecessary();
            mObservers.addAll(mToAdd);
            mToAdd.clear();
        }

        if (mToRemove.size() > 0) {
            mObservers.removeAll(mToRemove);
            mToRemove.clear();
            removeSubjectRatingListenersIfNecessary();
        }
    }

    private void notifyRatingChanged(DataRating newRating) {
        mLocked = true;

        for (ReviewReferenceObserver observer : mObservers) {
            observer.onRatingChanged(newRating);
        }

        updateObserverRegistrations();

        mLocked = false;
    }

    @NonNull
    private ValueEventListener ratingChangeListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataRating rating = mRatingConverter.convert(dataSnapshot);
                if (rating != null && !rating.equals(mRating)) {
                    mRating = rating;
                    notifyRatingChanged(mRating);
                } else if (rating == null) {
                    invalidate();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @NonNull
    private ValueEventListener subjectChangeListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSubject subject = mSubjectConverter.convert(dataSnapshot);
                if (subject != null && !subject.equals(mSubject)) {
                    mSubject = subject;
                    notifySubjectChanged(mSubject);
                } else if (subject == null) {
                    invalidate();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    private ReviewItemReference<DataSize> getSize(String aggregatesName) {
        return mReferencer.newSize(mAggregate.child(aggregatesName), getReviewId());
    }
}
