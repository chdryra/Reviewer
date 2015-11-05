/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewPublisher;
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
    private ReviewsRepository mRepository;
    private ReviewTreeNode mNode;
    private FactoryReview mReviewFactory;
    private ReviewView mReviewView;

//Constructors
    public ReviewsRepositoryScreen(ReviewsRepository repository,
                                   FactoryReview reviewFactory,
                                   String title,
                                   PublishDate publishDate) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
        Author author = repository.getAuthor();
        ReviewPublisher publisher = new ReviewPublisher(author, publishDate);
        Review root = mReviewFactory.createReviewUser(publisher, title, 0f);
        mNode = reviewFactory.createReviewTreeNode(root, true);
        for (Review review : repository.getReviews()) {
            addReview(review);
        }
        repository.registerObserver(this);
    }

    public ReviewView createView(MdGvConverter converter,
                           FactoryChildListScreen childListFactory,
                           FactoryReviewViewAdapter adapterFactory,
                           ReviewViewAction.GridItemAction giAction,
                           ReviewViewAction.MenuAction menuAction) {
        mReviewView = childListFactory.createView(mNode, converter, mRepository.getTagsManager(),
                adapterFactory, giAction, menuAction);
        return mReviewView;
    }

    //private methods
    private void addReview(Review review) {
        mNode.addChild(mReviewFactory.createReviewTreeNode(review, false));
    }

    private void removeReview(ReviewId id) {
        mNode.removeChild(id);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId id) {
        removeReview(id);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }
}
