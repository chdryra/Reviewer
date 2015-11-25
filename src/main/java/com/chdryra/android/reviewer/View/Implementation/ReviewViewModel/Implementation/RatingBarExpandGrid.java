package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

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
        mLaunchableFactory.launch(ui, getActivity(), REQUEST_CODE, ui.getLaunchTag());
    }
}
