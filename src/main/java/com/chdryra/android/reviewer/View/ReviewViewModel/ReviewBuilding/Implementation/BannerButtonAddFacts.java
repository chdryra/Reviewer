package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddFacts extends BannerButtonEdit<GvFactList.GvFact> {
    private static final int ADD_ON_BROWSER = RequestCodeGenerator.getCode("AddOnBrowser");

    //Constructors
    public BannerButtonAddFacts(String title) {
        super(GvFactList.GvFact.TYPE, title);
    }

    //Overridden
    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_browser), ADD_ON_BROWSER);
        return true;
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == ADD_ON_BROWSER) {
            LaunchableUi urlUi = FactoryLaunchable.newLaunchable(ActivityEditUrlBrowser.class);
            LauncherUi.launch(urlUi, getActivity(), getLaunchableRequestCode(), null,
                    new Bundle());
        }
    }
}
