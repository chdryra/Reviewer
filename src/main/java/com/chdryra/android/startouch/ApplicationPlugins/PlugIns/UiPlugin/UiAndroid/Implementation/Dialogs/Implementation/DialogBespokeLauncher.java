/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Implementation;


import android.os.Bundle;

import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DialogBespokeLauncher<T extends GvDataParcelable> extends
        DialogGvDataView2Button<T> {
    private final String mButtonTitle;

    public DialogBespokeLauncher(GvDataType<T> dataType, String buttonTitle) {
        super(dataType);
        mButtonTitle = buttonTitle;
    }

    @Override
    protected void onRightButtonClick() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        LaunchableConfig config = app.getUi().getConfig().getBespokeDatumViewer(getDataType()
                .getDatumName());
        ParcelablePacker<T> packer = new ParcelablePacker<>();
        Bundle args = new Bundle();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, getDatum(), args);
        config.launch(new UiLauncherArgs(config.getDefaultRequestCode()).setBundle(args));
        super.onRightButtonClick();
    }

    @Override
    protected void setDialogParameters() {
        super.setDialogParameters();
        dismissDialogOnRightClick();
        setRightButtonAction(ActionType.OTHER);
        setRightButtonText(mButtonTitle);
    }

    public static class ViewLocation extends DialogBespokeLauncher<GvLocation> {
        public ViewLocation() {
            super(GvLocation.TYPE, Strings.Buttons.MAP);
        }
    }
}
