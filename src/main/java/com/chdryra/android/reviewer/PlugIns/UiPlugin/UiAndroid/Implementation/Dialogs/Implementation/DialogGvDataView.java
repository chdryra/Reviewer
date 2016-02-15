/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogOneButtonFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts
        .Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts
        .Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts
        .Interfaces.DialogLayout;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataView<T extends GvData> extends DialogOneButtonFragment implements
        LaunchableUi {
    private GvDataType<T> mDataType;
    private DialogLayout<T> mLayout;
    private T mDatum;

    protected DialogGvDataView(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public String getLaunchTag() {
        return "View" + mDataType.getDatumName();
    }

    @Override
    public void launch(LauncherUi launcher) {
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
        GvDataType dataType = mDatum.getGvDataType();
        String title = dataType.equals(GvImage.TYPE) ? null : dataType.getDatumName();
        setDialogTitle(title);
    }

    private void setLayout() {
        ReviewerLocationServices services
                = ApplicationInstance.getInstance(getActivity()).getLocationServices();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(new DefaultLayoutConfig(), services);
        //TODO make type safe
        mLayout = (DialogLayout<T>) layoutFactory.newLayout(mDatum.getGvDataType());
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

