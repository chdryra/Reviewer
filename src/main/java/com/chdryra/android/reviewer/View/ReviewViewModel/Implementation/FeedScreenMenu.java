package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityBuildReview;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreenMenu extends MenuActionNone<GvReviewOverviewList.GvReviewOverview> {
    public static final int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
    private static final int MENU = R.menu.menu_feed;

    public FeedScreenMenu() {
        super(MENU, null, false);
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindMenuActionItem(new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                Activity activity = getActivity();
                activity.startActivity(new Intent(activity, ActivityBuildReview.class));
            }
        }, MENU_NEW_REVIEW_ID, false);
    }
}
