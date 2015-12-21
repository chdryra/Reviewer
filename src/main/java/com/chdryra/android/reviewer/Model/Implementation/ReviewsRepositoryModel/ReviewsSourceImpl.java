package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceImpl implements ReviewsSource {
    private ReviewsRepository mRepository;
    private FactoryReviews mReviewFactory;
    private TreeFlattener mTreeFlattener;

    //Constructors
    public ReviewsSourceImpl(ReviewsRepository repository,
                             FactoryReviews reviewFactory,
                             TreeFlattener treeFlattener) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
        mTreeFlattener = treeFlattener;
    }

    @Override
    public Review getReview(VerboseDataReview datum) {
        Review review = getReview(datum.getReviewId());
        if (review == null && datum.hasElements()) {
            review = getMetaReview((VerboseIdableItems<? extends VerboseDataReview>) datum,
                    datum.getStringSummary());
        }

        return review;
    }

    @Nullable
    @Override
    public ReviewNode asMetaReview(ReviewId id) {
        Review review = getReview(id);
        return review != null ? mReviewFactory.createMetaReview(review) : null;
    }

    @Override
    public ReviewNode getMetaReview(VerboseIdableItems data, String subject) {
        IdableItems<Review> reviews = new IdableDataList<>(null);
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i).getReviewId()));
        }

        return mReviewFactory.createMetaReview(reviews, subject);
    }

    @Override
    public ReviewNode asMetaReview(VerboseDataReview datum, String subject) {
        if (datum.isVerboseCollection()) {
            return getMetaReview((VerboseIdableItems<? extends VerboseDataReview>) datum,
                    subject);
        }
        Review review = getReview(datum);
        return review != null ? mReviewFactory.createMetaReview(review) : null;
    }

    @Override
    public ReviewNode getFlattenedMetaReview(VerboseIdableItems data, String subject) {
        ReviewNode node = getMetaReview(data, subject);
        if(node == null) return null;
        return mReviewFactory.createMetaReview(mTreeFlattener.flatten(node), subject);
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        return mRepository.getReview(reviewId);
    }

    @Override
    public Iterable<Review> getReviews() {
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
}
