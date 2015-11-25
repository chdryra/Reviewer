package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode
            ("GiLaunchReviewDataScreen");

    private FactoryLaunchableUi mLaunchableFactory;

    public GridItemLauncher(FactoryLaunchableUi launchableFactory) {
        mLaunchableFactory = launchableFactory;
    }

    //Overridden
    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?>
            expanded) {
        LaunchableUi screen = expanded.getReviewView();
        if (screen == null) screen = mLaunchableFactory.newViewScreen(expanded);
        launch(screen, REQUEST_CODE, screen.getLaunchTag(), new Bundle());
    }

    protected FactoryLaunchableUi getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected void launch(LaunchableUi ui, int requestCode, String tag, Bundle args) {
        mLaunchableFactory.launch(ui, getActivity(), requestCode, tag, args);
    }
}
