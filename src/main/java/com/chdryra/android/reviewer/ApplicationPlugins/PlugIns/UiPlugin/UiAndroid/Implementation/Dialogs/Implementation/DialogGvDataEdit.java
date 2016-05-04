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

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataEdit<T extends GvData>
        extends DialogCancelDeleteDoneFragment
        implements GvDataEditor, LaunchableUi {

    private static final int EDIT = R.string.edit;

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private T mDatum;
    private DataEditListener<T> mDataEditListener;

    public DialogGvDataEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public String getLaunchTag() {
        return edit() + mDataType.getDatumName();
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void setKeyboardAction(EditText editText) {
        setKeyboardDoDoneOnEditText(editText);
    }

    @Override
    public void setDeleteConfirmTitle(String title) {
        setDeleteWhatTitle(title);
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        mDataEditListener.onDelete(mDatum, getTargetRequestCode());
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), mDatum);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        getDatumToEdit();
        getTargetListener();
        setDialogTitle();
    }

    private void getTargetListener() {
        //TODO make type safe
        mDataEditListener = (DataEditListener<T>) getTargetListener(DataEditListener.class);
    }

    private void getDatumToEdit() {
        ParcelablePacker<T> unpacker = new ParcelablePacker<>();
        mDatum = unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, getArguments());
    }

    private void setLayout() {
        LocationServicesApi services = ApplicationInstance.getInstance(getActivity()).getLocationServices();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(new DefaultLayoutConfig(), services);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    private void setDialogTitle() {
        if (mDataType.equals(GvImage.TYPE)) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(edit() + " " + mDataType.getDatumName());
        }
    }

    private String edit() {
        return getString(EDIT);
    }

    @Override
    protected void onDoneButtonClick() {
        mDataEditListener.onEdit(mDatum, mLayout.createGvDataFromInputs(), getTargetRequestCode());
    }
}
