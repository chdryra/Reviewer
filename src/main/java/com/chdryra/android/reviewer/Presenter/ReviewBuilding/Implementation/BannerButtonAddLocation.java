package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddLocation extends BannerButtonAdd<GvLocation> {
    private LaunchableConfig mMapScreenConfig;

    //Constructors
    public BannerButtonAddLocation(LaunchableConfig adderConfig,
                                   LaunchableConfig mapScreenConfig,
                                   LaunchableUiLauncher launchableFactory, String title,
                                   GvDataList<GvLocation> emptyLocationList,
                                   GvDataPacker<GvLocation> dataPacker) {
        super(adderConfig, launchableFactory, title, emptyLocationList, dataPacker);
        mMapScreenConfig = mapScreenConfig;
    }

    //Overridden
    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_map), mMapScreenConfig.getRequestCode());
        return true;
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMapScreenConfig.getRequestCode()) {
            getLauncher().launch(mMapScreenConfig, getActivity(), new Bundle());
        }
    }
}
