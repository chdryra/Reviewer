/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewView;
import com.chdryra.android.reviewer.Controller.ReviewViewAction;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen {
    public static ReviewView newScreen(Context context) {
        ReviewView view = new ReviewView(Administrator.get(context).getPublishedReviews());

        view.setAction(new FeedScreenMenu());
        view.setAction(new GridItem());

        ReviewView.ReviewViewParams params = view.getParams();
        params.cellHeight = ReviewView.CellDimension.FULL;
        params.cellWidth = ReviewView.CellDimension.FULL;
        params.subjectIsVisible = false;
        params.ratingIsVisible = false;
        params.bannerButtonIsVisible = false;
        params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
        params.coverManager = false;

        return view;
    }

    public static class FeedScreenMenu extends ReviewViewAction.MenuAction {
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
                    requestNewReviewIntent();
                }
            }, MENU_NEW_REVIEW_ID, false);
        }

        private void requestNewReviewIntent() {
            Activity activity = getActivity();
            if (activity == null) return;

            Administrator admin = Administrator.get(getActivity());
            admin.newReviewBuilder();
            Intent i = new Intent(activity, ActivityReviewView.class);
            admin.packView(BuildScreen.newScreen(getActivity()), i);

            getActivity().startActivity(i);
        }
    }

    public static class GridItem extends ReviewViewAction.GridItemAction {
        private static final String TAG            = "FeedGridItemListener";
        private static final int    REQUEST_DELETE = 314;
        private FeedGridItemListener mListener;

        public GridItem() {
            mListener = new FeedGridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        @Override
        public void onGridItemClick(GvData item, View v) {
            super.onGridItemClick(item, v);
        }

        @Override
        public void onGridItemLongClick(GvData item, View v) {
            String alert = getActivity().getResources().getString(R.string.alert_delete_review);
            showAlertDialog(alert, REQUEST_DELETE, item);
        }

        protected void showAlertDialog(String alert, int requestCode, GvData item) {
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
            DialogShower.show(dialog, mListener, requestCode, DialogAlertFragment.ALERT_TAG);
        }

        protected void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == REQUEST_DELETE) {
                GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                GvReviewList.GvReviewOverview review = (GvReviewList.GvReviewOverview) datum;
                Administrator.get(getActivity()).deleteReview(review.getId());
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
}
