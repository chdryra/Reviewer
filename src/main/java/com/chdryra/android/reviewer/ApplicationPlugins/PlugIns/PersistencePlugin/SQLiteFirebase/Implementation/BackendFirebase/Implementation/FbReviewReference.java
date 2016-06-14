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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendDataConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
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
    private final ReviewListEntry mEntry;
    private final Firebase mReference;
    private final BackendDataConverter mDataConverter;
    private final BackendReviewConverter mReviewConverter;
    private final Map<ValueObserver<?>, ValueEventListener> mBindings;

    private interface SnapshotConverter<T> {
        T convert(String reviewId, DataSnapshot snapshot);
    }

    public FbReviewReference(ReviewListEntry entry,
                             Firebase reference,
                             BackendDataConverter dataConverter,
                             BackendReviewConverter reviewConverter) {
        mEntry = entry;
        mReference = reference;
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mBindings = new HashMap<>();
    }

    @Override
    public ReviewListEntry getBasicInfo() {
        return mEntry;
    }

    @Override
    public void registerSubjectObserver(final ValueObserver<DataSubject> observer) {
        bindObserver(ReviewDb.SUBJECT, observer, new SnapshotConverter<DataSubject>() {
            @Override
            public DataSubject convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convert(reviewId, (String) snapshot.getValue());
            }
        });
    }

    @Override
    public void registerRatingObserver(ValueObserver<DataRating> observer) {
        bindObserver(ReviewDb.RATING, observer, new SnapshotConverter<DataRating>() {
            @Override
            public DataRating convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Rating.class));
            }
        });
    }

    @Override
    public void registerAuthorObserver(ValueObserver<DataAuthorReview> observer) {
        bindObserver(ReviewDb.AUTHOR, observer, new SnapshotConverter<DataAuthorReview>() {
            @Override
            public DataAuthorReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Author.class));
            }
        });
    }

    @Override
    public void registerPublishDateObserver(ValueObserver<DataDateReview> observer) {
        bindObserver(ReviewDb.PUBLISH_DATE, observer, new SnapshotConverter<DataDateReview>() {
            @Override
            public DataDateReview convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(Long.class));
            }
        });
    }

    @Override
    public void registerCoverObserver(ValueObserver<DataImage> observer) {
        bindObserver(ReviewDb.COVER, observer, new SnapshotConverter<DataImage>() {
            @Override
            public DataImage convert(String id, DataSnapshot snapshot) {
                return mDataConverter.convert(id, snapshot.getValue(ImageData.class));
            }
        });
    }

    @Override
    public void registerCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>>
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
    public void registerImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer) {
        bindObserver(ReviewDb.IMAGES, observer, new SnapshotConverter<IdableList<? extends DataImage>>() {
            @Override
            public IdableList<? extends DataImage> convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convertImages(reviewId, (List<ImageData>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerCommentsObserver(ValueObserver<IdableList<? extends DataComment>> observer) {
        bindObserver(ReviewDb.COMMENTS, observer, new SnapshotConverter<IdableList<? extends DataComment>>() {
            @Override
            public IdableList<? extends DataComment> convert(String reviewId, DataSnapshot
                    snapshot) {
                return mDataConverter.convertComments(reviewId, (List<Comment>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer) {
        bindObserver(ReviewDb.FACTS, observer, new SnapshotConverter<IdableList<? extends DataFact>>() {
            @Override
            public IdableList<? extends DataFact> convert(String reviewId, DataSnapshot snapshot) {
                return mDataConverter.convertFacts(reviewId, (List<Fact>)snapshot.getValue());
            }
        });
    }

    @Override
    public void registerLocationsObserver(ValueObserver<IdableList<? extends DataLocation>> observer) {
        bindObserver(ReviewDb.LOCATIONS, observer, new SnapshotConverter<IdableList<? extends DataLocation>>() {
            @Override
            public IdableList<? extends DataLocation> convert(String reviewId, DataSnapshot
                    snapshot) {
                return mDataConverter.convertLocations(reviewId, (List<Location>)snapshot.getValue());
            }
        });
    }


    @Override
    public void registerTagsObserver(ValueObserver<List<String>> observer) {
        bindObserver(ReviewDb.TAGS, observer, new SnapshotConverter<List<String>>() {
            @Override
            public List<String> convert(String reviewId, DataSnapshot snapshot) {
                return (List<String>)snapshot.getValue();
            }
        });
    }

    @Override
    public void unregisterSubjectObserver(ValueObserver<DataSubject> observer) {
        unbindObserver(ReviewDb.SUBJECT, observer);
    }

    @Override
    public void unregisterRatingObserver(ValueObserver<DataRating> observer) {
        unbindObserver(ReviewDb.RATING, observer);
    }

    @Override
    public void unregisterAuthorObserver(ValueObserver<DataAuthorReview> observer) {
        unbindObserver(ReviewDb.AUTHOR, observer);
    }

    @Override
    public void unregisterPublishDateObserver(ValueObserver<DataDateReview> observer) {
        unbindObserver(ReviewDb.PUBLISH_DATE, observer);
    }

    @Override
    public void unregisterCoverObserver(ValueObserver<DataImage> observer) {
        unbindObserver(ReviewDb.COVER, observer);
    }

    @Override
    public void unregisterCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>> observer) {
        unbindObserver(ReviewDb.CRITERIA, observer);
    }

    @Override
    public void unregisterCommentsObserver(ValueObserver<IdableList<? extends DataComment>> observer) {
        unbindObserver(ReviewDb.COMMENTS, observer);
    }

    @Override
    public void unregisterFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer) {
        unbindObserver(ReviewDb.FACTS, observer);
    }

    @Override
    public void unregisterImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer) {
        unbindObserver(ReviewDb.IMAGES, observer);
    }

    @Override
    public void unregisterLocationsObserver(ValueObserver<IdableList<? extends DataLocation>> observer) {
        unbindObserver(ReviewDb.LOCATIONS, observer);
    }

    @Override
    public void unregisterTagsObserver(ValueObserver<List<String>> observer) {
        unbindObserver(ReviewDb.TAGS, observer);
    }

    @Override
    public void dereference(final DereferenceCallback callback) {
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
                observer.onValue(converter.convert(mEntry.getReviewId(), dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
