/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProviderObserver;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryScreen implements ReviewsProviderObserver {
    private ReviewTreeNode mNode;
    private ReviewView mReviewView;

    private ReviewsRepositoryScreen(Context context, ReviewsRepository repository, String title,
                                    ReviewViewAction.GridItemAction giAction,
                                    ReviewViewAction.MenuAction menuAction) {
        Author author = repository.getAuthor();
        ReviewPublisher publisher = new ReviewPublisher(author, PublishDate.now());
        Review root = FactoryReview.createReviewUser(publisher, title, 0f);
        mNode = FactoryReview.createReviewTreeNode(root, true);
        for (Review review : repository.getReviews()) {
            addReview(review);
        }

        mReviewView = ChildListScreen.newScreen(context, mNode, repository, giAction, menuAction);

        repository.registerObserver(this);
    }

    //Static methods
    public static ReviewView newScreen(Context context, ReviewsRepository repo, String title,
                                       ReviewViewAction.GridItemAction giAction,
                                       ReviewViewAction.MenuAction menuAction) {
        return new ReviewsRepositoryScreen(context, repo, title, giAction, menuAction).getReviewView();
    }

    //private methods
    private ReviewView getReviewView() {
        return mReviewView;
    }

    private void addReview(Review review) {
        mNode.addChild(FactoryReview.createReviewTreeNode(review, false));
    }

    private void removeReview(ReviewId id) {
        mNode.removeChild(id);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
        mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId id) {
        removeReview(id);
        mReviewView.onGridDataChanged();
    }
}
