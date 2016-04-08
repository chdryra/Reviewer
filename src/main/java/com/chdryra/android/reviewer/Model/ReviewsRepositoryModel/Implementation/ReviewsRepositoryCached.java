/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Utils.ReviewsCache;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryCached<T extends ReviewsRepository>
        implements ReviewsRepository, ReviewsRepositoryObserver {
    private ReviewsCache mCache;
    private T mArchive;

    public ReviewsRepositoryCached(ReviewsCache cache, T archive) {
        mCache = cache;
        mArchive = archive;
        mArchive.registerObserver(this);
    }

    @Override
    public void getReview(ReviewId id, CallbackRepository callback) {
        if(mCache.contains(id)) {
            callback.onFetchedFromRepo(mCache.get(id), CallbackMessage.ok("Fetched"));
        } else {
            mArchive.getReview(id, new ArchiveCallBack(callback));
        }
    }

    @Override
    public void getReviews(CallbackRepository callback) {
        mArchive.getReviews(new ArchiveCallBack(callback));
    }

    @Override
    public TagsManager getTagsManager() {
        return mArchive.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mArchive.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mArchive.unregisterObserver(observer);
    }

    protected ReviewsCache getCache() {
        return mCache;
    }

    protected T getArchive(){
        return mArchive;
    }

    private class ArchiveCallBack implements CallbackRepository {
        private CallbackRepository mCallback;

        public ArchiveCallBack(CallbackRepository callback) {
            mCallback = callback;
        }

        @Override
        public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
            if(review != null) mCache.add(review);
            mCallback.onFetchedFromRepo(review, result);
        }

        @Override
        public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
            for(Review review : reviews) {
                mCache.add(review);
            }

            mCallback.onFetchedFromRepo(reviews, result);
        }
    }

    @Override
    public void onReviewAdded(Review review) {

    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        mCache.remove(reviewId);
    }
}
