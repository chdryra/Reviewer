/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.corelibrary.Dialogs.DialogCancelDeleteDoneFragment;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServices;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataEdit<T extends GvDataParcelable>
        extends DialogCancelDeleteDoneFragment
        implements GvDataEditor, LaunchableUi {

    private final GvDataType<T> mDataType;
    private DatumLayoutEdit<T> mLayout;
    private ReviewDataEditor<T> mEditor;
    private DataEditListener<T> mListener;
    private T mDatum;

    private boolean mQuickSet = false;

    public DialogGvDataEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public String getLaunchTag() {
        return "Edit" + mDataType.getDatumName();
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public void setKeyboardAction(EditText editText) {
        setKeyboardDoDoneOnEditText(editText);
    }

    @Override
    public void setDeleteTitle(String title) {
        setDeleteWhatTitle(title);
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        if (isQuickSet()) {
            mEditor.delete(mDatum);
            mEditor.commitData();
        } else {
            mListener.onDelete(mDatum, getTargetRequestCode());
        }
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
        setIsQuickSet();
        setDialogTitle();
        getDatumToEdit();
    }

    @Override
    protected void onDoneButtonClick() {
        T newDatum = mLayout.createGvDataFromInputs();
        if (isQuickSet()) {
            if (!mDatum.equals(newDatum)) mEditor.replace(mDatum, newDatum);
            mEditor.commitData();
        } else {
            mListener.onEdit(mDatum, newDatum, getTargetRequestCode());
        }
    }

    @Override
    public void onDestroyView() {
        if (mEditor != null) mEditor.detachFromBuilder();
        super.onDestroyView();
    }

    private boolean isQuickSet() {
        return mQuickSet && mEditor != null;
    }

    private void setIsQuickSet() {
        Bundle args = getArguments();
        mQuickSet = args != null && args.getBoolean(EditorSuite.QUICK_ADD);
        if (!mQuickSet) {
            //TODO make type safe
            mListener = (DataEditListener<T>) getTargetListenerOrThrow(DataEditListener.class);
        } else {
            ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
            mEditor = app.getEditor().getEditor().newDataEditor(mDataType);
        }
    }

    private void getDatumToEdit() {
        ParcelablePacker<T> unpacker = new ParcelablePacker<>();
        mDatum = unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, getArguments());
    }

    private void setLayout() {
        LocationServices services = AppInstanceAndroid.getInstance(getActivity())
                .getGeolocation().getLocationServices();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(getActivity(), new
                DefaultLayoutConfig(), services);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    private void setDialogTitle() {
        if (mDataType.equals(GvImage.TYPE)) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(Strings.Dialogs.EDIT + " " + mDataType.getDatumName());
        }
    }
}
