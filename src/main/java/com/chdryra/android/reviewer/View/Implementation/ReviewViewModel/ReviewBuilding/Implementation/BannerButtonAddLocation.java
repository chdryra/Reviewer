package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddLocation extends BannerButtonAdd<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;
    private LaunchableConfig mMapScreenConfig;

    //Constructors
    public BannerButtonAddLocation(LaunchableConfig adderConfig,
                                   LaunchableConfig mapScreenConfig,
                                   String title,
                                   FactoryGvData dataFactory,
                                   GvDataPacker<GvLocation> dataPacker,
                                   LaunchableUiLauncher launchableFactory) {
        super(adderConfig, title, TYPE, dataFactory, dataPacker, launchableFactory);
        mMapScreenConfig = mapScreenConfig;
    }

    //Overridden
    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_map), mMapScreenConfig.getRequestCode());
        return true;
    }

    //TODO move launch class to config somehow...
    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMapScreenConfig.getRequestCode()) {
            getLaunchableFactory().launch(mMapScreenConfig, getActivity(), new Bundle());
        }
    }
}
