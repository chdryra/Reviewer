package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GridItemComments extends GridItemConfigLauncher<GvCommentList.GvComment> {
    public GridItemComments(LaunchableConfig<GvCommentList.GvComment> launchableConfig,
                            FactoryLaunchableUi launchableFactory) {
        super(launchableConfig, launchableFactory);
    }

    @Override
    public void onClickNotExpandable(GvCommentList.GvComment item, int position, View v) {
        try {
            GvCommentList.GvComment unsplit = item.getUnsplitComment();
            super.onClickNotExpandable(unsplit, position, v);
        } catch (ClassCastException e) {
            super.onClickNotExpandable(item, position, v);
        }
    }
}
