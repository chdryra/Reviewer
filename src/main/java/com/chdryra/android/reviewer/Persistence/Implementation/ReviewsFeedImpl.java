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
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsFeedImpl implements ReviewsFeed, ReviewsRepositoryObserver {
    private DataAuthor mAuthor;
    private ReviewsRepository mRepo;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public ReviewsFeedImpl(DataAuthor author, ReviewsRepository repo) {
        mAuthor = author;
        mRepo = repo;
        mObservers = new ArrayList<>();
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public void getReview(ReviewId id, RepositoryCallback callback) {
        mRepo.getReview(id, callback);
    }

    @Override
    public void getReviews(RepositoryCallback callback) {
        mRepo.getReviews(mAuthor, callback);
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        mRepo.getReviews(author, callback);
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
