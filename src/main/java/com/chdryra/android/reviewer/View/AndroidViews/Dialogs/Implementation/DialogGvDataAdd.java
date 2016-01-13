/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Implementation
        .DialogLayoutBasic;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Base class for all dialog fragments that can edit data on reviews.
 * <p>
 * This class is the launched dialog and handles mainly button presses,
 * view intialisation and callbacks to the commissioning fragment. All
 * other functionality is outsourced to the appropriate classes:
 * <ul>
 * <li>{@link GvDataPacker}: Unpacking of received data.</li>
 * <li>{@link DialogLayoutBasic.LayoutHolder}: UI updates and user input extraction</li>
 * <li>{@link GvDataAddListener}: commissioning fragment.
 * <li>{@link DataBuilder}: input validation when QUICK_SET = true.
 * </ul>
 * </p>
 * <p>
 * By default the dialog won't add any data to reviews. It is assumed that data is updated using
 * a callback to the commissioning fragment.
 * It is then up to that fragment/activity to decide what to do
 * with the entered data. However, if the QUICK_SET boolean in the dialog arguments is set to
 * true, the dialog will validate using a {@link DataBuilder} and forward the data directly to the
 * ControllerReviewEditable packed in the arguments by the Administrator.
 * </p>
 */
public abstract class DialogGvDataAdd<T extends GvData> extends
        DialogCancelAddDoneFragment implements GvDataAdder, LaunchableUi {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private DataBuilderAdapter<T> mBuilder;
    private GvDataAddListener<T> mAddListener;

    private boolean mQuickSet = false;

    /**
     * Provides a callback for when the add button is pressed
     */
    public interface GvDataAddListener<T extends GvData> {
        //abstract
        boolean onGvDataAdd(T data, int requestCode);

        void onGvDataCancel(int requestCode);

        void onGvDataDone(int requestCode);
    }

    //Constructors
    public DialogGvDataAdd(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    //public methods
    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    //protected methods
    @Override
    protected Intent getReturnData() {
        return null;
    }

    //package private methods
    boolean isQuickSet() {
        return mQuickSet && mBuilder != null;
    }

    //Overridden
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
        editText.setImeActionLabel("Add", KEYBOARD_DO_ACTION);
    }

    @Override
    public void setTitle(String title) {
        getDialog().setTitle(title);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mLayout.createGvDataFromInputs();

        boolean added = isQuickSet() ? mBuilder.add(newDatum) :
                newDatum.isValidForDisplay() && mAddListener.onGvDataAdd(newDatum, getTargetRequestCode());

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
        mQuickSet = args != null && args.getBoolean(QUICK_SET);
        if (!mQuickSet) {
            //TODO make type safe
            mAddListener = getTargetListener(GvDataAddListener.class);
        } else {
            ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
            mBuilder = app.getDataBuilderAdapter(mDataType);
        }
    }

    private void setDialogTitle() {
        setDialogTitle(getResources().getString(R.string.add) + " " + mDataType.getDatumName());
    }

    private void setLayout() {
        LocationServicesPlugin provider = ApplicationInstance.getInstance(getActivity()).getLocationServicesPlugin();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(new DefaultLayoutConfig(), provider);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    @Override
    protected void onCancelButtonClick() {
        if (isQuickSet()) {
            mBuilder.resetData();
        } else {
            mAddListener.onGvDataCancel(getTargetRequestCode());
        }
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) {
            mBuilder.publishData();
        } else {
            mAddListener.onGvDataDone(getTargetRequestCode());
        }
    }
}
