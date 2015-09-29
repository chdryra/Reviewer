/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataView;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Launcher.ReviewLauncher;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataScreen {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("ReviewDataScreen");
    private ReviewView        mReviewView;

    private ReviewDataScreen(ReviewViewAdapter<? extends GvData> adapter) {
        mReviewView = new ReviewView(adapter);
        mReviewView.setAction(new GridItem());
    }

    public static ReviewView newScreen(ReviewViewAdapter<? extends GvData> adapter) {
        return new  ReviewDataScreen(adapter).getScreen();
    }

    public static ReviewView newScreen(ReviewViewAdapter<? extends GvData> adapter,
                                       GvDataType forDefaults) {
        ReviewView screen = newScreen(adapter);
        DefaultParameters.setParams(screen, forDefaults);
        DefaultMenus.setMenu(screen, forDefaults);
        DefaultGridActions.setGridAction(screen, forDefaults);
        return screen;
    }

    private ReviewView getScreen() {
        return mReviewView;
    }

    public static class GridItem extends GridItemExpander {
        private static final String TAG                 = "ReviewViewExpandableGridItemListener";
        private static final int    REQUEST_GOTO_REVIEW = 314;
        private GridItemListener mListener;

        public GridItem() {
            mListener = new GridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        @Override
        public void onClickExpandable(GvData item, int position, View v, ReviewViewAdapter
                expanded) {
            ReviewView screen = expanded.getReviewView();
            //TODO make type safe
            if (screen == null) screen = newScreen(expanded, item.getGvDataType());
            LauncherUi.launch(screen, getReviewView().getParent(), REQUEST_CODE,
                    screen.getLaunchTag(), new Bundle());
        }

        @Override
        public void onClickNotExpandable(GvData item, int position, View v) {
            if (item.isCollection()) return;
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(item.getGvDataType());
            if (config != null) {
                ConfigGvDataUi.LaunchableConfig view = config.getViewConfig();
                LauncherUi.launch(view.getLaunchable(), mListener, view.getRequestCode(), view
                        .getTag(), args);
            }
        }

        @Override
        public void onLongClickNotExpandable(GvData item, int position, View v) {
            onClickNotExpandable(item, position, v);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            String alert = getActivity().getResources().getString(R.string.alert_goto_review);
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
            DialogShower.show(dialog, mListener, REQUEST_GOTO_REVIEW, DialogAlertFragment
                    .ALERT_TAG);
        }

        private void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == REQUEST_GOTO_REVIEW) {
                GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                ReviewLauncher.launchReview(getActivity(), getReviewView().getParent(), datum);
            }
        }

        protected abstract class GridItemListener extends Fragment
                implements DialogAlertFragment.DialogAlertListener, DialogGvDataView
                .GotoReviewListener {

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode, args);
            }

            @Override
            public void onGotoReview(GvData datum) {
                ReviewLauncher.launchReview(getActivity(), getReviewView().getParent(), datum);
            }
        }
    }
}
