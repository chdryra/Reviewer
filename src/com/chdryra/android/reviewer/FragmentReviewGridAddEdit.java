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
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: base class for adding/editing review data UIs.
 * <p/>
 * <p>
 * By default assumes the banner button will launch an Add dialog and a grid cell click will
 * launch an edit dialog (as defined in OptionsReviewBuild). If using this default then need to
 * implement <code>packGridCellData(.)</code> to bundle the grid cell selected data for editing
 * so that it can be passed to the edit dialog.
 * </p>
 * <p>
 * Need to implement <code>getDataType()</code> for proper data initialisation.
 * </p>
 * <p>
 * Need to implement <code>addData(.)</code> and <code>editData(.)</code> to know what to do
 * once the appropriate dialog or activity has returned the user input.
 * </p>
 *
 * @param <T>: GVData type shown in grid cell
 */
public abstract class FragmentReviewGridAddEdit<T extends GVData> extends
        FragmentReviewGrid<GVReviewDataList<T>> implements GVTypable {

    private ConfigReviewDataUI.ReviewDataConfig mDataOption;

    @Override
    public abstract GVType getGVType();

    protected abstract void addData(int resultCode, Intent data);

    protected abstract void editData(int resultCode, Intent data);

    protected abstract Bundle packGridCellData(T data, Bundle args);

    protected final int getRequestCodeAdd() {
        return mDataOption.getDialogAddRequestCode();
    }

    protected final int getRequestCodeEdit() {
        return mDataOption.getDialogEditRequestCode();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (requestCode == getRequestCodeAdd()) {
            addData(resultCode, data);

        } else if (requestCode == getRequestCodeEdit()) {
            editData(resultCode, data);

        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GVType dataType = getGVType();

        mDataOption = ConfigReviewDataUI.get(dataType);
        //TODO how to make this type safe
        @SuppressWarnings("unchecked") GVReviewDataList<T> data = getController().getData(dataType);
        setGridViewData(data);
        setDeleteWhatTitle(dataType.getDataString());
        setBannerButtonText(getResources().getString(R.string.add) + " " + dataType.getDatumString
                ());
    }

    @Override
    protected void onBannerButtonClick() {
        DialogShower.show(mDataOption.getDialogAddFragment(), FragmentReviewGridAddEdit.this,
                getRequestCodeAdd(), mDataOption.getDialogAddDataTag(),
                Administrator.get(getActivity()).pack(getController()));
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        //TODO how to make this type safe
        @SuppressWarnings("unchecked") T item = (T) parent.getItemAtPosition(position);
        DialogShower.show(mDataOption.getDialogEditFragment(), FragmentReviewGridAddEdit.this,
                getRequestCodeEdit(), mDataOption.getDialogEditDataTag(),
                packGridCellData(item, Administrator.get(getActivity()).pack(getController())));
    }
}
