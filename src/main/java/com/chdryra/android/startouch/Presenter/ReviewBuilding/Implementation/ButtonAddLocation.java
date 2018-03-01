/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ButtonAddLocation extends ButtonAdd<GvLocation> {
    private static final int LAUNCH_MAP = RequestCodeGenerator.getCode("LaunchMapAlert");
    private final LaunchableConfig mMapScreenConfig;

    public ButtonAddLocation(LaunchableConfig adderConfig,
                             LaunchableConfig mapScreenConfig,
                             DataReference<String> title,
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
