package com.chdryra.android.reviewer.View.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridItem {
    private FactoryLauncherUi mLauncherFactory;

    public FactoryGridItem(FactoryLauncherUi launcherFactory) {
        mLauncherFactory = launcherFactory;
    }

    public <T extends GvData> GridItemAction<T> newGridItemLauncher(Class<T> dataClass) {
        new GridItemLauncher<>(mLauncherFactory, mLaunchableFactory);
    }
}
