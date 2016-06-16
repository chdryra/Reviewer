/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryCached<T extends ReviewsRepository>
        implements ReviewsRepository, ReviewsRepositoryObserver {
    private ReviewsCache mCache;
    private T mArchive;
    private FactoryReviewsRepository mRepoFactory;

    public ReviewsRepositoryCached(ReviewsCache cache, T archive, FactoryReviewsRepository repoFactory) {
        mCache = cache;
        mArchive = archive;
        mRepoFactory = repoFactory;
        mArchive.registerObserver(this);
    }

    @Override
    public void getReview(ReviewId reviewId, RepositoryCallback callback) {
        if(mCache.contains(reviewId)) {
            callback.onRepositoryCallback(new RepositoryResult(mCache.get(reviewId)));
        } else {
            mArchive.getReview(reviewId, new ArchiveCallBack(callback));
        }
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        mArchive.getReviews(author, new ArchiveCallBack(callback));
    }

    @Override
    public ReviewsRepository getReviews(DataAuthor author) {
        return mRepoFactory.newAuthoredRepo(author, this);
    }

    @Override
    public void getReviews(RepositoryCallback callback) {
        mArchive.getReviews(callback);
    }

    @Override
    public void getReferences(RepositoryCallback callback) {
        mArchive.getReferences(callback);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        mArchive.getReference(reviewId, callback);
    }

    @Override
    public void getReferences(DataAuthor author, RepositoryCallback callback) {
        mArchive.getReferences(author, callback);
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

    private class ArchiveCallBack implements RepositoryCallback {
        private RepositoryCallback mCallback;

        public ArchiveCallBack(RepositoryCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onRepositoryCallback(RepositoryResult result) {
            if(!result.isError()) {
                if(result.isReview()) {
                    Review review = result.getReview();
                    if(review != null) mCache.add(review);
                } else if(result.isReviewCollection()) {
                    for(Review review : result.getReviews()) {
                        mCache.add(review);
                    }
                }
            }

            mCallback.onRepositoryCallback(result);
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
