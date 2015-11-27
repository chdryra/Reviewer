package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

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
