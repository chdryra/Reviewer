package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreenMenu extends MenuActionNone<GvReviewOverview> {
    public static final int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
    private static final int MENU = R.menu.menu_feed;
    private FactoryLaunchableUi mUiLauncher;
    private LaunchableConfig mBuildScreenConfig;

    public FeedScreenMenu(FactoryLaunchableUi uiLauncher, LaunchableConfig buildScreenConfig) {
        super(MENU, null, false);
        mUiLauncher = uiLauncher;
        mBuildScreenConfig = buildScreenConfig;
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindMenuActionItem(new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                mUiLauncher.launch(mBuildScreenConfig, getActivity(), new Bundle());
            }
        }, MENU_NEW_REVIEW_ID, false);
    }
}
