/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.AdderConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataAdd<T extends GvData> extends
        DialogCancelAddDoneFragment implements GvDataAdder, LaunchableUi {
    private static final int ADD = R.string.add;

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private DataBuilderAdapter<T> mBuilder;
    private DataAddListener<T> mAddListener;

    private boolean mQuickSet = false;

    public DialogGvDataAdd(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    boolean isQuickSet() {
        return mQuickSet && mBuilder != null;
    }

    @Override
    public String getLaunchTag() {
        return "Add" + mDataType.getDatumName();
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void setKeyboardAction(EditText editText) {
        setKeyboardDoActionOnEditText(editText);
        editText.setImeActionLabel(add(), KEYBOARD_DO_ACTION);
    }

    @Override
    public void setTitle(String title) {
        getDialog().setTitle(title);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mLayout.createGvDataFromInputs();

        boolean added = isQuickSet() ? mBuilder.add(newDatum) :
                newDatum.isValidForDisplay() && mAddListener.onAdd(newDatum, getTargetRequestCode());

        if (added) mLayout.onAdd(newDatum);
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        setIsQuickSet();
        setDialogTitle();
    }

    private void setIsQuickSet() {
        Bundle args = getArguments();
        mQuickSet = args != null && args.getBoolean(AdderConfig.QUICK_SET);
        if (!mQuickSet) {
            //TODO make type safe
            mAddListener = getTargetListenerOrThrow(DataAddListener.class);
        } else {
            ApplicationInstance app = AndroidAppInstance.getInstance(getActivity());
            mBuilder = app.getDataBuilderAdapter(mDataType);
        }
    }

    private void setDialogTitle() {
        setDialogTitle(add() + " " + mDataType.getDatumName());
    }

    private String add() {
        return getString(ADD);
    }

    private void setLayout() {
        LocationServicesApi provider = AndroidAppInstance.getInstance(getActivity()).getLocationServices();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(new DefaultLayoutConfig(), provider);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    @Override
    protected void onCancelButtonClick() {
        if (isQuickSet()) {
            mBuilder.resetData();
        } else {
            mAddListener.onCancel(getTargetRequestCode());
        }
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) {
            mBuilder.publishData();
        } else {
            mAddListener.onDone(getTargetRequestCode());
        }
    }
}
