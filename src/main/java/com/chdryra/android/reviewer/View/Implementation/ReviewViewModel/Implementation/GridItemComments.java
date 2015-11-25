package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GridItemComments extends GridItemConfigLauncher<GvComment> {
    public GridItemComments(LaunchableConfig<GvComment> launchableConfig,
                            FactoryLaunchableUi launchableFactory,
                            GvDataPacker<GvData> packer) {
        super(launchableConfig, launchableFactory, packer);
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
