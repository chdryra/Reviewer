/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.corelibrary.Dialogs.DialogCancelAddDoneFragment;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataAdd<T extends GvDataParcelable> extends
        DialogCancelAddDoneFragment implements GvDataAdder, LaunchableUi {
    private final GvDataType<T> mDataType;

    private DatumLayoutEdit<T> mLayout;
    private ReviewDataEditor<T> mEditor;
    private DataAddListener<T> mAddListener;

    private boolean mQuickAdd = false;

    public DialogGvDataAdd(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
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
        editText.setImeActionLabel(add(), setKeyboardDoActionOnEditText(editText));
    }

    @Override
    public void setTitle(String title) {
        getDialog().setTitle(title);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mLayout.createGvDataFromInputs();

        boolean added = isQuickAdd() ? mEditor.add(newDatum) :
                newDatum.isValidForDisplay() && mAddListener.onAdd(newDatum, getTargetRequestCode
                        ());

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
        setIsQuickReview();
        setDialogTitle();
    }

    @Override
    protected void onCancelButtonClick() {
        if (isQuickAdd()) {
            mEditor.resetData();
        } else {
            mAddListener.onCancel(getTargetRequestCode());
        }
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickAdd()) {
            mEditor.commitData();
        } else {
            mAddListener.onDone(getTargetRequestCode());
        }
    }

    private boolean isQuickAdd() {
        return mQuickAdd && mEditor != null;
    }

    private void setIsQuickReview() {
        Bundle args = getArguments();
        boolean quickReview = args != null && args.getBoolean(EditorSuite.QUICK_REVIEW);
        if (quickReview && isQuickType()) setHideMiddleButton();
    }

    private boolean isQuickType() {
        for(GvDataType<?> non : EditorSuite.NON_QUICK) {
            if(mDataType.equals(non)) return false;
        }
        return true;
    }

    private void setIsQuickSet() {
        Bundle args = getArguments();
        mQuickAdd = args != null && args.getBoolean(EditorSuite.QUICK_ADD);
        if (!mQuickAdd) {
            //TODO make type safe
            mAddListener = getTargetListenerOrThrow(DataAddListener.class);
        } else {
            ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
            mEditor = app.getEditor().getEditor().newDataEditor(mDataType);
        }
    }

    private void setDialogTitle() {
        setDialogTitle(add() + " " + mDataType.getDatumName());
    }

    private String add() {
        return Strings.Dialogs.ADD;
    }

    private void setLayout() {
        LocationServices api = AppInstanceAndroid.getInstance(getActivity())
                .getGeolocation().getLocationServices();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(getActivity(), new
                DefaultLayoutConfig(), api);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    @Override
    public void onDestroyView() {
        if(mEditor != null) mEditor.detachFromBuilder();
        super.onDestroyView();
    }
}

