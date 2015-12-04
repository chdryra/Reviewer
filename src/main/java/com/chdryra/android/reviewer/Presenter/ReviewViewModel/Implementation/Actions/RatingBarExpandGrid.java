package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");
    FactoryReviewViewLaunchable mLaunchableFactory;
    LaunchableUiLauncher mLauncher;

    public RatingBarExpandGrid(FactoryReviewViewLaunchable launchableFactory,
                               LaunchableUiLauncher launcher) {
        mLaunchableFactory = launchableFactory;
        mLauncher = launcher;
    }

    //Overridden
    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        mLauncher.launch(getLaunchableUi(expanded), getActivity(), REQUEST_CODE);
    }

    private LaunchableUi getLaunchableUi(ReviewViewAdapter<?> expanded) {
        LaunchableUi ui = expanded.getReviewView();
        if (ui == null) ui = mLaunchableFactory.newViewScreen(expanded);
        return ui;
    }
}
