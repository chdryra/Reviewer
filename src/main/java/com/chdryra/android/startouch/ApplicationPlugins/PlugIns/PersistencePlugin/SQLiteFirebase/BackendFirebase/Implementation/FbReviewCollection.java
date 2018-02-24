/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.CollectionReferenceBasic;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewCollectionDeleter;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FbReviewCollection extends CollectionReferenceBasic<ReviewReference,
        List<ReviewReference>, Size> implements ReviewCollection {
    private final FbReviewIdList mEntryList;
    private final ReviewDereferencer mDereferencer;
    private final Map<ItemSubscriber<ReviewReference>, EntrySubscriber> mSubscribers;

    public FbReviewCollection(FbReviewIdList entryList, ReviewDereferencer dereferencer) {
        mEntryList = entryList;
        mDereferencer = dereferencer;
        mSubscribers = new HashMap<>();
    }

    @Nullable
    @Override
    protected List<ReviewReference> getNullValue() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return mEntryList.getName();
    }

    @Override
    public void addEntry(ReviewId reviewId, Callback callback) {
        mEntryList.update(reviewId, DbUpdater.UpdateType.INSERT_OR_UPDATE, callback);
    }

    @Override
    public void removeEntry(ReviewId reviewId, Callback callback) {
        mEntryList.update(reviewId, DbUpdater.UpdateType.DELETE, callback);
    }

    @Override
    public void hasEntry(ReviewId reviewId, final Callback callback) {
        mEntryList.hasEntry(reviewId, callback);
    }

    @Override
    public DataReference<Size> getSize() {
        return mEntryList.getSize();
    }

    @Override
    public void getReference(final ReviewId reviewId, final RepoCallback callback) {
        mEntryList.getReference(reviewId, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReference()) {
                    callback.onRepoCallback(result);
                } else if (result.getMessage().equals(FbReviewIdList.NO_REFERENCE)) {
                    deleteFromPlaylistIfNecessary(result, reviewId, callback);
                }
            }
        });
    }

    @Override
    public void registerListener(InvalidationListener listener) {
        mEntryList.registerListener(listener);
    }

    @Override
    public void unregisterListener(InvalidationListener listener) {
        mEntryList.unregisterListener(listener);
    }

    @Override
    public boolean isValidReference() {
        return mEntryList.isValidReference();
    }

    @Override
    public void invalidate() {
        mEntryList.invalidate();
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<List<ReviewReference>> callback) {
        mEntryList.dereference(new DereferenceCallback<List<ReviewId>>() {
            @Override
            public void onDereferenced(DataValue<List<ReviewId>> value) {
                if (value.hasValue()) {
                    new Dereferencer(value.getData(), callback).dereference();
                } else {
                    callback.onDereferenced(new DataValue<List<ReviewReference>>(CallbackMessage
                            .error("No entries")));
                }
            }
        });
    }

    @Override
    protected void onBinding(ItemSubscriber<ReviewReference> subscriber) {
        EntrySubscriber entrySub = new EntrySubscriber(subscriber);
        mSubscribers.put(subscriber, entrySub);
        mEntryList.subscribe(entrySub);
    }

    @Override
    protected void onUnbinding(ItemSubscriber<ReviewReference> subscriber) {
        mEntryList.unsubscribe(mSubscribers.remove(subscriber));
    }

    private void deleteFromPlaylistIfNecessary(final RepoResult result,
                                               final ReviewId id,
                                               final RepoCallback callback) {
        if (result.isError() && result.getMessage().equals(FbReviewIdList.NO_REFERENCE)) {
            ReviewCollectionDeleter deleter
                    = new ReviewCollectionDeleter(id, FbReviewCollection.this, new
                    ReviewCollectionDeleter.DeleteCallback() {
                        @Override
                        public void onDeletedFromCollection(String name,
                                                            ReviewId reviewId,
                                                            CallbackMessage message) {
                            callback.onRepoCallback(result);
                        }
                    });
            //deleter.delete();
        } else {
            callback.onRepoCallback(result);
        }
    }

    private class EntrySubscriber implements ItemSubscriber<ReviewId> {
        private final ItemSubscriber<ReviewReference> mSubscriber;

        private EntrySubscriber(ItemSubscriber<ReviewReference> subscriber) {
            mSubscriber = subscriber;
        }

        @Override
        public void onItemAdded(ReviewId item) {
            getReference(item, new RepoCallback() {
                @Override
                public void onRepoCallback(RepoResult result) {
                    if (result.isReference()) mSubscriber.onItemAdded(result.getReference());
                }
            });
        }

        @Override
        public void onItemRemoved(ReviewId item) {
            getReference(item, new RepoCallback() {
                @Override
                public void onRepoCallback(RepoResult result) {
                    if (result.isReference()) {
                        mSubscriber.onItemRemoved(result.getReference());
                    }
                }
            });
        }

        @Override
        public void onCollectionChanged(Collection<ReviewId> newItems) {
            FbReviewCollection.this.dereference(new DereferenceCallback<List<ReviewReference>>() {
                @Override
                public void onDereferenced(DataValue<List<ReviewReference>> value) {
                    if (value.hasValue()) mSubscriber.onCollectionChanged(value.getData());
                }
            });
        }

        @Override
        public void onInvalidated(CollectionReference<ReviewId, ?, ?> reference) {
            mSubscriber.onInvalidated(FbReviewCollection.this);
        }
    }

    private class Dereferencer implements RepoCallback {
        private final List<ReviewId> mEntries;
        private final DereferenceCallback<List<ReviewReference>> mCallback;
        private final List<ReviewReference> mReferences = new ArrayList<>();

        private int mCallbacks = 0;

        public Dereferencer(List<ReviewId> entries, DereferenceCallback<List<ReviewReference>>
                callback) {
            mEntries = entries;
            mCallback = callback;
        }

        @Override
        public void onRepoCallback(RepoResult result) {
            if (result.isReference()) {
                mReferences.add(result.getReference());
            }

            if (++mCallbacks == mEntries.size()) {
                mCallback.onDereferenced(new DataValue<>(mReferences));
            }
        }

        private void dereference() {
            for (ReviewId reviewId : mEntries) {
                getReference(reviewId, this);
            }
        }
    }
}
