package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeProvider implements ReviewsProvider, ReviewsProviderObserver {
    private ReviewTreeNode mNode;
    private ArrayList<ReviewsProviderObserver> mObservers;
    private TagsManager mTagManager;

    public ReviewNodeProvider(ReviewsProvider provider, Author nodeOwner, String title) {
        mObservers = new ArrayList<>();

        ReviewPublisher publisher = new ReviewPublisher(nodeOwner, PublishDate.now());
        Review root = FactoryReview.createReviewUser(publisher, title, 0f);
        mNode = FactoryReview.createReviewTreeNode(root, true);
        mTagManager = provider.getTagsManager();
        provider.registerObserver(this);
        for (Review review : provider.getReviews()) {
            addReview(review);
        }
    }

    public ReviewNode getReviewNode() {
        return mNode;
    }

    @Override
    public Review getReview(ReviewId id) {
        return mNode.getChildren().get(id).getReview();
    }

    @Override
    public IdableList<Review> getReviews() {
        IdableList<Review> reviews = new IdableList<>();
        for (ReviewNode node : mNode.getChildren()) {
            reviews.add(node.getReview());
        }

        return reviews;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagManager;
    }

    @Override
    public void registerObserver(ReviewsProviderObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
    }

    @Override
    public void onReviewRemoved(ReviewId id) {
        removeReview(id);
    }

    private void addReview(Review review) {
        ReviewTreeNode node = FactoryReview.createReviewTreeNode(review, false);
        mNode.addChild(node);
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void removeReview(ReviewId id) {
        mNode.removeChild(id);
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewRemoved(id);
        }
    }
}

