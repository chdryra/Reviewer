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
public class EditScreenBannerButton extends ReviewViewAction.BannerButtonAction {
    private static final String TAG = "ActionBannerButtonAddListener";

    private ConfigGvDataUi.LaunchableConfig mConfig;
    private GvDataHandler                   mHandler;
    private Fragment                        mListener;

    public EditScreenBannerButton(ConfigGvDataUi.LaunchableConfig config, String title) {
        super(title);
        mConfig = config;
        mListener = new AddListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onAttachReviewView() {
        mHandler = FactoryGvDataHandler.newHandler(getData());
    }

    @Override
    public void onClick(View v) {
        if (getReviewView() == null) return;

        LauncherUi.launch(mConfig.getLaunchable(), mListener, getRequestCode(),
                mConfig.getTag(), new Bundle());
    }

    //TODO make type safe
    protected boolean addData(GvDataList.GvData data) {
        boolean added = mHandler.add(data, getActivity());
        getReviewView().updateUi();
        return added;
    }

    protected int getRequestCode() {
        return mConfig.getRequestCode();
    }

    //Dialogs expected to communicate directly with target fragments so using "invisible"
    // fragment as listener.
    //Restrictions on how fragments are constructed mean I have to use an abstract class...
    private abstract class AddListener extends Fragment implements DialogAddGvData
            .GvDataAddListener {
        @Override
        public boolean onGvDataAdd(GvDataList.GvData data) {
            return addData(data);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getRequestCode() && data != null
                    && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                addData(GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data));
            }
        }
    }
}
