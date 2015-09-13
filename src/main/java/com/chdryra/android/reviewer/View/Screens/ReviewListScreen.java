package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListScreen {
    private ReviewView mReviewView;

    private ReviewListScreen(Context context, ReviewNode node,
                             ReviewViewAction.GridItemAction giAction,
                             ReviewViewAction.MenuAction menuAction) {
        mReviewView = new ReviewView(FactoryReviewViewAdapter.newChildListAdapter(context, node));
        mReviewView.setAction(giAction);
        mReviewView.setAction(menuAction);
        mReviewView.setAction(new RbLaunchMetaReviewScreen());

        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        ReviewViewParams params = mReviewView.getParams();

        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
    }

    public static ReviewView newScreen(Context context, ReviewNode node,
                                       ReviewViewAction.GridItemAction giAction,
                                       ReviewViewAction.MenuAction menuAction) {
        return new ReviewListScreen(context, node, giAction, menuAction).getReviewView();
    }

    public static ReviewView newScreen(Context context, ReviewNode node) {
        return new ReviewListScreen(context, node, new GiLaunchReviewDataScreen(), null)
                .getReviewView();
    }

    private ReviewView getReviewView() {
        return mReviewView;
    }
}
