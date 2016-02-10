/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemFeedScreen extends GridItemLauncher<GvReviewOverview>
        implements DialogAlertFragment.DialogAlertListener{
    private static final int SHARE_EDIT = RequestCodeGenerator.getCode("ShareEditReview");

    private LaunchableUiAlertable mShareEditUi;

    public GridItemFeedScreen(FactoryReviewViewLaunchable launchableFactory,
                              LaunchableUiLauncher launcher,
                              LaunchableUiAlertable shareEditUi) {
        super(launchableFactory, launcher);
        mShareEditUi = shareEditUi;
    }

    @Override
    public void onGridItemLongClick(GvReviewOverview item, int position, View v) {
        launchShareEdit(item);
    }

    private void launchShareEdit(GvReviewOverview item) {
        Bundle args = new Bundle();
        args.putParcelable(mShareEditUi.getLaunchTag(), item.getGvReviewId());
        launch(mShareEditUi, SHARE_EDIT, args);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mShareEditUi.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mShareEditUi.onAlertPositive(requestCode, args);
    }
}
