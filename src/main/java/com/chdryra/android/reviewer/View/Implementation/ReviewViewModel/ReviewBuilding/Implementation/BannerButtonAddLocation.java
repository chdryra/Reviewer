package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Implementation.LaunchableActivities.ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddLocation extends BannerButtonAdd<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;
    private static final String TAG = "AddOnMap";
    private static final int ADD_ON_MAP = RequestCodeGenerator.getCode(TAG);

    //Constructors
    public BannerButtonAddLocation(LaunchableConfig<GvLocation> adderConfig,
                                   String title,
                                   FactoryGvData dataFactory,
                                   GvDataPacker<GvLocation> dataPacker,
                                   FactoryLaunchableUi launchableFactory) {
        super(adderConfig, title, TYPE, dataFactory, dataPacker, launchableFactory);
    }

    //Overridden
    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_map), ADD_ON_MAP);
        return true;
    }

    //TODO move launch class to config somehow...
    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == ADD_ON_MAP) {
            FactoryLaunchableUi launchableFactory = getLaunchableFactory();
            LaunchableUi mapUi = launchableFactory.newLaunchable(ActivityEditLocationMap.class);
            launchableFactory.launch(mapUi, getActivity(), getLaunchableRequestCode(), TAG, args);
        }
    }
}
