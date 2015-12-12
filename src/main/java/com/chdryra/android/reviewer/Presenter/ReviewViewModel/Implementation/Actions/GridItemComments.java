package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GridItemComments extends GridItemConfigLauncher<GvComment> {
    public GridItemComments(LaunchableConfig commentsViewConfig,
                            FactoryReviewViewLaunchable launchableFactory,
                            LaunchableUiLauncher launcher,
                            GvDataPacker<GvData> packer) {
        super(commentsViewConfig, launchableFactory, launcher, packer);
    }

    @Override
    public void onClickNotExpandable(GvComment item, int position, View v) {
        try {
            GvComment unsplit = item.getUnsplitComment();
            super.onClickNotExpandable(unsplit, position, v);
        } catch (ClassCastException e) {
            super.onClickNotExpandable(item, position, v);
        }
    }
}
