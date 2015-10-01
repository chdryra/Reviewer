/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewNodeProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProviderObserver;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorFeedScreen implements ReviewsProviderObserver {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("FeedScreen");

    private ReviewNodeProvider mProvider;
    private ReviewView        mReviewView;

    private AuthorFeedScreen(Context context, ReviewNodeProvider provider) {
        mProvider = provider;
        mReviewView = ReviewListScreen.newScreen(context, provider.getReviewNode()
                , provider.getTagsManager(), new GridItem(), new FeedScreenMenu());
    }

    public static ReviewView newScreen(Context context) {
        ReviewNodeProvider feed = Administrator.get(context).getAuthorFeed();
        AuthorFeedScreen screen = new AuthorFeedScreen(context, feed);
        feed.registerObserver(screen);
        return screen.getReviewView();
    }

    @Override
    public void onReviewAdded(Review review) {
        mReviewView.notifyDataSetChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId id) {
        mReviewView.notifyDataSetChanged();
    }

    private ReviewView getReviewView() {
        return mReviewView;
    }

    private static class FeedScreenMenu extends ReviewViewAction.MenuAction {
        public static final  int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
        private static final int MENU = R.menu.menu_feed;

        private FeedScreenMenu() {
            super(MENU, null, false);
        }

        @Override
        protected void addMenuItems() {
            bindMenuActionItem(new MenuActionItem() {
                @Override
                public void doAction(Context context, MenuItem item) {
                    Administrator.get(context).newReviewBuilder();
                    LaunchableUi ui = BuildScreen.newScreen(context);
                    LauncherUi.launch(ui, getReviewView().getParent(), REQUEST_CODE, ui.getLaunchTag
                            (), new Bundle());
                }
            }, MENU_NEW_REVIEW_ID, false);
        }
    }

    private class GridItem extends GiLaunchReviewDataScreen {
        private static final String TAG            = "FeedGridItemListener";
        private static final int    REQUEST_DELETE = 314;
        private FeedGridItemListener mListener;

        public GridItem() {
            mListener = new FeedGridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        @Override
        public void onLongClickExpandable(GvData item, int position, View v, ReviewViewAdapter
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
                GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                        .GvReviewOverview) datum;
                Administrator.get(getActivity()).deleteFromAuthorsFeed(review.getId());
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
