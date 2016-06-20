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
        .Backend.Implementation.ReviewAggregates;
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
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ValueBinder;
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
    private final Firebase mData;
    private final Firebase mAggregate;
    private final BackendDataConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final ReviewsCache mCache;
    private final Map<ValueBinder<?>, ValueEventListener> mBindings;

    private interface SnapshotConverter<T> {
        T convert(String reviewId, DataSnapshot snapshot);
    }

    public FbReviewReference(ReviewListEntry entry,
                             Firebase dataReference,
                             Firebase aggregateReference,
                             BackendDataConverter dataConverter,
                             BackendReviewConverter reviewConverter,
                             ReviewsCache cache) {
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mInfo = mDataConverter.convert(entry);
        mData = dataReference;
        mAggregate = aggregateReference;
        mCache = cache;
        mBindings = new HashMap<>();
    }

    @Override
    public ReviewInfo getInfo() {
        return mInfo;
    }

    @Override
    public void bind(final ReferenceBinders.SubjectBinder binder) {
        bindDataBinder(ReviewDb.SUBJECT, binder, new SnapshotConverter<DataSubject>() {
            @Override
            public DataSubject convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convert(reviewId, (String) snapshot.getValue());
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.RatingBinder binder) {
        bindDataBinder(ReviewDb.RATING, binder, new SnapshotConverter<DataRating>() {
            @Override
            public DataRating convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Rating.class));
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.AuthorBinder binder) {
        bindDataBinder(ReviewDb.AUTHOR, binder, new SnapshotConverter<DataAuthorReview>() {
            @Override
            public DataAuthorReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Author.class));
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.DateBinder binder) {
        bindDataBinder(ReviewDb.PUBLISH_DATE, binder, new SnapshotConverter<DataDateReview>() {
            @Override
            public DataDateReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Long.class));
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CoverBinder binder) {
        bindDataBinder(ReviewDb.COVER, binder, new SnapshotConverter<DataImage>() {
            @Override
            public DataImage convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(ImageData.class));
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {
        bindDataBinder(ReviewDb.CRITERIA, binder, new SnapshotConverter<IdableList<? extends 
                DataCriterion>>() {
            @Override
            public IdableList<? extends DataCriterion> convert(String reviewId, DataSnapshot
                    snapshot) {
                List<Criterion> value = (List<Criterion>) snapshot.getValue();
                return mDataConverter.convertCriteria(reviewId, value);
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {
        bindDataBinder(ReviewDb.IMAGES, binder, new SnapshotConverter<IdableList<? extends 
                DataImage>>() {
            @Override
            public IdableList<? extends DataImage> convert(String reviewId, DataSnapshot snapshot) {
                List<ImageData> value = (List<ImageData>) snapshot.getValue();
                return mDataConverter.convertImages(reviewId, value);
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {
        bindDataBinder(ReviewDb.COMMENTS, binder, new SnapshotConverter<IdableList<? extends 
                DataComment>>() {
            @Override
            public IdableList<? extends DataComment> convert(String reviewId, DataSnapshot
                    snapshot) {
                List<Comment> value = (List<Comment>) snapshot.getValue();
                return mDataConverter.convertComments(reviewId, value);
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {
        bindDataBinder(ReviewDb.FACTS, binder, new SnapshotConverter<IdableList<? extends DataFact>>() {
            @Override
            public IdableList<? extends DataFact> convert(String reviewId, DataSnapshot snapshot) {
                List<Fact> value = (List<Fact>) snapshot.getValue();
                return mDataConverter.convertFacts(reviewId, value);
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {
        bindDataBinder(ReviewDb.LOCATIONS, binder, new SnapshotConverter<IdableList<? extends 
                DataLocation>>() {
            @Override
            public IdableList<? extends DataLocation> convert(String reviewId, DataSnapshot
                    snapshot) {
                List<Location> value = (List<Location>) snapshot.getValue();
                return mDataConverter.convertLocations(reviewId, value);
            }
        });
    }


    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        bindDataBinder(ReviewDb.TAGS, binder, new SnapshotConverter<IdableList<? extends DataTag>>() {
            @Override
            public IdableList<DataTag> convert(String reviewId, DataSnapshot snapshot) {
                List<String> value = (List<String>) snapshot.getValue();
                IdableList<DataTag> tags = new IdableDataList<>(mInfo.getReviewId());
                for (String tagString : value) {
                    tags.add(new DatumTag(mInfo.getReviewId(), tagString));
                }

                return tags;
            }
        });
    }

    @Override
    public void bindToCriteria(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.CRITERIA, binder);
    }

    @Override
    public void bindToComments(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.COMMENTS, binder);
    }

    @Override
    public void bindToFacts(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.FACTS, binder);
    }

    @Override
    public void bindToImages(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.IMAGES, binder);
    }

    @Override
    public void bindToLocations(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.LOCATIONS, binder);
    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {
        bindSizeBinder(ReviewAggregates.TAGS, binder);
    }

    @Override
    public void unbind(ReferenceBinders.SubjectBinder binder) {
        unbindDataBinder(ReviewDb.SUBJECT, binder);
    }

    @Override
    public void unbind(ReferenceBinders.RatingBinder binder) {
        unbindDataBinder(ReviewDb.RATING, binder);
    }

    @Override
    public void unbind(ReferenceBinders.AuthorBinder binder) {
        unbindDataBinder(ReviewDb.AUTHOR, binder);
    }

    @Override
    public void unbind(ReferenceBinders.DateBinder binder) {
        unbindDataBinder(ReviewDb.PUBLISH_DATE, binder);
    }

    @Override
    public void unbind(ReferenceBinders.CoverBinder binder) {
        unbindDataBinder(ReviewDb.COVER, binder);
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        unbindDataBinder(ReviewDb.CRITERIA, binder);
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        unbindDataBinder(ReviewDb.COMMENTS, binder);
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        unbindDataBinder(ReviewDb.FACTS, binder);
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        unbindDataBinder(ReviewDb.IMAGES, binder);
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        unbindDataBinder(ReviewDb.LOCATIONS, binder);
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        unbindDataBinder(ReviewDb.TAGS, binder);
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.CRITERIA, binder);
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.COMMENTS, binder);
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.FACTS, binder);
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.IMAGES, binder);
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.LOCATIONS, binder);
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        unbindSizeBinder(ReviewAggregates.TAGS, binder);
    }
    @Override
    public void dereference(final DereferenceCallback callback) {
        ReviewId reviewId = mInfo.getReviewId();
        if (mCache.contains(reviewId)) {
            callback.onDereferenced(mCache.get(reviewId), CallbackMessage.ok());
            return;
        }

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    public boolean isValid() {
        return mInfo.isValid();
    }

    @NonNull
    private SnapshotConverter<Integer> getSize() {
        return new SnapshotConverter<Integer>() {
            @Override
            public Integer convert(String reviewId, DataSnapshot snapshot) {
                return snapshot.getValue(Integer.class);
            }
        };
    }

    private void bindSizeBinder(String child, ValueBinder<Integer> binder) {
        bindBinder(mAggregate, child, binder, getSize());    
    }
    
    private <T> void bindDataBinder(String child, ValueBinder<T> binder,
                                    SnapshotConverter<T> converter) {
        bindBinder(mData, child, binder, converter);
    }

    private <T> void bindBinder(Firebase root, String child, ValueBinder<T> binder,
                                SnapshotConverter<T> converter) {
        if (!mBindings.containsKey(binder)) {
            ValueEventListener listener = newListener(binder, converter);
            root.child(child).addValueEventListener(listener);
            mBindings.put(binder, listener);
        }
    }

    private <T> void unbindSizeBinder(String child, ValueBinder<T> binder) {
        unbindBinder(mAggregate, child, binder);
    }
    
    private <T> void unbindDataBinder(String child, ValueBinder<T> binder) {
        unbindBinder(mData, child, binder);
    }

    private <T> void unbindBinder(Firebase root, String child, ValueBinder<T> binder) {
        if (mBindings.containsKey(binder)) {
            root.child(child).removeEventListener(mBindings.remove(binder));
        }
    }
    
    @NonNull
    private <T> ValueEventListener newListener(final ValueBinder<T> observer,
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
}
