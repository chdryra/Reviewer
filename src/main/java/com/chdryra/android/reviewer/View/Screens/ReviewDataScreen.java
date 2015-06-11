/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataScreen {
    private static final int REQUEST_CODE = 1976;
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

    private void startNewScreen(LaunchableUi ui) {
        LauncherUi.launch(ui, mReviewView.getParent(), REQUEST_CODE,
                ui.getLaunchTag(), new Bundle());
    }

    private class GridItem extends GridItemExpander {
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
            startNewScreen(newScreen(mContext, expanded));
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            if (item.getReviewId() != null) {
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
                LaunchableUi ui = Administrator.get(mContext).getReviewLaunchable(datum
                        .getReviewId());
                startNewScreen(ui);
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
