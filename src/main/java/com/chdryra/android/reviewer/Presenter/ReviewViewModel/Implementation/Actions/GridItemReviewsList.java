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

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemReviewsList extends GridItemLauncher<GvNode> {
    private static final int SHARE_EDIT = RequestCodeGenerator.getCode("ShareEditReview");

    private final LaunchableUi mShareEditUi;

    public GridItemReviewsList(FactoryReviewView launchableFactory, LaunchableUi shareEditUi) {
        super(launchableFactory);
        mShareEditUi = shareEditUi;
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        Bundle args = new Bundle();
        DatumAuthorId data = new DatumAuthorId(item.getReviewId(), item.getAuthorId().toString());
        args.putParcelable(mShareEditUi.getLaunchTag(), data);
        launch(mShareEditUi, SHARE_EDIT, args);
    }
}
