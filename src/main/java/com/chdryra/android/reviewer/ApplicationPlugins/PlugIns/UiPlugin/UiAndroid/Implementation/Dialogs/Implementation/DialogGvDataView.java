/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;



import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogOneButtonFragment;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataView<T extends GvDataParcelable> extends DialogOneButtonFragment
        implements LaunchableUi {
    private final GvDataType<T> mDataType;
    private DatumLayoutView<T> mLayout;
    private T mDatum;

    DialogGvDataView(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    public String getLaunchTag() {
        return "View" + mDataType.getDatumName();
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), mDatum);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogParameters();
        unpackDatum();
        setLayout();
        setDialogTitle();
    }

    private void setDialogTitle() {
        GvDataType<?> dataType = mDatum.getGvDataType();
        String title = dataType.equals(GvImage.TYPE) ? null : dataType.getDatumName();
        setDialogTitle(title);
    }

    private void setLayout() {
        LocationServicesApi api
                = AppInstanceAndroid.getInstance(getActivity()).getLocationServices().getApi();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(getActivity(), new DefaultLayoutConfig(), api);
        //TODO make type safe
        mLayout = (DatumLayoutView<T>) layoutFactory.newLayout(mDatum.getGvDataType());
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    private void unpackDatum() {
        Bundle args = getArguments();
        ParcelablePacker<T> unpacker = new ParcelablePacker<>();
        mDatum = unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args);
    }

    private void setDialogParameters() {
        dismissDialogOnLeftClick();
        hideKeyboardOnLaunch();
    }
}

