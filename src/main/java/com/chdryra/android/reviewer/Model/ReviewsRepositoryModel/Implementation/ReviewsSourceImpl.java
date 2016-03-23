/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceImpl implements ReviewsSource {
    private ReviewsRepository mRepository;
    private FactoryReviews mReviewFactory;

    public ReviewsSourceImpl(ReviewsRepository repository,
                             FactoryReviews reviewFactory) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
    }

    @Override
    public ReviewNode asMetaReview(ReviewId id) {
        ReviewNode review = asMetaReviewNullable(id);
        return review != null ? review : mReviewFactory.getNullNode();
    }

    @Nullable
    private ReviewNode asMetaReviewNullable(ReviewId id) {
        Review review = getReviewNullable(id);
        return review != null ? mReviewFactory.createMetaReview(review) : null;
    }

    @Override
    public ReviewNode asMetaReview(VerboseDataReview datum, String subjectIfMetaOfItems) {
        ReviewNode node = asMetaReviewNullable(datum.getReviewId());

        if (node == null && datum.isVerboseCollection()) {
            node = getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subjectIfMetaOfItems);
        }

        return node != null ? node : mReviewFactory.getNullNode();
    }

    @Override
    public ReviewNode getMetaReview(VerboseIdableCollection data, String subject) {
        IdableCollection<Review> reviews = getUniqueReviews(data);
        return reviews.size() > 0 ?
                mReviewFactory.createMetaReview(reviews, subject) : mReviewFactory.getNullNode();
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        Review review = getReviewNullable(reviewId);
        return review != null ? review : mReviewFactory.getNullReview();
    }

    @Nullable
    private Review getReviewNullable(ReviewId reviewId) {
        return mRepository.getReview(reviewId);
    }

    @Override
    public Collection<Review> getReviews() {
        return mRepository.getReviews();
    }

    @Override
    public TagsManager getTagsManager() {
        return mRepository.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mRepository.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mRepository.unregisterObserver(observer);
    }

    @NonNull
    private IdableCollection<Review> getUniqueReviews(VerboseIdableCollection data) {
        IdableCollection<Review> reviews = new IdableDataCollection<>();
        ArrayList<ReviewId> reviewsAdded = new ArrayList<>();
        for (int i = 0; i < data.size(); ++i) {
            ReviewId reviewId = data.getItem(i).getReviewId();
            Review review = getReviewNullable(reviewId);
            if (review != null && !reviewsAdded.contains(reviewId)) {
                reviews.add(review);
                reviewsAdded.add(reviewId);
            }
        }
        return reviews;
    }
}
