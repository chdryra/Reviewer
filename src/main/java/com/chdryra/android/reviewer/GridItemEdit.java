/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEdit extends ViewReviewAction.GridItemAction {
    private static final String TAG = "GridItemEditListener";
    private final Fragment                        mListener;
    private       ConfigGvDataUi.LaunchableConfig mConfig;
    private       GvDataHandler                   mHandler;

    public GridItemEdit(ConfigGvDataUi.LaunchableConfig config) {
        mConfig = config;
        mListener = new EditListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onSetViewReview() {
        mHandler = FactoryGvDataHandler.newHandler(getData());
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item, View v) {
        if (getViewReview() == null) return;

        Bundle args = new Bundle();//Administrator.get(getActivity()).pack(getAdapter());
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

        LauncherUi.launch(mConfig.getLaunchable(), mListener, getRequestCode(),
                mConfig.getTag(), args);
    }

    protected int getRequestCode() {
        return mConfig.getRequestCode();
    }

    //TODO make type safe
    protected void editData(GvDataList.GvData oldDatum, GvDataList.GvData newDatum) {
        mHandler.replace(oldDatum, newDatum, getActivity());
        getViewReview().updateUi();
    }

    //TODO make type safe
    protected void deleteData(GvDataList.GvData datum) {
        mHandler.delete(datum);
        getViewReview().updateUi();
    }

    protected abstract class EditListener extends Fragment implements DialogFragmentGvDataEdit
            .GvDataEditListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {
            deleteData(data);
        }

        @Override
        public void onGvDataEdit(GvDataList.GvData oldDatum, GvDataList.GvData newDatum) {
            editData(oldDatum, newDatum);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getRequestCode() && data != null) {
                GvDataList.GvData oldDatum = GvDataPacker.unpackItem(GvDataPacker
                        .CurrentNewDatum.CURRENT, data);
                if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                    editData(oldDatum, GvDataPacker.unpackItem(GvDataPacker
                            .CurrentNewDatum.NEW, data));
                } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                    deleteData(oldDatum);
                }
            }
        }
    }
}
