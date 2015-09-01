package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListScreen {
    private ReviewView mReviewView;
    private AdapterReviewNode<GvReviewOverviewList.GvReviewOverview> mAdapter;

    private ReviewListScreen(ReviewNode node, ReviewViewAction.GridItemAction
            giAction, ReviewViewAction.MenuAction menuAction) {
        mAdapter = FactoryReviewViewAdapter.newChildListAdapter(node);

        mReviewView = new ReviewView(mAdapter);
        mReviewView.setAction(giAction);
        mReviewView.setAction(menuAction);
        mReviewView.setAction(new RatingBarAction());

        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        ReviewViewParams params = mReviewView.getParams();

        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
    }

    public static ReviewView newScreen(ReviewNode node, ReviewViewAction.GridItemAction giAction,
                                       ReviewViewAction.MenuAction menuAction) {
        ReviewListScreen screen = new ReviewListScreen(node, giAction, menuAction);
        return screen.getReviewView();
    }

    private ReviewView getReviewView() {
        return mReviewView;
    }

    private class RatingBarAction extends ReviewViewAction.RatingBarAction {
        private final int REQUEST_CODE = RequestCodeGenerator.getCode
                ("ReviewListScreen..RatingBarAction");

        @Override
        public void onClick(View v) {
            ReviewView ui = ReviewDataScreen.newScreen(mReviewView.getActivity(),
                    mAdapter.getTreeDataAdapter());
            LauncherUi.launch(ui, getReviewView().getParent(), REQUEST_CODE, ui.getLaunchTag(), new
                    Bundle());
        }
    }
}
