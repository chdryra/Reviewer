/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 October, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

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
 * <li>{@link LayoutHolder}: UI updates and user input extraction</li>
 * <li>{@link GvDataEditListener}: commissioning fragment.
 * </ul>
 * </p>
 */
public abstract class DialogGvDataEdit<T extends GvData>
        extends DialogCancelDeleteDoneFragment implements AddEditLayout.GvDataEditor,
        LaunchableUi {

    private final GvDataType<T> mDataType;
    private AddEditLayout<T> mLayout;
    private T mDatum;
    private GvDataEditListener<T> mEditListener;

    /**
     * Provides a callback that can be called delete or done buttons are pressed.
     *
     * @param <T>:{@link GvData} type
     */
    public interface GvDataEditListener<T extends GvData> {
        //abstract methods
        //abstract
        void onGvDataDelete(T data);

        void onGvDataEdit(T oldDatum, T newDatum);
    }

    //Constructors
    public DialogGvDataEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    //public methods
    public GvDataType getGvDataType() {
        return mDataType;
    }

    //protected methods
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
        mEditListener.onGvDataDelete(mDatum);
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

        Bundle args = getArguments();
        mLayout = FactoryGvDataViewLayout.newLayout(getGvDataType(), this);
        mLayout.onActivityAttached(getActivity(), args);
        GvDataPacker<T> unpacker = new GvDataPacker<>();
        mDatum = unpacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);

        //TODO make type safe
        mEditListener = (GvDataEditListener<T>) getTargetListener(GvDataEditListener.class);

        if (getGvDataType() == GvImageList.GvImage.TYPE) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(getResources().getString(R.string.edit) + " " + mDataType
                    .getDatumName());
        }
    }

    @Override
    protected void onDoneButtonClick() {
        mEditListener.onGvDataEdit(mDatum, mLayout.createGvData());
    }
}
