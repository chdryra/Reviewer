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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryAuthored implements ReviewsRepository, ReviewsRepositoryObserver {
    private DataAuthor mAuthor;
    private ReviewsRepository mRepo;
    private ArrayList<ReviewsRepositoryObserver> mObservers;
    private FactoryReviewsRepository mRepoFactory;

    public ReviewsRepositoryAuthored(DataAuthor author, ReviewsRepository repo, FactoryReviewsRepository repoFactory) {
        mAuthor = author;
        mRepo = repo;
        mRepoFactory = repoFactory;
        mObservers = new ArrayList<>();
    }

    @Override
    public void getReview(ReviewId reviewId, RepositoryCallback callback) {
        mRepo.getReview(reviewId, callback);
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        if(author.equals(mAuthor)) {
            mRepo.getReviews(author, callback);
        } else {
            callback.onRepositoryCallback(new RepositoryResult(new ArrayList<Review>()));
        }
    }

    @Override
    public ReviewsRepository getReviews(DataAuthor author) {
        return author == mAuthor ? this : mRepoFactory.newEmptyRepository(getTagsManager());
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        mRepo.getReference(reviewId, callback);
    }

    @Override
    public void getReferences(DataAuthor author, RepositoryCallback callback) {
        if(author.equals(mAuthor)) {
            mRepo.getReviews(author, callback);
        } else {
            callback.onRepositoryCallback(new RepositoryResult(new ArrayList<ReviewReference>(), author));
        }
    }

    @Override
    public void getReviews(RepositoryCallback callback) {
        mRepo.getReviews(mAuthor, callback);
    }

    @Override
    public void getReferences(RepositoryCallback callback) {
        mRepo.getReferences(mAuthor, callback);
    }

    @Override
    public TagsManager getTagsManager() {
        return mRepo.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        if(mObservers.size() == 0) mRepo.registerObserver(this);
        if(!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        if(mObservers.contains(observer)) mObservers.remove(observer);
        if(mObservers.size() == 0) mRepo.unregisterObserver(this);
    }

    @Override
    public void onReviewAdded(Review review) {
        for(ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        for(ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
