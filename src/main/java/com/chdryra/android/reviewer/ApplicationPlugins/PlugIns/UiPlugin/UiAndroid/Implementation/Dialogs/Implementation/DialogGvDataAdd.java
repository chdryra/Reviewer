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
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.Implementation.DataConfigs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataAdd<T extends GvDataParcelable> extends
        DialogCancelAddDoneFragment implements GvDataAdder, LaunchableUi {
    private static final int ADD = R.string.add;

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private ReviewDataEditor<T> mBuilder;
    private DataAddListener<T> mAddListener;

    private boolean mQuickSet = false;

    public DialogGvDataAdd(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    private boolean isQuickSet() {
        return mQuickSet && mBuilder != null;
    }

    @Override
    public String getLaunchTag() {
        return "Add" + mDataType.getDatumName();
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
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
        mQuickSet = args != null && args.getBoolean(DataConfigs.Adder.QUICK_SET);
        if (!mQuickSet) {
            //TODO make type safe
            mAddListener = getTargetListenerOrThrow(DataAddListener.class);
        } else {
            ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
            mBuilder = app.getReviewBuilder().getReviewEditor().newDataEditor(mDataType);
        }
    }

    private void setDialogTitle() {
        setDialogTitle(add() + " " + mDataType.getDatumName());
    }

    private String add() {
        return getString(ADD);
    }

    private void setLayout() {
        LocationServicesApi api = AppInstanceAndroid.getInstance(getActivity()).getLocationServices().getApi();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(getActivity(), new DefaultLayoutConfig(), api);
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
            mBuilder.commitData();
        } else {
            mAddListener.onDone(getTargetRequestCode());
        }
    }
}
