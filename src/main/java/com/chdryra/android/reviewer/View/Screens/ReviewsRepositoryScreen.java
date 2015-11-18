/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvReviewConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryScreen implements ReviewsProviderObserver {
    private ReviewNodeComponent mNode;
    private FactoryReviewNodeComponent mNodeFactory;
    private ReviewView mReviewView;

//Constructors
    public ReviewsRepositoryScreen(ReviewsProvider repository,
                                   FactoryReviews reviewFactory,
                                   String title) {
        Review root = reviewFactory.createUserReview(title, 0f);

        mNodeFactory = reviewFactory.getComponentFactory();
        mNode = mNodeFactory.createReviewNodeComponent(root, true);
        for (Review review : repository.getReviews()) {
            addReview(review);
        }

        repository.registerObserver(this);
    }

    public ReviewView createView(GvReviewConverter<GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> reviewConverter,
                                 GvImageConverter imageConverter,
                           BuilderChildListScreen childListFactory,
                           FactoryReviewViewAdapter adapterFactory,
                           GridItemAction giAction,
                           MenuAction menuAction) {
        mReviewView = childListFactory.createView(mNode, reviewConverter, imageConverter,
                adapterFactory, giAction, menuAction);
        return mReviewView;
    }

    //private methods
    private void addReview(Review review) {
        mNode.addChild(mNodeFactory.createReviewNodeComponent(review, false));
    }

    private void removeReview(String reviewId) {
        mNode.removeChild(reviewId);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(String reviewId) {
        removeReview(reviewId);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }
}
