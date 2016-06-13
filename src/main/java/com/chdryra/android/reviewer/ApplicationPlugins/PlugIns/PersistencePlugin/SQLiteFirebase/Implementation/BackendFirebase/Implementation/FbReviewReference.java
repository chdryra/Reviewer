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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
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
    private final Map<ValueObserver<?>, ValueEventListener> mBindings;

    public FbReviewReference(ReviewListEntry entry, Firebase reference) {
        mEntry = entry;
        mReference = reference;
        mBindings = new HashMap<>();
    }

    @Override
    public ReviewListEntry getBasicInfo() {
        return mEntry;
    }

    @Override
    public void registerSubjectObserver(ValueObserver<String> observer) {
        bindObserver(ReviewDb.SUBJECT, observer);
    }

    @Override
    public void registerRatingObserver(ValueObserver<Rating> observer) {
        bindObserver(ReviewDb.RATING, observer);
    }

    @Override
    public void registerAuthorObserver(ValueObserver<Author> observer) {
        bindObserver(ReviewDb.AUTHOR, observer);
    }

    @Override
    public void registerPublishDateObserver(ValueObserver<Long> observer) {
        bindObserver(ReviewDb.PUBLISH_DATE, observer);
    }

    @Override
    public void registerCoverObserver(ValueObserver<ImageData> observer) {
        bindObserver(ReviewDb.COVER, observer);
    }

    @Override
    public void registerCriteriaObserver(ValueObserver<List<Criterion>> observer) {
        bindObserver(ReviewDb.CRITERIA, observer);
    }

    @Override
    public void registerCommentsObserver(ValueObserver<List<Comment>> observer) {
        bindObserver(ReviewDb.COMMENTS, observer);
    }

    @Override
    public void registerFactsObserver(ValueObserver<List<Fact>> observer) {
        bindObserver(ReviewDb.FACTS, observer);
    }

    @Override
    public void registerImagesObserver(ValueObserver<List<ImageData>> observer) {
        bindObserver(ReviewDb.IMAGES, observer);
    }

    @Override
    public void registerLocationsObserver(ValueObserver<List<Location>> observer) {
        bindObserver(ReviewDb.LOCATIONS, observer);
    }

    @Override
    public void registerTagsObserver(ValueObserver<List<String>> observer) {
        bindObserver(ReviewDb.TAGS, observer);
    }

    @Override
    public void unregisterSubjectObserver(ValueObserver<String> observer) {
        unbindObserver(ReviewDb.SUBJECT, observer);
    }

    @Override
    public void unregisterRatingObserver(ValueObserver<Rating> observer) {
        unbindObserver(ReviewDb.RATING, observer);
    }

    @Override
    public void unregisterAuthorObserver(ValueObserver<Author> observer) {
        unbindObserver(ReviewDb.AUTHOR, observer);
    }

    @Override
    public void unregisterPublishDateObserver(ValueObserver<Long> observer) {
        unbindObserver(ReviewDb.PUBLISH_DATE, observer);
    }

    @Override
    public void unregisterCriteriaObserver(ValueObserver<List<Criterion>> observer) {
        unbindObserver(ReviewDb.CRITERIA, observer);
    }

    @Override
    public void unregisterCommentsObserver(ValueObserver<List<Comment>> observer) {
        unbindObserver(ReviewDb.COMMENTS, observer);
    }

    @Override
    public void unregisterFactsObserver(ValueObserver<List<Fact>> observer) {
        unbindObserver(ReviewDb.FACTS, observer);
    }

    @Override
    public void unregisterImagesObserver(ValueObserver<List<ImageData>> observer) {
        unbindObserver(ReviewDb.IMAGES, observer);
    }

    @Override
    public void unregisterLocationsObserver(ValueObserver<List<Location>> observer) {
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
                callback.onDereferenced(value, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onDereferenced(new ReviewDb(), FirebaseBackend.backendError(firebaseError));
            }
        });
    }

    private <T> void bindObserver(String child, ValueObserver<T> observer) {
        if (!mBindings.containsKey(observer)) {
            ValueEventListener listener = newListener(observer);
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
    private <T> ValueEventListener newListener(final ValueObserver<T> observer) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                observer.onValue(new DatumReviewId(mEntry.getReviewId()), (T) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
