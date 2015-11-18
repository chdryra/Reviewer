package com.chdryra.android.reviewer.View.Screens.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode
            ("GiLaunchReviewDataScreen");

    private FactoryLaunchable mLaunchableFactory;

    public GridItemLauncher(FactoryLaunchable launchableFactory) {
        mLaunchableFactory = launchableFactory;
    }

    //Overridden
    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?>
            expanded) {
        LaunchableUi screen = expanded.getReviewView();
        if (screen == null) screen = mLaunchableFactory.newScreen(expanded);
        LauncherUi.launch(screen, getActivity(), REQUEST_CODE, screen.getLaunchTag(), new Bundle());
    }

    protected FactoryLaunchable getLaunchableFactory() {
        return mLaunchableFactory;
    }
}
