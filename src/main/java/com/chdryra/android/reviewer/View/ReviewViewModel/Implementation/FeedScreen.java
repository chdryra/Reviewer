package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements DialogAlertFragment.DialogAlertListener, ReviewsProviderObserver{
    private ReviewNodeComponent mNode;
    private FactoryReviewNodeComponent mNodeFactory;
    private ReviewView mReviewView;
    private GridItemFeedScreen mGridItem;

    public FeedScreen(ReviewsProvider feed,
                      String title,
                      FactoryReviews reviewFactory) {
        Review root = reviewFactory.createUserReview(title, 0f);
        mNodeFactory = reviewFactory.getComponentFactory();
        mNode = mNodeFactory.createReviewNodeComponent(root, true);
        for (Review review : feed.getReviews()) {
            mNode.addChild(mNodeFactory.createReviewNodeComponent(review, false));
        }

        feed.registerObserver(this);
    }

    public ReviewView createView(BuilderChildListView childListBuilder,
                                 FactoryReviewViewAdapter adapterFactory,
                                 SubjectAction<GvReviewOverviewList.GvReviewOverview> subject,
                                 RatingBarAction<GvReviewOverviewList.GvReviewOverview> ratingBar,
                                 BannerButtonAction<GvReviewOverviewList.GvReviewOverview> bannerButtonAction,
                                 GridItemFeedScreen gridItem,
                                 MenuAction<GvReviewOverviewList.GvReviewOverview> menuAction
                                 ) {
        mGridItem = gridItem;
        ReviewViewActions<GvReviewOverviewList.GvReviewOverview> actions
                = new ReviewViewActions<>(subject, ratingBar, bannerButtonAction, gridItem, menuAction);
        mReviewView = childListBuilder.buildView(mNode, adapterFactory, actions);
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
