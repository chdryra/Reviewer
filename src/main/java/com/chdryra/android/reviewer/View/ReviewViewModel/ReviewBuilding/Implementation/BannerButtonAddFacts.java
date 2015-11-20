package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddFacts extends BannerButtonAdd<GvFactList.GvFact> {
    private static final GvDataType<GvFactList.GvFact> TYPE = GvFactList.GvFact.TYPE;
    private static final int ADD_ON_BROWSER = RequestCodeGenerator.getCode("AddOnBrowser");
    private LaunchableConfig<GvUrlList.GvUrl> mUrlAdder;

    //Constructors
    public BannerButtonAddFacts(String title, LaunchableConfig<GvFactList.GvFact> factAdder,
                                LaunchableConfig<GvUrlList.GvUrl> urlAdder,
                                FactoryGvData dataFactory,
                                GvDataPacker<GvFactList.GvFact> dataPacker,
                                FactoryLaunchableUi launchableFactory) {
        super(factAdder, title, TYPE, dataFactory, dataPacker, launchableFactory);
        mUrlAdder = urlAdder;
    }

    //Overridden
    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_browser), ADD_ON_BROWSER);
        return true;
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == ADD_ON_BROWSER) launchConfig(mUrlAdder);
    }
}
