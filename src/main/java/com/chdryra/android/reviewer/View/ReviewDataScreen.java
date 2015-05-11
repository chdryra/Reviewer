/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Controller.ReviewFeed;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataScreen {
    private Context           mContext;
    private ReviewView        mReviewView;
    private ReviewViewAdapter mAdapter;

    private ReviewDataScreen(Context context, ReviewViewAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
        mReviewView = new ReviewView(mAdapter);
        mReviewView.setAction(new GridItem());
    }

    public static ReviewView newScreen(Context context, ReviewViewAdapter adapter) {
        return new ReviewDataScreen(context, adapter).getScreen();
    }

    private ReviewView getScreen() {
        return mReviewView;
    }

    private void startNewScreen(ReviewViewAdapter adapter) {
        requestNewIntent(newScreen(mContext, adapter));
    }

    private void requestNewIntent(ReviewView newActivityScreen) {
        ActivityReviewView.startNewActivity(mReviewView.getActivity(), newActivityScreen);
    }

    private class GridItem extends GridItemExpandable {
        private static final String TAG                 = "ReviewViewExpandableGridItemListener";
        private static final int    REQUEST_GOTO_REVIEW = 314;
        private GridItemListener mListener;

        public GridItem() {
            super(mAdapter);
            mListener = new GridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        @Override
        public void onClickExpanded(GvData item, int position, View v, ReviewViewAdapter expanded) {
            startNewScreen(expanded);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            if (item.hasReviewId()) {
                String alert = getActivity().getResources().getString(R.string.alert_goto_review);
                Bundle args = new Bundle();
                GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
                DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
                DialogShower.show(dialog, mListener, REQUEST_GOTO_REVIEW, DialogAlertFragment
                        .ALERT_TAG);
            }
        }

        private void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == REQUEST_GOTO_REVIEW) {
                GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                startNewScreen(ReviewFeed.getFeedAdapter(mContext).expandItem(datum));
            }
        }

        protected abstract class GridItemListener extends Fragment
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
