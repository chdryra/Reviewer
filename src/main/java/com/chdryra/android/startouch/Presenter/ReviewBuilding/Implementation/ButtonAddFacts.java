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
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class ButtonAddFacts extends ButtonAdd<GvFact> {
    private static final int LAUNCH_BROWSER
            = RequestCodeGenerator.getCode(ButtonAddFacts.class, "LaunchUrlBrowser");

    private final LaunchableConfig mUrlAdder;

    public ButtonAddFacts(DataReference<String> title, LaunchableConfig factAdder,
                          LaunchableConfig urlAdder,
                          GvDataList<GvFact> emptyFactList,
                          ParcelablePacker<GvFact> dataPacker) {
        super(factAdder, title, emptyFactList, dataPacker);
        mUrlAdder = urlAdder;
    }

    @Override
    public boolean onLongClick(View v) {
        showAlert(Strings.Alerts.ADD_ON_BROWSER, LAUNCH_BROWSER, new Bundle());
        return true;
    }

    @Override
    public void doAlertPositive(Bundle args) {
        launch(mUrlAdder, new Bundle());
    }
}
