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
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProviderObserver;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
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

    private ReviewTreeNode mNode;
    private ReviewView mReviewView;

    private AuthorFeedScreen(Context context, ReviewsRepository repository, Author author) {
        String title = author.getName() + "'s feed";
        ReviewPublisher publisher = new ReviewPublisher(author, PublishDate.now());
        Review root = FactoryReview.createReviewUser(publisher, title, 0f);
        mNode = FactoryReview.createReviewTreeNode(root, true);
        for (Review review : repository.getReviews()) {
            addReview(review);
        }

        mReviewView = ReviewListScreen.newScreen(context, mNode
                , repository, new GridItem(), new FeedScreenMenu());

        repository.registerObserver(this);
    }

    //Static methods
    public static ReviewView newScreen(Context context) {
        ReviewsRepository provider = Administrator.get(context).getReviewsRepository();
        Author author = Administrator.get(context).getAuthor();
        AuthorFeedScreen screen = new AuthorFeedScreen(context, provider, author);
        return screen.getReviewView();
    }

    //private methods
    private ReviewView getReviewView() {
        return mReviewView;
    }

    private void addReview(Review review) {
        mNode.addChild(FactoryReview.createReviewTreeNode(review, false));
    }

    private void removeReview(ReviewId id) {
        mNode.removeChild(id);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
        mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId id) {
        removeReview(id);
        mReviewView.onGridDataChanged();
    }

    private static class FeedScreenMenu extends ReviewViewAction.MenuAction {
        public static final int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
        private static final int MENU = R.menu.menu_feed;

        private FeedScreenMenu() {
            super(MENU, null, false);
        }

        //Overridden
        @Override
        protected void addMenuItems() {
            bindMenuActionItem(new MenuActionItem() {
                //Overridden
                @Override
                public void doAction(Context context, MenuItem item) {
                    Administrator.get(context).newReviewBuilder();
                    LaunchableUi ui = BuildScreen.newScreen(context);
                    LauncherUi.launch(ui, getReviewView().getFragment(), REQUEST_CODE, ui
                            .getLaunchTag
                                    (), new Bundle());
                }
            }, MENU_NEW_REVIEW_ID, false);
        }
    }

    private class GridItem extends GiLaunchReviewDataScreen {
        private static final String TAG = "FeedGridItemListener";
        private static final int REQUEST_DELETE = 314;
        private FeedGridItemListener mListener;

        //Constructors
        public GridItem() {
            mListener = new FeedGridItemListener() {
            };
            super.registerActionListener(mListener, TAG);
        }

        private void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == REQUEST_DELETE) {
                GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                        .GvReviewOverview) datum;
                Administrator.get(getActivity()).deleteFromAuthorsFeed(review.getId());
            }
        }

        //Overridden
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

        protected abstract class FeedGridItemListener extends Fragment
                implements DialogAlertFragment.DialogAlertListener {

            //Overridden
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
