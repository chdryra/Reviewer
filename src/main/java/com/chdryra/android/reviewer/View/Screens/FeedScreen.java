package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityBuildReview;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements DialogAlertFragment.DialogAlertListener{
    private ReviewView mView;
    private FeedScreenGridItem mGridItem;

    public FeedScreen(Context context, ReviewsRepository feed) {
        Author author = feed.getAuthor();
        String title = author.getName() + "'s feed";
        mGridItem = new FeedScreenGridItem();
        ReviewViewAction.MenuAction menu = new FeedScreenMenu();
        mView = ReviewsRepositoryScreen.newScreen(context, feed, title, mGridItem, menu);
    }

    public ReviewView getReviewView() {
        return mView;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if(requestCode == FeedScreenGridItem.DIALOG_ALERT) {
            mGridItem.onAlertPositive(requestCode, args);
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 18/10/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class FeedScreenGridItem extends GiLaunchReviewDataScreen
            implements DialogAlertFragment.DialogAlertListener{
        private static final int DIALOG_ALERT = RequestCodeGenerator.getCode("DeleteReview");

        //Overridden
        @Override
        public void onLongClickExpandable(GvData item, int position, View v, ReviewViewAdapter
                expanded) {
            if (expanded != null) {
                String alert = getActivity().getResources().getString(R.string.alert_delete_review);
                Bundle args = new Bundle();
                GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
                DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, DIALOG_ALERT, args);
                DialogShower.show(dialog, getActivity(), DialogAlertFragment.ALERT_TAG);
            }
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
            GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                    .GvReviewOverview) datum;
            Administrator.get(getActivity()).deleteFromAuthorsFeed(review.getId());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 18/10/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class FeedScreenMenu extends ReviewViewAction.MenuAction {
        public static final int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
        private static final int MENU = R.menu.menu_feed;
        private static final int REQUEST_CODE = RequestCodeGenerator.getCode("FeedScreenMenu");

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
                    LaunchableUi ui = new ActivityBuildReview();
                    LauncherUi.launch(ui, getReviewView().getFragment(), REQUEST_CODE,
                            ui.getLaunchTag(), new Bundle());
                }
            }, MENU_NEW_REVIEW_ID, false);
        }
    }
}
