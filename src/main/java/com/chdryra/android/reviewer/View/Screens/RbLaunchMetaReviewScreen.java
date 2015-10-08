package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RbLaunchMetaReviewScreen extends ReviewViewAction.RatingBarAction {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode
            ("RbLaunchMetaReviewScreen");

    //Overridden
    @Override
    public void onClick(View v) {
        try {
            AdapterReviewNode adapter = (AdapterReviewNode) getAdapter();
            ReviewView ui = ReviewDataScreen.newScreen(adapter.expandGridData());
            LauncherUi.launch(ui, getReviewView().getFragment(), REQUEST_CODE, ui.getLaunchTag(),
                    new
                            Bundle());
        } catch (ClassCastException e) {

        }
    }
}
