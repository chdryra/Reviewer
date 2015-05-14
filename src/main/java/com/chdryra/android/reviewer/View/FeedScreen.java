/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen {
    private static final int REQUEST_CODE = 1976;
    private Context           mContext;
    private ReviewView        mReviewView;
    private ReviewViewAdapter mAdapter;

    private FeedScreen(Context context) {
        mContext = context;

        mAdapter = ReviewFeed.getFeedAdapter(mContext);
        mReviewView = new ReviewView(mAdapter);

        mReviewView.setAction(new FeedScreenMenu());
        mReviewView.setAction(new GridItem());
        mReviewView.setAction(new RatingBarAction());

        ReviewView.ReviewViewParams params = mReviewView.getParams();
        params.cellHeight = ReviewView.CellDimension.FULL;
        params.cellWidth = ReviewView.CellDimension.FULL;
        params.subjectIsVisible = true;
        params.ratingIsVisible = true;
        params.bannerButtonIsVisible = true;
        params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
        params.coverManager = false;
    }

    public static ReviewView newScreen(Context context) {
        FeedScreen screen = new FeedScreen(context);
        return screen.getScreen();
    }

    private ReviewView getScreen() {
        return mReviewView;
    }

    private void launch(LaunchableUi ui) {
        LauncherUi.launch(ui, mReviewView.getParent(), REQUEST_CODE, ui.getLaunchTag(), new
                Bundle());
    }

    private class FeedScreenMenu extends ReviewViewAction.MenuAction {
        public static final  int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
        private static final int MENU               = R.menu.fragment_feed;

        private FeedScreenMenu() {
            super(MENU, null, false);
        }

        @Override
        protected void addMenuItems() {
            addMenuActionItem(new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    Administrator.get(mContext).newReviewBuilder();
                    launch(BuildScreen.newScreen(getActivity()));
                }
            }, MENU_NEW_REVIEW_ID, false);
        }
    }

    private class GridItem extends GridItemExpander {
        private static final String TAG            = "FeedGridItemListener";
        private static final int    REQUEST_DELETE = 314;
        private FeedGridItemListener mListener;

        public GridItem() {
            super(mAdapter);
            mListener = new FeedGridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        @Override
        public void onClickExpanded(GvData item, int position, View v, ReviewViewAdapter expanded) {
            launch(ReviewDataScreen.newScreen(mContext, expanded));
        }

        @Override
        public void onLongClickExpanded(GvData item, int position, View v, ReviewViewAdapter
                expanded) {
            if (expanded != null) {
                String alert = getActivity().getResources().getString(R.string.alert_delete_review);
                Bundle args = new Bundle();
                GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
                DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
                DialogShower.show(dialog, mListener, REQUEST_DELETE, DialogAlertFragment.ALERT_TAG);
            }
        }

        private void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == REQUEST_DELETE) {
                GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                GvReviewList.GvReviewOverview review = (GvReviewList.GvReviewOverview) datum;
                ReviewFeed.removeFromFeed(getActivity(), review.getId());
                getReviewView().resetGridViewData();
            }
        }

        protected abstract class FeedGridItemListener extends Fragment
                implements DialogAlertFragment.DialogAlertListener {

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode, args);
            }
        }
    }

    private class RatingBarAction extends ReviewViewAction.RatingBarAction {
        @Override
        public void onClick(View v) {
            launch(ReviewDataScreen.newScreen(mContext, ReviewFeed.getAggregateAdapter(mContext)));
        }
    }
}
