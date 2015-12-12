package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemDeleteRequester;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements
        DialogAlertFragment.DialogAlertListener,
        ReviewsRepositoryObserver,
        GridItemDeleteRequester.DeleteRequestListener{
    private ReviewNodeComponent mNode;
    private FactoryReviews mReviewsFactory;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemDeleteRequester mGridItem;
    private DeleteRequestListener mListener;

    public interface DeleteRequestListener {
        void onDeleteRequested(ReviewId reviewId);
    }

    public FeedScreen(ReviewsFeed feed,
                      String title,
                      FactoryReviews reviewsFactory,
                      DeleteRequestListener listener) {
        mReviewsFactory = reviewsFactory;
        mNode = mReviewsFactory.createMetaReviewMutable(feed.getReviews(), title);
        feed.registerObserver(this);
        mListener = listener;
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

    private void addReviewToNode(Review review) {
        mNode.addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }

    private void removeReviewFromNode(ReviewId reviewId) {
        mNode.removeChild(reviewId);
    }

    @Override
    public void onReviewAdded(Review review) {
        addReviewToNode(review);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        removeReviewFromNode(reviewId);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onDeleteRequested(ReviewId reviewId) {
        mListener.onDeleteRequested(reviewId);
    }
}
