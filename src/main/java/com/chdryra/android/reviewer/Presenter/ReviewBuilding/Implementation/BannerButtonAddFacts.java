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

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddFacts extends BannerButtonAdd<GvFact> {
    private LaunchableConfig mUrlAdder;

    public BannerButtonAddFacts(String title, LaunchableConfig factAdder,
                                LaunchableConfig urlAdder, LaunchableUiLauncher launchableFactory,
                                GvDataList<GvFact> emptyFactList,
                                GvDataPacker<GvFact> dataPacker) {
        super(factAdder, launchableFactory, title, emptyFactList, dataPacker);
        mUrlAdder = urlAdder;
    }

    @Override
    public boolean onLongClick(View v) {
        showAlertDialog(getActivity().getString(R.string.alert_add_on_browser), mUrlAdder.getRequestCode());
        return true;
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mUrlAdder.getRequestCode()) launchConfig(mUrlAdder);
    }
}
