/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;

/**
 * Base class for all dialog fragments that can edit data on reviews.
 * <p>
 * This class is the launched dialog and handles mainly button presses,
 * view intialisation and callbacks to the commissioning fragment. All
 * other functionality is outsourced to the appropriate classes:
 * <ul>
 * <li>{@link GvDataPacker}: Unpacking of received data.</li>
 * <li>{@link LayoutHolder}: UI updates and user input extraction</li>
 * <li>{@link GvDataAddListener}: commissioning fragment.
 * <li>{@link GvDataHandler}: input validation when QUICK_SET = true.
 * </ul>
 * </p>
 * <p>
 * By default the dialog won't add any data to reviews. It is assumed that data is updated using
 * a callback to the commissioning fragment.
 * It is then up to that fragment/activity to decide what to do
 * with the entered data. However, if the QUICK_SET boolean in the dialog arguments is set to
 * true, the dialog will validate using a {@link GvDataHandler} and forward the data directly to the
 * ControllerReviewEditable packed in the arguments by the Administrator.
 * </p>
 */
public abstract class DialogAddGvData<T extends GvData> extends
        DialogCancelAddDoneFragment implements GvDataEditLayout.GvDataAdder, LaunchableUi {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private final GvDataType                   mDataType;
    private final GvDataEditLayout<T>          mLayout;
    private       ReviewBuilder.DataBuilder<T> mBuilder;
    private       GvDataAddListener<T>         mAddListener;

    private boolean mQuickSet = false;

    /**
     * Provides a callback for when the add button is pressed
     */
    public interface GvDataAddListener<T extends GvData> {
        boolean onGvDataAdd(T data);

        void onGvDataCancel();

        void onGvDataDone();
    }

    public DialogAddGvData(Class<? extends GvDataList<T>> dataClass) {
        mDataType = FactoryGvData.gvType(dataClass);
        mLayout = FactoryGvDataViewLayout.newLayout(mDataType, this);
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

    public GvDataType getGvDataType() {
        return mDataType;
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mLayout.onActivityAttached(getActivity(), args);
        mQuickSet = args != null && args.getBoolean(QUICK_SET);

        //TODO make type safe
        mBuilder = (ReviewBuilder.DataBuilder<T>) Administrator.get(getActivity())
                .getReviewBuilder().getDataBuilder(mDataType);

        //TODO make type safe
        if (!isQuickSet()) {
            mAddListener = getTargetListener(GvDataAddListener.class);
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + mDataType.getDatumName());
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mLayout.createGvData();

        boolean added = isQuickSet() ? mBuilder.add(newDatum) :
                newDatum.isValidForDisplay() && mAddListener.onGvDataAdd(newDatum);

        if (added) mLayout.onAdd(newDatum);
    }

    @Override
    protected void onCancelButtonClick() {
        if (isQuickSet()) {
            mBuilder.reset();
        } else {
            mAddListener.onGvDataCancel();
        }
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) {
            mBuilder.setData();
        } else {
            mAddListener.onGvDataDone();
        }
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    boolean isQuickSet() {
        return mQuickSet && mBuilder != null;
    }
}
