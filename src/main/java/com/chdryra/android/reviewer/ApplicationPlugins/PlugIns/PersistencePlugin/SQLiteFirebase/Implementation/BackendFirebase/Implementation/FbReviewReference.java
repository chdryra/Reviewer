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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendDataConverter;
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
        .Backend.Implementation.ReviewAggregates;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ValueBinder;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewReference implements ReviewReference {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private final ReviewInfo mInfo;
    private final Firebase mData;
    private final Firebase mAggregate;
    private final BackendDataConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final ReviewsCache mCache;
    private final Map<ValueBinder<?>, ValueEventListener> mBindings;

    private final ReviewNode mNode;

    private interface SnapshotConverter<T> {
        T convert(DataSnapshot snapshot);
    }

    private interface DataMethod<T extends HasReviewId> {
        void execute(IdableList<T> data, CallbackMessage message);
    }

    private interface SizeMethod {
        void execute(DataSize size, CallbackMessage message);
    }
    
    public FbReviewReference(ReviewListEntry entry,
                             Firebase dataReference,
                             Firebase aggregateReference,
                             BackendDataConverter dataConverter,
                             BackendReviewConverter reviewConverter,
                             ReviewsCache cache,
                             FactoryReviews reviewsFactory) {
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mInfo = mDataConverter.convert(entry);
        mData = dataReference;
        mAggregate = aggregateReference;
        mCache = cache;
        mBindings = new HashMap<>();

        mNode = reviewsFactory.createLeafNode(this);
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
    public DataDateReview getPublishDate() {
        return mInfo.getPublishDate();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mInfo.getAuthor();
    }

    @Override
    public ReviewId getReviewId() {
        return mInfo.getReviewId();
    }

    @Override
    public ReviewNode asNode() {
        return mNode;
    }

    @Override
    public void getData(final CoverCallback callback) {
        getCover(callback);
    }

    @Override
    public void getData(final TagsCallback callback) {
        getData(ReviewDb.TAGS,
                getTagsConverter(),
                new IdableDataList<DataTag>(getReviewId()),
                new DataMethod<DataTag>() {
                    @Override
                    public void execute(IdableList<DataTag> data, CallbackMessage message) {
                        callback.onTags(data, message);
                    }
                });
    }

    @Override
    public void getData(final CriteriaCallback callback) {
        getData(ReviewDb.CRITERIA,
                getCriteriaConverter(),
                new IdableDataList<DataCriterion>(getReviewId()),
                new DataMethod<DataCriterion>() {
                    @Override
                    public void execute(IdableList<DataCriterion> data, CallbackMessage message) {
                        callback.onCriteria(data, message);
                    }
                });
    }

    @Override
    public void getData(final ImagesCallback callback) {
        getData(ReviewDb.IMAGES,
                getImagesConverter(),
                new IdableDataList<DataImage>(getReviewId()),
                new DataMethod<DataImage>() {
                    @Override
                    public void execute(IdableList<DataImage> data, CallbackMessage message) {
                        callback.onImages(data, message);
                    }
                });
    }

    @Override
    public void getData(final CommentsCallback callback) {
    getData(ReviewDb.COMMENTS,
            getCommentsConverter(),
            new IdableDataList<DataComment>(getReviewId()),
            new DataMethod<DataComment>() {
                @Override
                public void execute(IdableList<DataComment> data, CallbackMessage message) {
                    callback.onComments(data, message);
                }
            });
    }

    @Override
    public void getData(final LocationsCallback callback) {
        getData(ReviewDb.LOCATIONS,
                getLocationsConverter(),
                new IdableDataList<DataLocation>(getReviewId()),
                new DataMethod<DataLocation>() {
                    @Override
                    public void execute(IdableList<DataLocation> data, CallbackMessage message) {
                        callback.onLocations(data, message);
                    }
                });
    }

    @Override
    public void getData(final FactsCallback callback) {
        getData(ReviewDb.FACTS,
                getFactsConverter(),
                new IdableDataList<DataFact>(getReviewId()),
                new DataMethod<DataFact>() {
                    @Override
                    public void execute(IdableList<DataFact> data, CallbackMessage message) {
                        callback.onFacts(data, message);
                    }
                });
    }

    @Override
    public void getSize(final TagsSizeCallback callback) {
        getSize(ReviewAggregates.TAGS, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumTags(size, message);
            }
        });
    }

    @Override
    public void getSize(final CriteriaSizeCallback callback) {
        getSize(ReviewAggregates.CRITERIA, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumCriteria(size, message);
            }
        });
    }

    @Override
    public void getSize(final ImagesSizeCallback callback) {
        getSize(ReviewAggregates.IMAGES, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumImages(size, message);
            }
        });
    }

    @Override
    public void getSize(final CommentsSizeCallback callback) {
        getSize(ReviewAggregates.COMMENTS, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumComments(size, message);
            }
        });
    }

    @Override
    public void getSize(final LocationsSizeCallback callback) {
        getSize(ReviewAggregates.LOCATIONS, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumLocations(size, message);
            }
        });
    }

    @Override
    public void getSize(final FactsSizeCallback callback) {
        getSize(ReviewAggregates.FACTS, new SizeMethod() {
            @Override
            public void execute(DataSize size, CallbackMessage message) {
                callback.onNumFacts(size, message);
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CoverBinder binder) {
        bindDataBinder(ReviewDb.COVER, binder, getCoverConverter());
    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {
        bindListBinder(ReviewDb.CRITERIA, binder, getCriteriaConverter());
    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {
        bindListBinder(ReviewDb.IMAGES, binder, getImagesConverter());
    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {
        bindListBinder(ReviewDb.COMMENTS, binder, getCommentsConverter());
    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {
        bindListBinder(ReviewDb.FACTS, binder, getFactsConverter());
    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {
        bindListBinder(ReviewDb.LOCATIONS, binder, getLocationsConverter());
    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        bindListBinder(ReviewDb.TAGS, binder, getTagsConverter());
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
                Review review = mReviewConverter.convert(value);
                mCache.add(review);
                callback.onDereferenced(review, CallbackMessage.ok());
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
    public boolean isValidReference() {
        return mInfo.isValid();
    }

    @NonNull
    private SnapshotConverter<IdableList<DataTag>> getTagsConverter() {
        return new SnapshotConverter<IdableList<DataTag>>() {
            @Override
            public IdableList<DataTag> convert(DataSnapshot snapshot) {
                List<String> value = (List<String>) snapshot.getValue();
                IdableList<DataTag> tags = new IdableDataList<>(mInfo.getReviewId());
                for (String tagString : value) {
                    tags.add(new DatumTag(mInfo.getReviewId(), tagString));
                }

                return tags;
            }
        };
    }

    @NonNull
    private SnapshotConverter<DataImage> getCoverConverter() {
        return new SnapshotConverter<DataImage>() {
            @Override
            public DataImage convert(DataSnapshot snapshot) {
                ImageData value = snapshot.getValue(ImageData.class);
                return mDataConverter.convert(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private SnapshotConverter<IdableList<DataCriterion>> getCriteriaConverter() {
        return new SnapshotConverter<IdableList<DataCriterion>>() {
            @Override
            public IdableList<DataCriterion> convert(DataSnapshot snapshot) {
                List<Criterion> value = toList(snapshot, Criterion.class);
                return mDataConverter.convertCriteria(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private SnapshotConverter<IdableList<DataImage>> getImagesConverter() {
        return new SnapshotConverter<IdableList<DataImage>>() {
            @Override
            public IdableList<DataImage> convert(DataSnapshot snapshot) {
                List<ImageData> value = toList(snapshot, ImageData.class);
                return mDataConverter.convertImages(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private <T> List<T> toList(DataSnapshot snapshot, Class<T> valueType) {
        List<T> value = new ArrayList<>();
        for(DataSnapshot child : snapshot.getChildren()) {
            value.add(child.getValue(valueType));
        }
        return value;
    }

    @NonNull
    private SnapshotConverter<IdableList<DataComment>> getCommentsConverter() {
        return new SnapshotConverter<IdableList<DataComment>>() {
            @Override
            public IdableList<DataComment> convert(DataSnapshot snapshot) {
                List<Comment> value = toList(snapshot, Comment.class);
                return mDataConverter.convertComments(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private SnapshotConverter<IdableList<DataFact>> getFactsConverter() {
        return new SnapshotConverter<IdableList<DataFact>>() {
            @Override
            public IdableList<DataFact> convert(DataSnapshot snapshot) {
                List<Fact> value = toList(snapshot, Fact.class);
                return mDataConverter.convertFacts(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private SnapshotConverter<IdableList<DataLocation>> getLocationsConverter() {
        return new SnapshotConverter<IdableList<DataLocation>>() {
            @Override
            public IdableList<DataLocation> convert(DataSnapshot snapshot) {
                List<Location> value = toList(snapshot, Location.class);
                return mDataConverter.convertLocations(getReviewId().toString(), value);
            }
        };
    }

    @NonNull
    private SnapshotConverter<DataSize> getSize() {
        return new SnapshotConverter<DataSize>() {
            @Override
            public DataSize convert(DataSnapshot snapshot) {
                return new DatumSize(getReviewId(), snapshot.getValue(Integer.class));
            }
        };
    }

    private void getCover(final CoverCallback callback) {
        mData.child(ReviewDb.COVER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCover(getCoverConverter().convert(dataSnapshot), OK);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onCover(new DatumImage(getReviewId()), CallbackMessage.error("Cover not found"));
            }
        });
    }

    private <T extends HasReviewId> void getData(String child,
                                                 final SnapshotConverter<IdableList<T>> converter,
                                                 final IdableDataList<T> emptyData,
                                                 final DataMethod<T> method) {
        mData.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emptyData.addAll(converter.convert(dataSnapshot));
                method.execute(emptyData, OK);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                method.execute(emptyData, getErrorMessage(firebaseError));
            }
        });
    }

    private void getSize(String child, final SizeMethod method) {
        mAggregate.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSize size = getSize().convert(dataSnapshot);
                method.execute(size, CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                method.execute(new DatumSize(getReviewId(), 0), getErrorMessage(firebaseError));
            }
        });
    }
    
    private CallbackMessage getErrorMessage(FirebaseError firebaseError) {
        BackendError backendError = FirebaseBackend.backendError(firebaseError);
        return CallbackMessage.error(backendError.getMessage());
    }

    private <T extends HasReviewId> void bindListBinder(String child,
                                                        ValueBinder<IdableList<? extends T>> binder,
                                                        SnapshotConverter<IdableList<T>> converter) {
        if (!mBindings.containsKey(binder)) {
            bind(mData, child, binder, newListListener(binder, converter));
        }
    }

    private <T extends HasReviewId> void bindDataBinder(String child,
                                                        ValueBinder<T> binder,
                                                        SnapshotConverter<T> converter) {
        if (!mBindings.containsKey(binder)) {
            bind(mData, child, binder, newDataListener(binder, converter));
        }
    }

    private void bindSizeBinder(String child, ValueBinder<DataSize> binder) {
        if (!mBindings.containsKey(binder)) {
            bind(mAggregate, child, binder, newSizeListener(binder, getSize()));
        }
    }

    private void bind(Firebase root, String child, ValueBinder<?> binder, ValueEventListener listener) {
        root.child(child).addValueEventListener(listener);
        mBindings.put(binder, listener);
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
    private <T extends HasReviewId> ValueEventListener newListListener(final ValueBinder<IdableList<? extends T>> binder,
                                                                       final SnapshotConverter<IdableList<T>> converter) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binder.onValue(converter.convert(dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @NonNull
    private <T extends HasReviewId> ValueEventListener newDataListener(final ValueBinder<T> binder,
                                                                       final SnapshotConverter<T> converter) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binder.onValue(converter.convert(dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @NonNull
    private ValueEventListener newSizeListener(final ValueBinder<DataSize> binder,
                                                                   final SnapshotConverter<DataSize> converter) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binder.onValue(converter.convert(dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
