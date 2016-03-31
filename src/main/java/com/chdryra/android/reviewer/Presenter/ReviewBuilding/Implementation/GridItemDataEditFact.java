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

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.mygenerallibrary.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditFact extends GridItemDataEdit<GvFact> {
    private static final int LAUNCH_BROWSER
            = RequestCodeGenerator.getCode("LaunchUrlBrowser");

    private LaunchableConfig mUrlConfig;

    public GridItemDataEditFact(LaunchableConfig factConfig,
                                LaunchableConfig urlConfig,
                                LaunchableUiLauncher launchableFactory,
                                ParcelablePacker<GvFact> dataPacker) {
        super(factConfig, launchableFactory, dataPacker);
        mUrlConfig = urlConfig;
    }

    @Override
    public void onGridItemLongClick(GvFact item, int position, View v) {
        if (!item.isUrl()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_edit_on_browser), LAUNCH_BROWSER,
                    packItem(item));
        }
    }

    @Override
    protected void doAlertPositive(Bundle args) {
        launch(mUrlConfig, args);
    }
}
