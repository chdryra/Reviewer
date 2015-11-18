package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.Implementation.RatingBarActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RbExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");
    FactoryLaunchable mScreenFactory;

    public RbExpandGrid(FactoryLaunchable screenFactory) {
        mScreenFactory = screenFactory;
    }

    //Overridden
    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        LaunchableUi ui = expanded.getReviewView();
        if (ui == null) ui = mScreenFactory.newScreen(expanded);
        LauncherUi.launch(ui, getActivity(), REQUEST_CODE, ui.getLaunchTag(), new Bundle());
    }
}
