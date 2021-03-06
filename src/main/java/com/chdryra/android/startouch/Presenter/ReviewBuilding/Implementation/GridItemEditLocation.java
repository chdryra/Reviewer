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
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditLocation extends GridItemEdit<GvLocation> {
    private static final int LAUNCH_MAP
            = RequestCodeGenerator.getCode("LaunchMap");

    private final LaunchableConfig mMapEditorConfig;

    public GridItemEditLocation(LaunchableConfig editorConfig,
                                LaunchableConfig mapEditorConfig,
                                ParcelablePacker<GvLocation> dataPacker) {
        super(editorConfig, dataPacker);
        mMapEditorConfig = mapEditorConfig;
    }

    @Override
    public void onGridItemLongClick(GvLocation item, int position, View v) {
        showAlert(Strings.Alerts.EDIT_ON_MAP, LAUNCH_MAP, packItem(item));
    }

    @Override
    public void doAlertPositive(Bundle args) {
        launch(mMapEditorConfig, args);
    }
}
