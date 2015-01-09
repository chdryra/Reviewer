/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

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
 * <li>{@link GvDataViewHolder}: UI updates and user input extraction</li>
 * <li>{@link GvDataEditListener}: commissioning fragment.
 * </ul>
 * </p>
 */
public abstract class DialogFragmentGvDataEdit<T extends GvDataList.GvData>
        extends DialogCancelDeleteDoneFragment implements GvDataViewEdit.GvDataEditor,
        LaunchableUi {

    private GvDataList.GvType     mDataType;
    private T                     mDatum;
    private GvDataPacker<T>       mPacker;
    private GvDataViewHolder<T>   mViewHolder;
    private GvDataEditListener<T> mEditListener;

    /**
     * Provides a callback that can be called delete or done buttons are pressed.
     *
     * @param <T>:{@link GvDataList.GvData} type
     */
    public interface GvDataEditListener<T extends GvDataList.GvData> {
        void onGvDataDelete(T data);

        void onGvDataEdit(T oldDatum, T newDatum);
    }

    DialogFragmentGvDataEdit(Class<? extends GvDataList<T>> gvDataListClass) {
        mDataType = FactoryGvData.gvType(gvDataListClass);
        mPacker = new GvDataPacker<>();
        mViewHolder = FactoryGvDataViewHolder.newHolder(getGvType(), this);
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

    public GvDataList.GvType getGvType() {
        return mDataType;
    }

    @Override
    protected View createDialogUi() {
        mViewHolder.inflate(getActivity());
        mViewHolder.initialiseView(mDatum);

        return mViewHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatum = mPacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, getArguments());

        try {
            //TODO make type safe
            mEditListener = (GvDataEditListener<T>) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                    "GvDataEditListener");
        }

        if (getGvType() == GvDataList.GvType.IMAGES) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(getResources().getString(R.string.edit) + " " + mDataType
                    .getDatumString());
        }
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
    protected void onDoneButtonClick() {
        mEditListener.onGvDataEdit(mDatum, mViewHolder.getGvData());
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }
}
