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

import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditLocation extends GridItemDataEdit<GvLocation> {
    private LaunchableConfig mMapEditorConfig;

    public GridItemDataEditLocation(LaunchableConfig editorConfig,
                                    LaunchableConfig mapEditorConfig,
                                    ParcelablePacker<GvLocation> dataPacker) {
        super(editorConfig, dataPacker);
        mMapEditorConfig = mapEditorConfig;
    }

    @Override
    public void onGridItemLongClick(GvLocation item, int position, View v) {
        showAlert(Strings.Alerts.EDIT_ON_MAP, mMapEditorConfig.getRequestCode(), packItem(item));
    }

    @Override
    public void doAlertPositive(Bundle args) {
        launch(mMapEditorConfig, args);
    }
}
