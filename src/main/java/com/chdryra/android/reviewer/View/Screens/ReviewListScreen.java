package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListScreen {
    private ReviewView mReviewView;

    private ReviewListScreen(Context context, ReviewNode node, ReviewsRepository repository,
                             ReviewViewAction.GridItemAction giAction,
                             ReviewViewAction.MenuAction menuAction) {
        ReviewViewAdapter<? extends GvData> adapter =
                FactoryReviewViewAdapter.newChildListAdapter(context, node, repository);

        ReviewViewActionCollection actions = new ReviewViewActionCollection();
        actions.setAction(giAction);
        if(menuAction != null) actions.setAction(menuAction);
        actions.setAction(new RbExpandGrid());

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        ReviewViewPerspective perspective = new ReviewViewPerspective(adapter, params, actions);
        mReviewView = new ReviewView(perspective);
    }

    public static ReviewView newScreen(Context context, ReviewNode node, ReviewsRepository repository,
                                       ReviewViewAction.GridItemAction giAction,
                                       ReviewViewAction.MenuAction menuAction) {
        return new ReviewListScreen(context, node, repository, giAction, menuAction)
                .getReviewView();
    }

    public static ReviewView newScreen(Context context, ReviewNode node, ReviewsRepository repository) {
        return new ReviewListScreen(context, node, repository, new GiLaunchReviewDataScreen(),
                null).getReviewView();
    }

    public static ReviewView newScreen(Context context, Review review, ReviewsRepository repository) {
        return newScreen(context, FactoryReview.createMetaReview(review), repository);
    }

    private ReviewView getReviewView() {
        return mReviewView;
    }
}
