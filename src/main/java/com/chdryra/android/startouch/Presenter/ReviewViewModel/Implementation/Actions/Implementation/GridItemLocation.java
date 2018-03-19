/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.os.Bundle;
import android.view.View;

import com.chdryra.android.corelibrary.Dialogs.AlertListener;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataReference;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GridItemLocation extends GridItemConfigLauncher<GvLocation.Reference> implements
        AlertListener {
    private static final int LAUNCH_MAP
            = RequestCodeGenerator.getCode(GridItemLocation.class, "LaunchMap");

    private final LaunchableConfig mMapper;
    private final GvConverterLocations mConverter;
    private int mAlertDialogRequestCode;

    public GridItemLocation(UiLauncher launcher,
                            FactoryReviewView launchableFactory,
                            LaunchableConfig dataConfig,
                            LaunchableConfig mapper,
                            GvConverterLocations converter) {
        super(launcher, launchableFactory, dataConfig);
        mMapper = mapper;
        mConverter = converter;
    }

    protected void showAlert(String alert, int requestCode, Bundle args) {
        mAlertDialogRequestCode = requestCode;
        getCurrentScreen().showAlert(alert, requestCode, this, args);
    }

    @Override
    public void onLongClickNotExpandable(GvLocation.Reference item, int position, View v) {
        ParcelablePacker<GvLocation> packer = new ParcelablePacker<>();
        Bundle args = new Bundle();
        VhDataReference<DataLocation> vh = item.getReferenceViewHolder();
        if (vh != null && vh.getDataValue() != null) {
            packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, mConverter.convert(vh
                    .getDataValue()), args);
            showAlert(Strings.Alerts.SHOW_ON_MAP, LAUNCH_MAP, args);
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mAlertDialogRequestCode) {
            mMapper.launch(new UiLauncherArgs(mMapper.getDefaultRequestCode()).setBundle(args));
        }
    }
}
