/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepoCollection<Key> extends RepoReadableBasic implements ReviewsRepoReadable {
    private final ReviewDereferencer mDereferencer;
    private Map<Key, RepoSubscriber> mRepoSubscribers;

    public RepoCollection(ReviewDereferencer dereferencer, SizeReferencer sizeReferencer) {
        super(dereferencer, sizeReferencer);
        mDereferencer = dereferencer;
        mRepoSubscribers = new HashMap<>();
    }


    public void add(Key id, ReviewsRepoReadable repo) {
        if (!mRepoSubscribers.containsKey(id)) {
            RepoSubscriber handler = new RepoSubscriber(repo);
            mRepoSubscribers.put(id, handler);
            if (getItemSubscribers().size() > 0) handler.subscribe();
        }
    }

    public void remove(Key id) {
        if (mRepoSubscribers.containsKey(id)) {
            mRepoSubscribers.remove(id).unsubscribeAndDelete();
        }
    }

    @Override
    protected void doDereferencing(DereferenceCallback<List<ReviewReference>> callback) {
        doDereferencing(mRepoSubscribers.values().iterator(), new ArrayList<ReviewReference>(),
                callback);
    }

    @Override
    protected void onBinding(ItemSubscriber<ReviewReference> subscriber) {
        if (getItemSubscribers().size() != 1) return;

        for (RepoSubscriber sub : mRepoSubscribers.values()) {
            if (!sub.isSubscribed()) sub.subscribe();
        }
    }

    @Override
    protected void onUnbinding(ItemSubscriber<ReviewReference> subscriber) {
        if (getItemSubscribers().size() != 0) return;

        for (RepoSubscriber sub : mRepoSubscribers.values()) {
            if (sub.isSubscribed()) sub.unsubscribe();
        }
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        new ReferenceFinder(reviewId, callback).execute();
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }

    Set<Key> getKeys() {
        return mRepoSubscribers.keySet();
    }

    private void doDereferencing(final Iterator<RepoSubscriber> handler, final List<ReviewReference>
            references, final DereferenceCallback<List<ReviewReference>> callback) {
        if (handler.hasNext()) {
            handler.next().getRepo().dereference(new DereferenceCallback<List<ReviewReference>>() {
                @Override
                public void onDereferenced(DataValue<List<ReviewReference>> value) {
                    if (value.hasValue()) references.addAll(value.getData());
                    doDereferencing(handler, references, callback);
                }
            });
        } else {
            callback.onDereferenced(new DataValue<>(references));
        }
    }

    private class ReferenceFinder {
        private ReviewId mId;
        private RepoCallback mCallback;
        private int mNumReturned;
        private boolean mDone;

        private ReferenceFinder(ReviewId id, RepoCallback callback) {
            mId = id;
            mCallback = callback;
        }

        private void execute() {
            mNumReturned = 0;
            mDone = false;
            boolean idFound = false;
            for (RepoSubscriber sub : mRepoSubscribers.values()) {
                if (mDone) break;
                if (sub.hasReviewId(mId)) {
                    idFound = true;
                    getReferenceFromSub(sub, true);
                    break;
                }
            }

            if (!idFound) {
                for (RepoSubscriber sub : mRepoSubscribers.values()) {
                    getReferenceFromSub(sub, false);
                }
            }
        }

        private void getReferenceFromSub(RepoSubscriber sub, final boolean doneOnResult) {
            sub.getRepo().getReference(mId, new RepoCallback() {
                @Override
                public void onRepoCallback(RepoResult result) {
                    parseResult(result, doneOnResult);
                }
            });
        }

        private void parseResult(RepoResult result, boolean doneOnResult) {
            ReviewReference reference = result.getReference();
            if (result.isReference()) {
                doCallback(reference);
            } else if (doneOnResult || ++mNumReturned >= mRepoSubscribers.size()) {
                doCallback(null);
            }
        }

        private void doCallback(@Nullable ReviewReference reference) {
            mDone = true;
            RepoResult result;
            if (reference != null) {
                result = new RepoResult(reference);
            } else {
                result = new RepoResult(CallbackMessage.error("Reference not found"));
            }
            mCallback.onRepoCallback(result);
        }
    }

    private class RepoSubscriber implements ReviewsSubscriber {
        private ReviewsRepoReadable mRepo;
        private List<ReviewId> mReviews;
        private boolean mSubscribed = false;

        private boolean mLocked = false;
        private int mUnsubscribeIndex = 0;

        private RepoSubscriber(ReviewsRepoReadable repo) {
            mRepo = repo;
            mReviews = new ArrayList<>();
        }

        @Override
        public void onItemAdded(ReviewReference item) {
            if (!mLocked) {
                mReviews.add(item.getReviewId());
                notifyOnAdd(item);
            }
        }

        @Override
        public void onItemRemoved(ReviewReference item) {
            if (!mLocked) {
                mReviews.remove(item.getReviewId());
                notifyOnRemove(item);
            }
        }

        @Override
        public void onInvalidated(CollectionReference<ReviewReference, ?, ?> reference) {
            mReviews.clear();
            notifyOnInvalidated(reference);
        }

        @Override
        public void onCollectionChanged(Collection<ReviewReference> newItems) {
            if (!mLocked) {
                mReviews.clear();
                for (ReviewReference reference : newItems)
                    mReviews.add(reference.getReviewId());
            }

            notifyOnChanged(newItems);
        }

        private boolean isSubscribed() {
            return mSubscribed;
        }

        private ReviewsRepoReadable getRepo() {
            return mRepo;
        }

        private void subscribe() {
            if (!mSubscribed) {
                mSubscribed = true;
                mRepo.subscribe(this);
            }
        }

        private void unsubscribe() {
            if (mSubscribed) {
                mSubscribed = false;
                mRepo.unsubscribe(this);
            }
        }

        private boolean hasReviewId(ReviewId id) {
            return mReviews.contains(id);
        }

        private void unsubscribeAndDelete() {
            if (!mLocked) {
                mLocked = true;
                mUnsubscribeIndex = 0;
                for (ReviewId id : mReviews) {
                    mRepo.getReference(id, new RepoCallback() {
                        @Override
                        public void onRepoCallback(RepoResult result) {
                            if (result.isReference()) notifyOnRemove(result.getReference());
                            if (++mUnsubscribeIndex == mReviews.size()) delete();
                        }
                    });
                }
            }
        }

        private void delete() {
            unsubscribe();
            mLocked = false;
            mReviews.clear();
            mRepo = null;
        }
    }
}
