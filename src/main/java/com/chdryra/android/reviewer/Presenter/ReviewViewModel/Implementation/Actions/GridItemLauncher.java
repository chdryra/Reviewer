package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode
            ("GiLaunchReviewDataScreen");

    private FactoryReviewViewLaunchable mLaunchableFactory;
    private LaunchableUiLauncher mLauncher;

    public GridItemLauncher(FactoryReviewViewLaunchable launchableFactory,
                            LaunchableUiLauncher launcher) {
        mLauncher = launcher;
        mLaunchableFactory = launchableFactory;
    }

    //Overridden
    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
        launch(getLaunchableUi(expanded), REQUEST_CODE, new Bundle());
    }

    private LaunchableUi getLaunchableUi(ReviewViewAdapter<?> expanded) {
        LaunchableUi screen = expanded.getReviewView();
        if (screen == null) screen = mLaunchableFactory.newViewScreen(expanded);
        return screen;
    }

    protected LaunchableUiLauncher getLauncher() {
        return mLauncher;
    }

    protected void launch(LaunchableUi ui, int requestCode, Bundle args) {
        mLauncher.launch(ui, getActivity(), requestCode, args);
    }
}
