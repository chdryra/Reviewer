/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAddLocation extends BannerButtonAdd<GvLocation> {
    private static final int LAUNCH_MAP = RequestCodeGenerator.getCode("LaunchMapAlert");
    private final LaunchableConfig mMapScreenConfig;

    public BannerButtonAddLocation(LaunchableConfig adderConfig,
                                   LaunchableConfig mapScreenConfig,
                                   String title,
                                   GvDataList<GvLocation> emptyLocationList,
                                   ParcelablePacker<GvLocation> dataPacker) {
        super(adderConfig, title, emptyLocationList, dataPacker);
        mMapScreenConfig = mapScreenConfig;
    }

    @Override
    public boolean onLongClick(View v) {
        showAlert(Strings.Alerts.ADD_ON_MAP, LAUNCH_MAP, new Bundle());
        return true;
    }

    @Override
    public void doAlertPositive(Bundle args) {
        launch(mMapScreenConfig, new Bundle());
    }
}
