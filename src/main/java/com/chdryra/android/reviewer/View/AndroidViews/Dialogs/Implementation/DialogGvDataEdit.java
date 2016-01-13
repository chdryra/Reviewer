/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 October, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Implementation
        .DialogLayoutBasic;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Base class for all dialog fragments that can edit data on reviews.
 * <p>
 * This class is the launched dialog and handles mainly button presses,
 * view intialisation and callbacks to the commissioning fragment. All
 * other functionality is outsourced to the appropriate classes:
 * <ul>
 * <li>{@link GvDataPacker}: Unpacking of received data.</li>
 * <li>{@link DialogLayoutBasic.LayoutHolder}: UI updates and user input extraction</li>
 * <li>{@link EditListener}: commissioning fragment.
 * </ul>
 * </p>
 */
public abstract class DialogGvDataEdit<T extends GvData>
        extends DialogCancelDeleteDoneFragment
        implements GvDataEditor, LaunchableUi {

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private T mDatum;
    private EditListener<T> mEditListener;

    public interface EditListener<T extends GvData> {
        void onDelete(T data, int requestCode);

        void onEdit(T oldDatum, T newDatum, int requestCode);
    }

    public DialogGvDataEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    //Overridden
    @Override
    public String getLaunchTag() {
        return "Edit" + mDataType.getDatumName();
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
        mEditListener.onDelete(mDatum, getTargetRequestCode());
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
        mEditListener = (EditListener<T>) getTargetListener(EditListener.class);
    }

    private void getDatumToEdit() {
        GvDataPacker<T> unpacker = new GvDataPacker<>();
        mDatum = unpacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, getArguments());
    }

    private void setLayout() {
        LocationServicesPlugin provider = ApplicationInstance.getInstance(getActivity()).getLocationServicesPlugin();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(new DefaultLayoutConfig(), provider);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    private void setDialogTitle() {
        if (mDataType.equals(GvImage.TYPE)) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(getResources().getString(R.string.edit) + " " + mDataType
                    .getDatumName());
        }
    }

    @Override
    protected void onDoneButtonClick() {
        mEditListener.onEdit(mDatum, mLayout.createGvDataFromInputs(), getTargetRequestCode());
    }
}
