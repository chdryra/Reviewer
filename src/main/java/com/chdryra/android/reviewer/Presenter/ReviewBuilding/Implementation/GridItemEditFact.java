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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditFact extends GridItemEdit<GvFact> {
    private static final int LAUNCH_BROWSER
            = RequestCodeGenerator.getCode("LaunchUrlBrowser");

    private final LaunchableConfig mUrlConfig;

    public GridItemEditFact(LaunchableConfig factConfig,
                            LaunchableConfig urlConfig,
                            ParcelablePacker<GvFact> dataPacker) {
        super(factConfig, dataPacker);
        mUrlConfig = urlConfig;
    }

    @Override
    public void onGridItemLongClick(GvFact item, int position, View v) {
        if (!item.isUrl()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlert(Strings.Alerts.EDIT_ON_BROWSER, LAUNCH_BROWSER, packItem(item));
        }
    }

    @Override
    protected void doAlertPositive(Bundle args) {
        launch(mUrlConfig, args);
    }
}
