/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepoCollection<Key> implements ReviewsRepoReadable {
    private final ReviewDereferencer mDereferencer;
    private Map<Key, RepoHandler> mRepoHandlers;
    private List<ReviewsSubscriber> mSubscribers;

    public RepoCollection(ReviewDereferencer dereferencer) {
        mDereferencer = dereferencer;
        mRepoHandlers = new HashMap<>();
        mSubscribers = new ArrayList<>();
    }

    public void add(Key id, ReviewsRepoReadable repo) {
        if (!mRepoHandlers.containsKey(id)) {
            RepoHandler handler = new RepoHandler(id, repo);
            mRepoHandlers.put(id, handler);
            if (mSubscribers.size() > 0) handler.subscribe();
        }
    }

    public void remove(Key id) {
        if (mRepoHandlers.containsKey(id)) {
            mRepoHandlers.remove(id).unsubscribeAndDelete();
        }
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        if (!mSubscribers.contains(subscriber)) {
            mSubscribers.add(subscriber);
            for (RepoHandler sub : mRepoHandlers.values()) {
                if (mSubscribers.size() == 1 && !sub.isSubscribed()) {
                    sub.subscribe();
                } else {
                    sub.notifyNewSubscriber(subscriber);
                }
            }
        }
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        if (mSubscribers.contains(subscriber)) {
            mSubscribers.remove(subscriber);
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
        return mRepoHandlers.keySet();
    }

    private void notifyOnAdd(ReviewReference reference) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewAdded(reference);
        }
    }

    private void notifyOnRemove(ReviewReference reference) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewRemoved(reference);
        }
    }

    private void notifyOnInvalidated(ReviewId reviewId) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReferenceInvalidated(reviewId);
        }
    }

    private void notifyOnEdit(ReviewReference reference) {
        for (ReviewsSubscriber subscriber : mSubscribers) {
            subscriber.onReviewEdited(reference);
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
            for (RepoHandler sub : mRepoHandlers.values()) {
                if (mDone) break;
                if (sub.hasReviewId(mId)) {
                    idFound = true;
                    getReferenceFromSub(sub, true);
                    break;
                }
            }

            if (!idFound) {
                for (RepoHandler sub : mRepoHandlers.values()) {
                    getReferenceFromSub(sub, false);
                }
            }
        }

        private void getReferenceFromSub(RepoHandler sub, final boolean doneOnResult) {
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
            } else if (doneOnResult || ++mNumReturned >= mRepoHandlers.size()) {
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

    private class RepoHandler implements ReviewsSubscriber {
        private Key mRepoId;
        private ReviewsRepoReadable mRepo;
        private List<ReviewId> mReviews;
        private boolean mSubscribed = false;

        private boolean mLocked = false;
        private int mUnsubscribeIndex = 0;

        private RepoHandler(Key repoId, ReviewsRepoReadable repo) {
            mRepoId = repoId;
            mRepo = repo;
            mReviews = new ArrayList<>();
        }

        @Override
        public String getSubscriberId() {
            return mRepoId.toString();
        }

        @Override
        public void onReviewAdded(ReviewReference reference) {
            if (!mLocked) {
                mReviews.add(reference.getReviewId());
                notifyOnAdd(reference);
            }
        }

        @Override
        public void onReviewEdited(ReviewReference reference) {
            if (!mLocked) notifyOnEdit(reference);
        }

        @Override
        public void onReviewRemoved(ReviewReference reference) {
            if (!mLocked) {
                mReviews.remove(reference.getReviewId());
                notifyOnRemove(reference);
            }
        }

        @Override
        public void onReferenceInvalidated(ReviewId reviewId) {
            if (!mLocked) {
                mReviews.remove(reviewId);
                notifyOnInvalidated(reviewId);
            }
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

        private boolean hasReviewId(ReviewId id) {
            return mReviews.contains(id);
        }

        private void notifyNewSubscriber(final ReviewsSubscriber subscriber) {
            for (ReviewId id : mReviews) {
                mRepo.getReference(id, new RepoCallback() {
                    @Override
                    public void onRepoCallback(RepoResult result) {
                        if (!result.isReference()) subscriber.onReviewAdded(result.getReference());
                    }
                });
            }
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
            mRepo.unsubscribe(this);
            mLocked = false;
            mReviews.clear();
            mRepo = null;
        }
    }
}
