package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements
        DialogAlertFragment.DialogAlertListener,
        ReviewsProviderObserver{
    private ReviewNodeComponent mNode;
    private FactoryReviews mReviewsFactory;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemDeleteRequester mGridItem;

    public FeedScreen(ReviewsFeed feed,
                      String title,
                      FactoryReviews reviewsFactory) {
        Review root = reviewsFactory.createUserReview(title, 0f);
        mReviewsFactory = reviewsFactory;
        mNode = mReviewsFactory.createReviewNodeComponent(root, true);
        for (Review review : feed.getReviews()) {
            mNode.addChild(mReviewsFactory.createReviewNodeComponent(review, false));
        }

        feed.registerObserver(this);
    }

    public ReviewView<GvReviewOverview> createView(FactoryReviewViewLaunchable launchableFactory,
                                 FactoryReviewViewAdapter adapterFactory,
                                 SubjectAction<GvReviewOverview> subject,
                                 RatingBarAction<GvReviewOverview> ratingBar,
                                 BannerButtonAction<GvReviewOverview> bannerButtonAction,
                                 GridItemDeleteRequester gridItem,
                                 MenuAction<GvReviewOverview> menuAction
                                 ) {
        mGridItem = gridItem;
        ReviewViewActions<GvReviewOverview> actions
                = new ReviewViewActions<>(subject, ratingBar, bannerButtonAction, gridItem, menuAction);
        mReviewView = launchableFactory.newReviewsListScreen(mNode, adapterFactory, actions);
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
    private void addReviewToNode(Review review) {
        mNode.addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }

    private void removeReviewFromNode(String reviewId) {
        mNode.removeChild(reviewId);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReviewToNode(review);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(String reviewId) {
        removeReviewFromNode(reviewId);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }
}
