package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListScreen {
    private ReviewView mReviewView;
    private AdapterReviewNode<GvReviewOverviewList.GvReviewOverview> mAdapter;

    private ReviewListScreen(ReviewNode node,
                             ReviewViewAction.GridItemAction giAction,
                             ReviewViewAction.MenuAction menuAction) {
        mAdapter = FactoryReviewViewAdapter.newChildListAdapter(node);

        mReviewView = new ReviewView(mAdapter);
        mReviewView.setAction(giAction);
        mReviewView.setAction(menuAction);
        mReviewView.setAction(new RbLaunchMetaReviewScreen());

        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        ReviewViewParams params = mReviewView.getParams();

        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
    }

    public static ReviewView newScreen(ReviewNode node,
                                       ReviewViewAction.GridItemAction giAction,
                                       ReviewViewAction.MenuAction menuAction) {
        return new ReviewListScreen(node, giAction, menuAction).getReviewView();
    }

    public static ReviewView newScreen(ReviewNode node) {
        return new ReviewListScreen(node, new GiLaunchReviewDataScreen(), null).getReviewView();
    }

    private ReviewView getReviewView() {
        return mReviewView;
    }
}
