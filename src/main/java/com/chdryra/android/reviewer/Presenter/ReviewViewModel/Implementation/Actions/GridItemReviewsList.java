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

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemReviewsList extends GridItemLauncher<GvNode>
        implements AlertListener, NewReviewListener {
    private static final int SHARE_EDIT = RequestCodeGenerator.getCode("ShareEditReview");

    private LaunchableUiAlertable mShareEditUi;
    private BuildScreenLauncher mLauncher;

    public GridItemReviewsList(FactoryReviewView launchableFactory,
                               LaunchableUiAlertable shareEditUi,
                               BuildScreenLauncher launcher) {
        super(launchableFactory);
        mShareEditUi = shareEditUi;
        mLauncher = launcher;
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        launchShareEdit(item);
    }

    private void launchShareEdit(GvNode item) {
        Bundle args = new Bundle();
        args.putParcelable(mShareEditUi.getLaunchTag(), item.getGvReviewId());
        launch(mShareEditUi, SHARE_EDIT, args);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mLauncher.launch(getApp(), template);
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
