/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;


import android.os.Bundle;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DialogBespokeLauncher<T extends GvDataParcelable> extends DialogGvDataView2Button<T> {

    public DialogBespokeLauncher(GvDataType<T> dataType) {
        super(dataType);
    }

    @Override
    protected void onRightButtonClick() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        LaunchableConfig config = app.getUi().getConfig().getBespokeDatumViewer(getDataType().getDatumName());
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
        setRightButtonText(Strings.Buttons.MAP);
    }

    public static class ViewLocation extends DialogBespokeLauncher<GvLocation> {
        public ViewLocation() {
            super(GvLocation.TYPE);
        }
    }
}
