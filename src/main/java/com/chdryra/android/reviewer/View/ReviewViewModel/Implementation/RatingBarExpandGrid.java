package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");
    FactoryLaunchableUi mLaunchableFactory;

    public RatingBarExpandGrid(FactoryLaunchableUi launchableFactory) {
        mLaunchableFactory = launchableFactory;
    }

    //Overridden
    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        LaunchableUi ui = expanded.getReviewView();
        if (ui == null) ui = mLaunchableFactory.newViewScreen(expanded);
        LauncherUiImpl.launch(ui, getActivity(), REQUEST_CODE, ui.getLaunchTag(), new Bundle());
    }
}
