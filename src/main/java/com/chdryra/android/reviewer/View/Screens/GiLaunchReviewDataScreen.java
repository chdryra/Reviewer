package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GiLaunchReviewDataScreen extends GridItemExpander {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode
            ("GiLaunchReviewDataScreen");

    //Overridden
    @Override
    public void onClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
        ReviewView ui = ReviewDataScreen.newScreen(expanded, item.getGvDataType());
        LauncherUi.launch(ui, getReviewView().getFragment(), REQUEST_CODE, ui.getLaunchTag(), new
                Bundle());
    }
}
