/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActionGridItemEdit extends ReviewView.GridItemAction {
    private static final String TAG = "ActionGridItemEditListener";
    private ConfigGvDataUi.GvDataUiConfig mConfig;
    private GvDataHandler                 mHandler;

    public ActionGridItemEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        super(controller, dataType);
        ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(getDataType());
        mConfig = config.getEditorConfig();
    }

    @Override
    public void onSetReviewView() {
        mHandler = FactoryGvDataHandler.newHandler(getReviewView().getGridData());
    }

    protected Fragment getNewListener() {
        return new EditListener() {
            @Override
            public void onGvDataDelete(GvDataList.GvData data) {
                mHandler.delete(data);
                getReviewView().updateUi();
            }

            @Override
            public void onGvDataEdit(GvDataList.GvData oldDatum, GvDataList.GvData newDatum) {
                mHandler.replace(oldDatum, newDatum, getActivity());
                getReviewView().updateUi();
            }
        };
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item) {
        if (getReviewView() == null) return;

        Bundle args = Administrator.get(getActivity()).pack(getController());
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

        LauncherUi.launch(mConfig.getReviewDataUI(), getListener(TAG), mConfig.getRequestCode(),
                mConfig.getTag(), args);
    }

    //restrictions on how fragments are constructed mean I have to use an abstract class...
    private abstract class EditListener extends Fragment implements DialogFragmentGvDataEdit
            .GvDataEditListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {

        }

        @Override
        public void onGvDataEdit(GvDataList.GvData oldDatum, GvDataList.GvData newDatum) {

        }
    }
}
