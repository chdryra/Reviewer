package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddFacts extends BannerButtonAdd<GvFact> {
    private static final GvDataType<GvFact> TYPE = GvFact.TYPE;
    private static final int ADD_ON_BROWSER = RequestCodeGenerator.getCode("AddOnBrowser");
    private LaunchableConfig<GvUrl> mUrlAdder;

    //Constructors
    public BannerButtonAddFacts(String title, LaunchableConfig<GvFact> factAdder,
                                LaunchableConfig<GvUrl> urlAdder,
                                FactoryGvData dataFactory,
                                GvDataPacker<GvFact> dataPacker,
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
