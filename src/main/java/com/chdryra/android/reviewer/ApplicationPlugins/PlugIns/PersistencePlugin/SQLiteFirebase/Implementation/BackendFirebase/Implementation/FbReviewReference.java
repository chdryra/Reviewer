/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendDataConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceObservers;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ValueObserver;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewReference implements ReviewReference {
    private final ReviewInfo mInfo;
    private final Firebase mReference;
    private final BackendDataConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final ReviewsCache mCache;
    private final Map<ValueObserver<?>, ValueEventListener> mBindings;

    private interface SnapshotConverter<T> {
        T convert(String reviewId, DataSnapshot snapshot);
    }

    public FbReviewReference(ReviewListEntry entry,
                             Firebase reference,
                             BackendDataConverter dataConverter,
                             BackendReviewConverter reviewConverter,
                             ReviewsCache cache) {
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mInfo = mDataConverter.convert(entry);
        mReference = reference;
        mCache = cache;
        mBindings = new HashMap<>();
    }

    @Override
    public ReviewInfo getInfo() {
        return mInfo;
    }

    @Override
    public void registerObserver(final ReferenceObservers.SubjectObserver observer) {
        bindObserver(ReviewDb.SUBJECT, observer, new SnapshotConverter<DataSubject>() {
            @Override
            public DataSubject convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convert(reviewId, (String) snapshot.getValue());
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.RatingObserver observer) {
        bindObserver(ReviewDb.RATING, observer, new SnapshotConverter<DataRating>() {
            @Override
            public DataRating convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Rating.class));
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.AuthorObserver observer) {
        bindObserver(ReviewDb.AUTHOR, observer, new SnapshotConverter<DataAuthorReview>() {
            @Override
            public DataAuthorReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Author.class));
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.DateObserver observer) {
        bindObserver(ReviewDb.PUBLISH_DATE, observer, new SnapshotConverter<DataDateReview>() {
            @Override
            public DataDateReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Long.class));
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.CoverObserver observer) {
        bindObserver(ReviewDb.COVER, observer, new SnapshotConverter<DataImage>() {
            @Override
            public DataImage convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(ImageData.class));
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.CriteriaObserver
                                                 observer) {
        bindObserver(ReviewDb.CRITERIA, observer, new SnapshotConverter<IdableList<? extends DataCriterion>>() {
            @Override
            public IdableList<? extends DataCriterion> convert(String reviewId, DataSnapshot
                    snapshot) {
                return mDataConverter.convertCriteria(reviewId, (List<Criterion>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.ImagesObserver observer) {
        bindObserver(ReviewDb.IMAGES, observer, new SnapshotConverter<IdableList<? extends DataImage>>() {
            @Override
            public IdableList<? extends DataImage> convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convertImages(reviewId, (List<ImageData>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.CommentsObserver observer) {
        bindObserver(ReviewDb.COMMENTS, observer, new SnapshotConverter<IdableList<? extends DataComment>>() {
            @Override
            public IdableList<? extends DataComment> convert(String reviewId, DataSnapshot
                    snapshot) {
                return mDataConverter.convertComments(reviewId, (List<Comment>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.FactsObserver observer) {
        bindObserver(ReviewDb.FACTS, observer, new SnapshotConverter<IdableList<? extends DataFact>>() {
            @Override
            public IdableList<? extends DataFact> convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convertFacts(reviewId, (List<Fact>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerObserver(ReferenceObservers.LocationsObserver observer) {
        bindObserver(ReviewDb.LOCATIONS, observer, new SnapshotConverter<IdableList<? extends DataLocation>>() {
            @Override
            public IdableList<? extends DataLocation> convert(String reviewId, DataSnapshot
                    snapshot) {
                return mDataConverter.convertLocations(reviewId, (List<Location>)snapshot.getValue());
            }
        });
    }


    @Override
    public void registerObserver(ReferenceObservers.TagsObserver observer) {
        bindObserver(ReviewDb.TAGS, observer, new SnapshotConverter<IdableList<? extends DataTag>>() {
            @Override
            public IdableList<DataTag> convert(String reviewId, DataSnapshot snapshot) {
                List<String> tagStrings = (List<String>) snapshot.getValue();
                IdableList<DataTag> tags = new IdableDataList<>(mInfo.getReviewId());
                for(String tagString : tagStrings) {
                    tags.add(new DatumTag(mInfo.getReviewId(), tagString));
                }

                return tags;
            }
        });
    }

    @Override
    public void unregisterObserver(ReferenceObservers.SubjectObserver observer) {
        unbindObserver(ReviewDb.SUBJECT, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.RatingObserver observer) {
        unbindObserver(ReviewDb.RATING, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.AuthorObserver observer) {
        unbindObserver(ReviewDb.AUTHOR, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.DateObserver observer) {
        unbindObserver(ReviewDb.PUBLISH_DATE, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CoverObserver observer) {
        unbindObserver(ReviewDb.COVER, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CriteriaObserver observer) {
        unbindObserver(ReviewDb.CRITERIA, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CommentsObserver observer) {
        unbindObserver(ReviewDb.COMMENTS, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.FactsObserver observer) {
        unbindObserver(ReviewDb.FACTS, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.ImagesObserver observer) {
        unbindObserver(ReviewDb.IMAGES, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.LocationsObserver observer) {
        unbindObserver(ReviewDb.LOCATIONS, observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.TagsObserver observer) {
        unbindObserver(ReviewDb.TAGS, observer);
    }

    @Override
    public void dereference(final DereferenceCallback callback) {
        ReviewId reviewId = mInfo.getReviewId();
        if(mCache.contains(reviewId)) {
            callback.onDereferenced(mCache.get(reviewId), CallbackMessage.ok());
            return;
        }

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewDb value = dataSnapshot.getValue(ReviewDb.class);
                callback.onDereferenced(mReviewConverter.convert(value), CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError backendError = FirebaseBackend.backendError(firebaseError);
                callback.onDereferenced(mReviewConverter.getNullReview(),
                        CallbackMessage.error(backendError.getMessage()));
            }
        });
    }

    private <T> void bindObserver(String child, ValueObserver<T> observer,
                                  SnapshotConverter<T> converter) {
        if (!mBindings.containsKey(observer)) {
            ValueEventListener listener = newListener(observer, converter);
            mReference.child(child).addValueEventListener(listener);
            mBindings.put(observer, listener);
        }
    }

    private <T> void unbindObserver(String child, ValueObserver<T> observer) {
        if (mBindings.containsKey(observer)) {
            mReference.child(child).removeEventListener(mBindings.remove(observer));
        }
    }

    @NonNull
    private <T> ValueEventListener newListener(final ValueObserver<T> observer,
                                               final SnapshotConverter<T> converter) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                observer.onValue(converter.convert(mInfo.getReviewId().toString(), dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @Override
    public boolean isValid() {
        return mInfo.isValid();
    }
}
