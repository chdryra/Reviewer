package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemAddEditFact extends GridItemEdit<GvFactList.GvFact> {
    private static final int EDIT_ON_BROWSER = RequestCodeGenerator.getCode("EditOnBrowser");
    private LaunchableConfig<GvUrlList.GvUrl> mUrlConfig;

    //Constructors
    public GridItemAddEditFact(LaunchableConfig<GvFactList.GvFact> factConfig,
                        LaunchableConfig<GvUrlList.GvUrl> urlConfig,
                        FactoryLaunchableUi launchableFactory,
                        GvDataPacker<GvFactList.GvFact> dataPacker) {
        super(factConfig, launchableFactory, dataPacker);
        mUrlConfig = urlConfig;
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvFactList.GvFact item, int position, View v) {
        if (!item.isUrl()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_edit_on_browser),
                    EDIT_ON_BROWSER, item);
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == EDIT_ON_BROWSER) {
            FactoryLaunchableUi launchableFactory = getLaunchableFactory();
            LaunchableUi urlUi = mUrlConfig.getLaunchable(launchableFactory);
            launchableFactory.launch(urlUi, getActivity(), getLaunchableRequestCode(), null, args);
        }
    }
}
