package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements DialogAlertFragment.DialogAlertListener, ReviewsProviderObserver{
    private FactoryReviewViewAdapter mAdapterFactory;
    private ReviewNodeComponent mNode;
    private FactoryReviewNodeComponent mNodeFactory;
    private ReviewView mReviewView;
    private GridItemFeedScreen mGridItem;
    private MenuAction<GvReviewOverviewList.GvReviewOverview> mMenuAction;

    public FeedScreen(ReviewsProvider feed,
                      String title,
                      FactoryReviewViewAdapter adapterFactory,
                      FactoryReviews reviewFactory,
                      GridItemFeedScreen gridItem,
                      MenuAction<GvReviewOverviewList.GvReviewOverview> menuAction) {
        Review root = reviewFactory.createUserReview(title, 0f);

        mNodeFactory = reviewFactory.getComponentFactory();
        mNode = mNodeFactory.createReviewNodeComponent(root, true);
        for (Review review : feed.getReviews()) {
            addReview(review);
        }
        feed.registerObserver(this);

        mAdapterFactory = adapterFactory;
        mGridItem = gridItem;
        mMenuAction = menuAction;
    }

    public ReviewView createView(BuilderChildListView childListBuilder) {
        mReviewView = childListBuilder.buildView(mNode, mAdapterFactory, mGridItem, mMenuAction);
        return mReviewView;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
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
