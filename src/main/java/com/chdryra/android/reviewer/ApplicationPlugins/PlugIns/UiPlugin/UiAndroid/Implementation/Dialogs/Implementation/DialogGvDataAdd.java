/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

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
        boolean quickReview = args != null && args.getBoolean(ReviewEditorSuite.QUICK_REVIEW);
        if (quickReview && !mDataType.equals(GvTag.TYPE)) setHideMiddleButton();
    }

    private void setIsQuickSet() {
        Bundle args = getArguments();
        mQuickAdd = args != null && args.getBoolean(ReviewEditorSuite.QUICK_ADD);
        if (!mQuickAdd) {
            //TODO make type safe
            mAddListener = getTargetListenerOrThrow(DataAddListener.class);
        } else {
            ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
            mEditor = app.getReviewEditor().getEditor().newDataEditor(mDataType);
        }
    }

    private void setDialogTitle() {
        setDialogTitle(add() + " " + mDataType.getDatumName());
    }

    private String add() {
        return Strings.Dialogs.ADD;
    }

    private void setLayout() {
        LocationServicesApi api = AppInstanceAndroid.getInstance(getActivity())
                .getLocationServices().getApi();
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

